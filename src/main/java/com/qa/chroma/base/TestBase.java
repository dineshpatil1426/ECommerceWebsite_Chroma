package com.qa.chroma.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.chroma.constants.AppConstants;


public class TestBase {
	
	
	public static WebDriver driver;
	public static Properties prop;
	
	public TestBase() {
		try {
			prop = new Properties();
			String currentDir = System.getProperty("user.dir");
			FileInputStream ip = new FileInputStream(currentDir+"\\src\\test\\resources\\config\\config.properties");
			
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("FileNotFoundException Path Error...");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException Path Error...");
		}
	}
	
	public static void initialazation() {

		String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			
			  ChromeOptions options = new ChromeOptions(); 
			  options.addArguments("--disable-autofill-keyboard-accessory-view");
			  options.addArguments("--disable-save-password-bubble");
			  Map<String, Object> prefs = new HashMap<>(); 
			  prefs.put("credentials_enable_service", false);
			  prefs.put("profile.password_manager_enabled", false);
			  prefs.put("profile.default_content_setting_values.notifications", 2);
			  prefs.put("autofill.profile_enabled", false);
			  options.setExperimentalOption("prefs", prefs);
			  
			driver = new ChromeDriver();
		} else if (browserName.equals("FF")) {
			driver = new FirefoxDriver();
		} else if (browserName.equals("edge")) {
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(AppConstants.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppConstants.IMPLICIT_WAIT));

		driver.get(prop.getProperty("url"));
	}

}
