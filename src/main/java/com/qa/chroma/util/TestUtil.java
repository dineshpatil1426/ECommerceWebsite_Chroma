package com.qa.chroma.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.chroma.base.TestBase;

public class TestUtil extends TestBase {
	
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 10;
	
	public String takeScreenshotAtEndOfTest(String ss) throws IOException {
		
		
		String timestamp=new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date());
		
		TakesScreenshot ts= (TakesScreenshot)driver;
		
		File src=ts.getScreenshotAs(OutputType.FILE);
		
		String fileDrop=System.getProperty("user.dir")+".\\screenshots\\"+"ss"+timestamp+".png";
		File trg=new File(fileDrop);
		
		FileUtils.copyFile(src, trg);
		return (fileDrop);
		
	}

}
