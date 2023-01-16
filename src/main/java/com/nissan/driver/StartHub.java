package com.nissan.driver;

import org.openqa.selenium.grid.Main;
import org.openqa.selenium.net.PortProber;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StartHub {

	public static void main(String[] args) {
		
		int port = PortProber.findFreePort();
		port=4444;
		WebDriverManager.chromedriver().setup();
		Main.main(new String[] {"standalone","--port",String.valueOf(port) });
	}
	
	public static void startHub() {
		
		int port=4444;
		WebDriverManager.chromedriver().setup();
		Main.main(new String[] {"standalone","--port",String.valueOf(port) });
	}
	
	
	
	
}
