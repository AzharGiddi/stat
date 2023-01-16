package com.nissan.automation.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.nissan.constants.NAFConstants;
import com.nissan.driver.DriverManager;
import com.nissan.reports.ExtentLogger;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public final class ScreenshotUtils {


	public static String getBase64String() {
	String base64="";
		try {
			base64= ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
		}catch(Exception e){
			ExtentLogger.info("Unable to take screenshot due to "+e.getLocalizedMessage(), false);
		}

		 return base64;
	}
	
	public static String getBase64StringForFullScreenShot() {
		
		Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(DriverManager.getDriver());
        try {
        	ByteArrayOutputStream outputFile = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(),"PNG",outputFile);
            byte[] bytes = outputFile.toByteArray();
    		return  Base64.encodeBase64String(bytes);	
            
        } catch (IOException e) {

            e.printStackTrace();
        }
		//keeping this as back up if above fullscreenshot method fails
		return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);

		 
	}




private ScreenshotUtils() {
	
}
}
