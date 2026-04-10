package com.qa.chroma.listeners;

import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.chroma.util.TestUtil;

public class Listeners  implements ITestListener {
	
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest test;
	String fileName;

	public void onStart(ITestContext context) {
		
		//String time = new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date());
		//fileName = "myReport" + time + ".html";
		
		fileName = "myReport.html";
		
		String currentDir = System.getProperty("user.dir");
		spark = new ExtentSparkReporter(currentDir +".\\reports\\" + fileName);
		spark.config().setDocumentTitle("QA Automation report");
		spark.config().setReportName("Functional Test");
		spark.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("ENV", "QA");
		extent.setSystemInfo("Browser", "chrome");
		extent.setSystemInfo("Reporter", "Dinesh");
		extent.flush();
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.PASS, " passed" + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "failed " + result.getName());
		test.log(Status.INFO, "reason" + result.getThrowable());
		try {
			String imgPath= new TestUtil().takeScreenshotAtEndOfTest(result.getName());
			//String imgPath= TestUtil.takeScreenshotAtEndOfTest(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			test.fail("Screenshot",
			        MediaEntityBuilder
			                .createScreenCaptureFromPath("screenshots/" + imgPath)
			                .build());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, " skipped" + result.getName());
		test.log(Status.INFO, "reason" + result.getThrowable());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}
	

}
