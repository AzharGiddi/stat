package com.automation.core.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.script.ScriptException;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.interpol.ConfigurationInterpolator;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.hamcrest.Matchers;

import com.automation.core.driver.DriverFactory;
import com.automation.core.driver.DriverManager;
import com.automation.core.utils.FileUtil;
import com.automation.core.utils.PropertyUtil;
import com.automation.core.utils.StringComparator;
import com.automation.core.utils.StringMatcher;
import com.automation.core.utils.StringUtil;



public class ConfigurationManager {
	// early initialization
	static final Log log = LogFactoryImpl.getLog(ConfigurationManager.class);
	private static final ConfigurationManager INSTANCE = new ConfigurationManager();
	

	/**
	 * Private constructor, prevents instantiation from other classes
	 */
	private ConfigurationManager() {
		AbstractConfiguration.setDefaultListDelimiter(';');
		registerLookups();
		setHostName();
	}

	private void setHostName() {
		try {
			System.setProperty("host.name",
					InetAddress.getLocalHost().getHostName());
		} catch (Exception | Error e) {
			// This code added for MAC to fetch hostname
			InputStream stream=null;
			Scanner s = null;
			try {
				Process proc = Runtime.getRuntime().exec("hostname");
				stream = proc.getInputStream();
				if (stream != null) {
					s = new Scanner(stream);
					s.useDelimiter("\\A");
					String val = s.hasNext() ? s.next() : "";
					stream.close();
					s.close();
					System.setProperty("host.name",val);
				}
			} catch (Exception | Error e1) {
				log.trace(e1);
			}finally {
				try {
					if(null!=s)s.close();
					if(null!=stream)stream.close();
				} catch (Exception e2) {
					log.trace(e2);
				}
			}
		}
	}

	private void registerLookups(){
		ConfigurationInterpolator.registerGlobalLookup("rnd", new StrLookup() {
			public String lookup(String var) {
				var = var.replace("<%", "${").replace("%>", "}");
				var = getBundle().getSubstitutor().replace(var);
				return StringUtil.getRandomString(var);
			}
		});
		ConfigurationInterpolator.registerGlobalLookup("expr", new StrLookup() {
			public String lookup(String var) {
				try {
					var = var.replace("<%", "${").replace("%>", "}");
					var = getBundle().getSubstitutor().replace(var);
					Object res = StringUtil.eval(var);
					return String.valueOf(res);
				} catch (ScriptException e) {
					throw new RuntimeException("Unable to evaluate expression: " + var, e);
				}
			}
		});
	}
	public static ConfigurationManager getInstance() {
		return INSTANCE;
	}

	private static InheritableThreadLocal<PropertyUtil> LocalProps =
			new InheritableThreadLocal<PropertyUtil>() {
				@Override
				protected PropertyUtil initialValue() {
					PropertyUtil p = new PropertyUtil(
							System.getProperty("application.properties.file",
									"resources/application.properties"));
					p.setEncoding(p.getString(ApplicationProperties.LOCALE_CHAR_ENCODING.key, "UTF-8"));
					p.setProperty("execution.start.ts", System.currentTimeMillis());

					File prjDir = new File(".").getAbsoluteFile().getParentFile();
					p.setProperty("project.path", prjDir.getAbsolutePath());
					if(!p.containsKey("project.name"))
					p.setProperty("project.name", prjDir.getName());

					String[] resources = p.getStringArray("env.resources", "resources");
					for (String resource : resources) {
						addBundle(p, resource);
					}
					//p.setProperty("execute.initialValuelisteners", true);
					//executeOnLoadListeners(p);
					
					ConfigurationListener cl = new PropertyConfigurationListener();
					p.addConfigurationListener(cl);
					
					return p;
				}

				@Override
				protected PropertyUtil childValue(PropertyUtil parentValue) {
					PropertyUtil cp = new PropertyUtil(parentValue);
					ConfigurationListener cl = new PropertyConfigurationListener();
					cp.addConfigurationListener(cl);
					
					return cp;
				}

			};

	/**
	 * To add local resources.
	 * 
	 * @param fileOrDir
	 */
	public static void addBundle(String fileOrDir) {
		ConfigurationManager.addBundle(getBundle(), fileOrDir);
	}

