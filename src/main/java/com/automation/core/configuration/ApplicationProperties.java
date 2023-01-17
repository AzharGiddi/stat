package com.automation.core.configuration;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestResult;

public enum ApplicationProperties {
	/**
	 * <b>key</b>: <code>report.log.level</code><br/>
	 * <b>value</b>: one of Info, Pass, Fail
	 * <DL>
	 * <DT>Info
	 * <DD>will report Info, pass and fail messages
	 * <DT>Pass
	 * <DD>will report pass and fail messages but not Info
	 * <DT>Fail
	 * <DD>will report fail messages but not Info or Pass
	 * </DL>
	 */
	REPORT_LOG_LEVEL("report.log.level"),
	/**
	 * <b>key</b>: <code>report.log.skip.success</code><br/>
	 * <b>value</b>: boolean, when true it will not show verification success
	 * message in report.
	 */
	REPORT_SKIP_SUCCESS("report.log.skip.success"),
	/**
	 * <b>key</b>: <code>test.results.dir </code><br/>
	 * <b>value</b>: dir to place generated result files
	 */
	REPORT_DIR("test.results.dir"), JSON_REPORT_ROOT_DIR("json.report.root.dir"), JSON_REPORT_DIR("json.report.dir"),
	/**
	 * <b>key</b>: <code> selenium.screenshots.dir </code><br/>
	 * <b>value</b>: dir to place screen-shots
	 */
	SCREENSHOT_DIR("selenium.screenshots.dir"),
	/**
	 * <b>key</b>: <code> selenium.screenshots.relative.path </code><br/>
	 * <b>value</b>: screen-shots relative path for reporting
	 */
	SCREENSHOT_RELATIVE_PATH("selenium.screenshots.relative.path"),
	/**
	 * <b>key</b>: <code>selenium.success.screenshots </code><br/>
	 * <b>value</b>: set this flag to 1 if you want to capture screen-shots for
	 * assertion/verification success.
	 */
	SUCEESS_SCREENSHOT("selenium.success.screenshots"),
	/**
	 * <b>key</b>: <code>selenium.failure.screenshots </code><br/>
	 * <b>value</b>: set this flag to 1 if you want to capture screen-shots for
	 * assertion/verification failures. Default value is 1
	 */
	FAILURE_SCREENSHOT("selenium.failure.screenshots"),
	/**
	 * <b>key</b>: <code>selenium.wait.timeout </code><br/>
	 * <b>value</b>: default wait time to be used by framework by wait/assert/verify
	 * methods
	 */
	SELENIUM_WAIT_TIMEOUT("selenium.wait.timeout"),
	/**
	 * <b>key</b>: <code> driver.name </code><br/>
	 * <b>value</b>: driver to be used, for instance firefoxDriver or
	 * firefoxRemoteDriver etc...
	 * 
	 * @since 2.1.6
	 */
	DRIVER_NAME("driver.name"),
	/**
	 * <b>key</b>: <code> driver.init.retry.timeout </code><br/>
	 * <b>value</b>: duration in multiplication of 10 seconds for example 50.
	 * 
	 * @since 2.1.9
	 */
	DRIVER_INIT_TIMEOUT("driver.init.retry.timeout"),

	/**
	 * <b>key</b>: <code> driver.additional.capabilities </code><br/>
	 * <b>value</b>: specify multiple additional capabilities as map that can
	 * applicable for any driver.
	 * 
	 * @see {@link #DRIVER_CAPABILITY_PREFIX} to provide individual capability <br/>
	 *      {@link #DRIVER_ADDITIONAL_CAPABILITIES_FORMAT} to provide driver
	 *      specific capability
	 */
	DRIVER_ADDITIONAL_CAPABILITIES("driver.additional.capabilities"),
	/**
	 * <b>key</b>: <code> driver.capabilities </code><br/>
	 * <b>value</b>: specify additional capability by name with this prefix that can
	 * applicable for any driver. For example,
	 * driver.capabilities.&lt;capabilityName&gt;=&lt;value&gt;
	 * 
	 * @see {@link #DRIVER_ADDITIONAL_CAPABILITIES} to provide multiple additional
	 *      capabilities
	 */
	DRIVER_CAPABILITY_PREFIX("driver.capabilities"),

	/**
	 * <b>key</b>: <code> &lt;drivername&gt;.additional.capabilities </code> <br/>
	 * <b>value</b>: specify multiple additional capabilities as map that can
	 * applicable for specific driver.
	 * 
	 * @see {@link #DRIVER_CAPABILITY_PREFIX} to provide single capability <br/>
	 *      {@link #DRIVER_ADDITIONAL_CAPABILITIES} to provide capability for all
	 *      drivers
	 */
	DRIVER_ADDITIONAL_CAPABILITIES_FORMAT("%s.additional.capabilities"),

	/**
	 * <b>key</b>: <code> &lt;drivername&gt;.capabilities </code> <br/>
	 * <b>value</b>: specify additional capability by name with this prefix that can
	 * applicable for specific driver. For example,
	 * &lt;drivername&gt;.capabilities.&lt;capabilityName&gt;=&lt;value&gt;
	 * 
	 * @see {@link #DRIVER_CAPABILITY_PREFIX} to provide individual capability <br/>
	 *      {@link #DRIVER_ADDITIONAL_CAPABILITIES_FORMAT} to provide driver
	 *      specific capability
	 */
	DRIVER_CAPABILITY_PREFIX_FORMAT("%s.capabilities"),

	/**
	 * <b>key</b>: <code> driverClass </code> <br/>
	 * <b>value</b>: capability name to specify driver class name.
	 */
	CAPABILITY_NAME_DRIVER_CLASS("driverClass"),

