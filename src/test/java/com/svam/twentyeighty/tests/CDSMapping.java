package com.svam.twentyeighty.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
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

public class CDSMapping extends TestDriver {
	static Logger LOGGER = Logger.getLogger(CDSMapping.class);
	Common common;
	ExtentTest extentTest;
	SMILLogin sm;
	SMILCourses cou;

	@BeforeTest
	public void appInitilization() throws Exception {
		try {
			sm = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILLogin.class);
			common = PageFactory.initElements(TestDriver.getInstance().getDriver(), Common.class);
			cou = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILCourses.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 0)
	public void mapping() {

		try {
			LOGGER.info("------------CDS Course Mapping Process-------------");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);

			sm.login("SinghT", "Welcome@321");
			WebElement results = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
			Assert.assertEquals(results.getText(), "Service Management Integration for Learning");

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cou.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Courses\" menu");
			cou = (SMILCourses) common.clickMenuTab("Courses");
			
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Serach for all courses");
			//Assert.assertEquals(cou.advanceSerachForAllCourses(), "Advance Serach for all Courses");
			cou.advanceSerachForAllCourses();
			
			cou.clickEditforAllSerachResult();
			

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 1 is failed due to below problem",
					testResult);
		}
	}

	@BeforeMethod
	public void startReporting(Method method) {
		ExtentManager.extentTest = ExtentManager.getInstance().startTest(method.getName());
		System.out.println("------------Running @BeforeMethod of Regression-------------");
	}

	@AfterMethod
	public void testResult(Method method, ITestResult result) throws IOException {
		System.out.println("------------Running @AfterMethod of Regression-------------");
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
	}

	@AfterClass
	public void generateResult() {
		System.out.println("------------Running @AfterClass of Regression-------------");
		ExtentManager.getInstance().flush();
		ExtentManager.getInstance().close();
		driver.close();
	}
}