	/**
	 * @param p
	 * @param fileOrDir
	 */
	private static void addBundle(PropertyUtil p, String fileOrDir) {
		String localResources = p.getString("local.reasources",
				p.getString("env.local.resources", "resources"));
		fileOrDir = p.getSubstitutor().replace(fileOrDir);
		File resourceFile = new File(fileOrDir);
		String[] locals = p.getStringArray(ApplicationProperties.LOAD_LOCALES.key);
		/**
		 * will reload existing properties value(if any) if the last loaded
		 * dir/file is not the current one. case: suit-1 default, suit-2 :
		 * s2-local, suit-3: default Here after suit-2 you need to reload
		 * default.
		 */
		if (!localResources.equalsIgnoreCase(resourceFile.getAbsolutePath())) {
			p.addProperty("local.reasources", resourceFile.getAbsolutePath());
			if (resourceFile.exists()) {
				if (resourceFile.isDirectory()) {
					boolean loadSubDirs = p.getBoolean("resources.load.subdirs", true);
					File[] propFiles = FileUtil.listFilesAsArray(resourceFile,
							".properties", StringComparator.Suffix, loadSubDirs);
					log.info("Resource dir: " + resourceFile.getAbsolutePath()
							+ ". Found property files to load: " + propFiles.length);
					File[] locFiles = FileUtil.listFilesAsArray(resourceFile, ".loc",
							StringComparator.Suffix, loadSubDirs);
					File[] wscFiles = FileUtil.listFilesAsArray(resourceFile, ".wsc",
							StringComparator.Suffix, loadSubDirs);
					PropertyUtil p1 = new PropertyUtil();
					p1.load(propFiles);
					p1.load(locFiles);
					p1.load(wscFiles);
					p.copy(p1);

					propFiles = FileUtil.listFilesAsArray(resourceFile, ".xml",
							StringComparator.Suffix, loadSubDirs);
					log.info("Resource dir: " + resourceFile.getAbsolutePath()
							+ ". Found property files to load: " + propFiles.length);

					p1 = new PropertyUtil();
					p1.load(propFiles);
					p.copy(p1);

				} else {
					try {
						if (fileOrDir.endsWith(".properties")
								|| fileOrDir.endsWith(".xml")
								|| fileOrDir.endsWith(".loc")
								|| fileOrDir.endsWith(".wsc")) {
							p.load(new File[]{resourceFile});
						}
					} catch (Exception e) {
						log.error(
								"Unable to load " + resourceFile.getAbsolutePath() + "!",
								e);
					}
				}
				// add locals if any
				if (null != locals && locals.length > 0
						&& (locals.length == 1 || StringUtil.isBlank(p.getString(
								ApplicationProperties.DEFAULT_LOCALE.key, "")))) {
					p.setProperty(ApplicationProperties.DEFAULT_LOCALE.key, locals[0]);
				}
				for (String local : locals) {
					log.info("loading local: " + local);
					addLocal(p, local, fileOrDir);
				}

			} else {
				log.error(resourceFile.getAbsolutePath() + " not exist!");
			}
		}
	}

	private static void addLocal(PropertyUtil p, String local, String fileOrDir) {
		String defaultLocal = p.getString(ApplicationProperties.DEFAULT_LOCALE.key, "");//
		File resourceFile = new File(fileOrDir);
		/**
		 * will reload existing properties value(if any) if the last loaded
		 * dir/file is not the current one. case: suit-1 default, suit-2 :
		 * s2-local, suit-3: default Here after suit-2 you need to reload
		 * default.
		 */
		boolean loadSubDirs = p.getBoolean("resources.load.subdirs", true);

		if (resourceFile.exists()) {
			PropertyUtil p1 = new PropertyUtil();
			p1.setEncoding(
					p.getString(ApplicationProperties.LOCALE_CHAR_ENCODING.key, "UTF-8"));
			if (resourceFile.isDirectory()) {
				File[] propFiles = FileUtil.listFilesAsArray(resourceFile, "." + local,
						StringComparator.Suffix, loadSubDirs);
				p1.load(propFiles);

			} else {
				try {
					if (fileOrDir.endsWith(local)) {
						p1.load(fileOrDir);
					}
				} catch (Exception e) {
					log.error("Unable to load " + resourceFile.getAbsolutePath() + "!",
							e);
				}
			}
			if (local.equalsIgnoreCase(defaultLocal)) {
				p.copy(p1);
			} else {
				Iterator<?> keyIter = p1.getKeys();
				Configuration localSet = p.subset(local);
				while (keyIter.hasNext()) {
					String key = (String) keyIter.next();
					localSet.addProperty(key, p1.getObject(key));
				}
			}

		} else {
			log.error(resourceFile.getAbsolutePath() + " not exist!");
		}
	}