	/**
	 * <b>key</b>: <code> remote.server</code><br/>
	 * <b>value</b>: remote server url, which will be considered if configured
	 * remote driver.
	 * 
	 * @since 2.1.6
	 */
	REMOTE_SERVER("remote.server"),

	/**
	 * <b>key</b>: <code> remote.port</code><br/>
	 * <b>value</b>: remote server port, which will be considered if configured
	 * remote driver.
	 * 
	 * @since 2.1.6
	 */
	REMOTE_PORT("remote.port"),

	/**
	 * <b>key</b>: <code> env.baseurl </code><br/>
	 * <b>value</b>: base URL of AUT to be used.
	 */
	SELENIUM_BASE_URL("env.baseurl"), // selenium.browser.url
	WEBDRIVER_REMOTE_SESSION("webdriver.remote.session"),
	/**
	 * <b>key</b>: <code> webdriver.chrome.driver </code><br/>
	 * <b>value</b>: Set Chrome driver path.
	 */
	CHROME_DRIVER_PATH("webdriver.chrome.driver"),
	/**
	 * <b>key</b>: <code> env.load.locales</code><br/>
	 * <b>value</b>: list of local names to be loaded
	 */
	LOAD_LOCALES("env.load.locales"),

	/**
	 * <b>key</b>: <code> env.default.locale</code><br/>
	 * <b>value</b>: local name from loaded locals that need to treated as default
	 * local
	 */
	DEFAULT_LOCALE("env.default.locale"), LOCALE_CHAR_ENCODING("locale.char.encoding"),
	/**
	 * <b>key</b>: <code>retry.count </code><br/>
	 * <b>value</b>: integer to specify how many times test should be retried on
	 * failure by default retry analyzer. This will not take effect if custom retry
	 * analyzer is provided using {@link ApplicationProperties#RETRY_ANALYZER
	 * retry.analyzer }
	 */
	RETRY_CNT("retry.count"),
	/**
	 * <b>key</b>: <code>retry.analyzer</code><br/>
	 * <b>value</b>: fully qualified class name that implements
	 * {@link IRetryAnalyzer}. Provide this property to use your custom retry
	 * analyzer.
	 */
	RETRY_ANALYZER("retry.analyzer"), DRY_RUN_MODE("dryrun.mode"),

	/**
	 * <p>
	 * Set true to trust all certificates and ignore host name verification for
	 * web-services.
	 * </p>
	 * <b>key</b>: <code>https.accept.all.cert</code><br/>
	 * <b>value</b>: boolean true/false.
	 * 
	 * @since 2.1.13
	 * 
	 */
	HTTPS_ACCEPT_ALL_CERT("https.accept.all.cert"),

	/**
	 * <p>
	 * Set proxy server that needs to used by {@link UriProxySelector}
	 * </p>
	 * <b>key</b>: <code>proxy.server</code><br/>
	 * <b>value</b>: proxy server.
	 * 
	 * @since 2.1.14
	 * 
	 */
	PROXY_SERVER_KEY("proxy.server"),
	/**
	 * <p>
	 * Set proxy server port that needs to used by {@link UriProxySelector}. Default
	 * value is 80.
	 * </p>
	 * <b>key</b>: <code>proxy.port</code><br/>
	 * <b>value</b>: integer port of running proxy server.
	 * 
	 * @since 2.1.14
	 * 
	 */
	PROXY_PORT_KEY("proxy.port"),
	/**
	 * <p>
	 * Set one or more host url that needs to be proxied through given proxy server.
	 * </p>
	 * <b>key</b>: <code>host.to.proxy</code><br/>
	 * <b>value</b>: one or more host URL separated by ';'
	 * 
	 * @since 2.1.14
	 * 
	 */
	PROXY_HOSTS_KEY("host.to.proxy");

	public String key;

	private ApplicationProperties(String key) {
		this.key = key;
	}

	/**
	 * @param defaultVal
	 *            optional
	 * @return
	 */
	public String getStringVal(String... defaultVal) {
		return System.getProperty(key, getObjectVal(defaultVal).toString());
	}

	private Object getObjectVal(String... defaultVal) {
		List<?> list = ConfigurationManager.getBundle().getList(key,
				(null == defaultVal || defaultVal.length < 1 || null == defaultVal[0]) ? Collections.emptyList()
						: Arrays.asList(defaultVal));
		return list != null && !list.isEmpty() ? list.get(list.size() - 1) : "";
	}

	/**
	 * @param defaultVal
	 *            optional
	 * @return
	 */
	public int getIntVal(int... defaultVal) {
		try {
			return Integer.parseInt(getStringVal());

		} catch (Exception e) {
			// just ignore
		}
		return (null != defaultVal) && (defaultVal.length > 0) ? defaultVal[0] : 0;
	}

	/**
	 * @param defaultVal
	 *            optional
	 * @return
	 */
	public boolean getBoolenVal(boolean... defaultVal) {
		try {
			String sVal = getStringVal().trim();
			return StringUtils.isNumeric(sVal) ? (Integer.parseInt(sVal) != 0) : Boolean.parseBoolean(sVal);
		} catch (Exception e) {
			// just ignore
		}
		return (null != defaultVal) && (defaultVal.length > 0) && defaultVal[0];
	}

	public Object getObject(Object... defaultVal) {
		Object objToReturn = ConfigurationManager.getBundle().getObject(key);
		if( null != objToReturn) {
			return objToReturn;
		}
		
		return (null != defaultVal) && (defaultVal.length > 0) ? defaultVal[0] : null;
		
		/*return null != objToReturn ? objToReturn
				: (null != defaultVal) && (defaultVal.length > 0) ? defaultVal[0] : null;*/
	}
}
