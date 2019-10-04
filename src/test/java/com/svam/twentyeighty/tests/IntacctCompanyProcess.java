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

import com.relevantcodes.extentreports.LogStatus;
import com.svam.driver.TestDriver;
import com.svam.framework.Apps;
import com.svam.pages.Common;
import com.svam.pages.IntacctGems;
import com.svam.pages.IntacctLogin;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class IntacctCompanyProcess extends TestDriver {
	static Logger LOGGER = Logger.getLogger(IntacctCompanyProcess.class);

	IntacctLogin il;
	IntacctGems gems;

	@BeforeTest
	public void initilizeBrowser() throws Exception {
		try {
			TestDriver.appInstantiation(Apps.Intacct);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 0)
	public void createCompany() {
		try {
			il = PageFactory.initElements(TestDriver.getInstance().getDriver(), IntacctLogin.class);

			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the Login Page");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Login Page of Stage Intacct", il.validateUI());
			gems = il.login("ESI-DEV", "SSISETL", "TCd9Da60$Tq");

			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the Home Page");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Stage Intacct", gems.validateUI());
			Common common = PageFactory.initElements(TestDriver.driver, Common.class);
			common.clickOnNewMenuOptions("GEMS", "Companies", "Companies");
			gems.createNewCompany();

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