	public static void addAll(Map<String, ?> props) {
		ConfigurationManager.getBundle().addAll(props);
	}

	public static PropertyUtil getBundle() {
		return ConfigurationManager.LocalProps.get();
	}

	public static void setBundle(PropertyUtil bundle) {
		LocalProps.set(bundle);
	}

	private static Map<String, String> getBuildInfo() {
		Manifest manifest = null;
		Map<String, String> buildInfo = new HashMap<String, String>();
		JarFile jar = null;
		try {
			URL url = ConfigurationManager.class.getProtectionDomain().getCodeSource()
					.getLocation();
			File file = new File(url.toURI());
			jar = new JarFile(file);
			manifest = jar.getManifest();
		} catch (NullPointerException ignored) {
		} catch (URISyntaxException ignored) {
		} catch (IOException ignored) {
		} catch (IllegalArgumentException ignored) {
		} finally {
			if (null != jar)
				try {
					jar.close();
				} catch (IOException e) {
					log.warn(e.getMessage());
				}
		}

		if (manifest == null) {
			return buildInfo;
		}

		try {
			Attributes attributes = manifest.getAttributes("Build-Info");
			Set<Entry<Object, Object>> entries = attributes.entrySet();
			for (Entry<Object, Object> e : entries) {
				buildInfo.put(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
			}
		} catch (NullPointerException e) {
			// Fall through
		}

		return buildInfo;
	}

	
	

	private static class PropertyConfigurationListener implements ConfigurationListener {
		String oldValue;

		@SuppressWarnings("unchecked")
		@Override
		public void configurationChanged(ConfigurationEvent event) {

			if ((event.getType() == AbstractConfiguration.EVENT_CLEAR_PROPERTY
					|| event.getType() == AbstractConfiguration.EVENT_SET_PROPERTY)
					&& event.isBeforeUpdate()) {
				oldValue = String.format("%s",
						getBundle().getObject(event.getPropertyName()));
			}

			if ((event.getType() == AbstractConfiguration.EVENT_ADD_PROPERTY
					|| event.getType() == AbstractConfiguration.EVENT_SET_PROPERTY)
					&& !event.isBeforeUpdate()) {
				String key = event.getPropertyName();
				Object value = event.getPropertyValue();
				if (null != oldValue && Matchers.equalTo(oldValue).matches(value)) {
					// do nothing
					return;
				}

				// driver reset
				if (key.equalsIgnoreCase(ApplicationProperties.DRIVER_NAME.key)
						// single capability or set of capabilities change
						|| StringMatcher.containsIgnoringCase(".capabilit").match(key)
						|| key.equalsIgnoreCase(ApplicationProperties.REMOTE_SERVER.key)
						|| key.equalsIgnoreCase(ApplicationProperties.REMOTE_PORT.key)) {
					DriverManager.unload();
					if(key.equalsIgnoreCase(ApplicationProperties.DRIVER_NAME.key)){
						DriverManager.setDriver((String)value);
					}
				}
				String[] bundles = null;
				// Resource loading
				if (key.equalsIgnoreCase("env.resources")) {

					if (event.getPropertyValue() instanceof ArrayList<?>) {
						ArrayList<String> bundlesArray =
								((ArrayList<String>) event.getPropertyValue());
						bundles = bundlesArray.toArray(new String[bundlesArray.size()]);
					} else {
						String resourcesBundle = (String) value;
						if (StringUtil.isNotBlank(resourcesBundle))
							bundles = resourcesBundle.split(String
									.valueOf(PropertyUtil.getDefaultListDelimiter()));
					}
					if (null != bundles && bundles.length > 0) {
						for (String res : bundles) {
							log.info("Adding resources from: " + res);
							ConfigurationManager.addBundle(res);
						}
						//executeOnChangeListeners();
					}
				}
				// Locale loading
				if (key.equalsIgnoreCase(ApplicationProperties.DEFAULT_LOCALE.key)) {
					String[] resources =
							getBundle().getStringArray("env.resources", "resources");
					for (String resource : resources) {
						String fileOrDir = getBundle().getSubstitutor().replace(resource);
						addLocal(getBundle(), (String) event.getPropertyValue(),
								fileOrDir);
					}
					//executeOnChangeListeners();
				}
				//
					
			}

		}
	}
	
	

}

