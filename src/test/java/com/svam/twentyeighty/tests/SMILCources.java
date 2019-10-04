package com.svam.twentyeighty.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.svam.driver.TestDriver;
import com.svam.framework.Apps;
import com.svam.pages.Common;
import com.svam.pages.SMILCPC;
import com.svam.pages.SMILCourses;
import com.svam.pages.SMILLogin;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class SMILCources extends TestDriver {
	static Logger LOGGER = Logger.getLogger(SMILCources.class);
	Common common;
	ExtentTest extentTest;
	SMILLogin sm;
	SMILCourses courses;

	@BeforeTest
	public void initilizeBrowser() throws Exception {
		try {
			TestDriver.appInstantiation(Apps.SMIL);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 0)
	public void IntacctEnrollmentProcess() throws Exception {
		try {
			sm = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILLogin.class);
			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the SMIL Login");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Registration Completed", sm.validateUI());
			sm.login("SinghT", "Welcome@123");

			common = PageFactory.initElements(TestDriver.getInstance().getDriver(), Common.class);

			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the SMIL Home Page");

			// attachScreenShot("Verifying SMIL Home Page",TRUE);
			courses = (SMILCourses) common.clickMenuTab("COURSES");
			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the CPC");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@BeforeMethod
	public void startReporting(Method method) {
		ExtentManager.extentTest = ExtentManager.getInstance().startTest(method.getName());
	}

	@AfterMethod
	public void testResult(Method method, ITestResult result) throws IOException {

		String screenShotPath = new File("Framework\\Test_Reports\\Screenshots\\ ").getAbsolutePath() + method.getName()
				+ DateFormat.getDateTimeInstance().format(new Date()).toString().replaceAll(":", "_")
						.replaceAll("\\s+", "_").replaceAll(",", "")
				+ ".png";

		try {
			UIUtils.takeScreenshot(TestDriver.driver, screenShotPath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			ExtentManager.extentTest.log(LogStatus.PASS, "Test Case " + method.getName() + " is pass"
					+ ExtentManager.extentTest.addScreenCapture(screenShotPath));
			break;
		case ITestResult.FAILURE:
			ExtentManager.extentTest.log(LogStatus.FAIL, "Test Case " + method.getName() + " is failed");
			break;
		case ITestResult.SKIP:
			ExtentManager.extentTest.log(LogStatus.SKIP, "Test Case " + method.getName() + " is skiped");
			break;
		default:
			break;
		}
		ExtentManager.getInstance().endTest(ExtentManager.extentTest);
		driver.close();
	}

	@AfterClass
	public void generateResult() throws IOException {
		ExtentManager.getInstance().flush();
		ExtentManager.getInstance().close();
		driver.quit();
	}

}
