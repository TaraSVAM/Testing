package com.svam.twentyeighty.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.svam.pages.PublicWebsite;
import com.svam.pages.SMILCPC;
import com.svam.pages.SMILCourses;
import com.svam.pages.SMILLogin;
import com.svam.utils.ExcelUtils;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class PublicWebsiteToSMIL extends TestDriver {

	static Logger LOGGER = Logger.getLogger(PublicWebsiteToSMIL.class);
	Common common;
	ExtentTest extentTest;
	PublicWebsite pw;
	SMILLogin sm;
	SMILCPC cpc;
	SMILCourses courses;

	@BeforeTest
	public void loginSMIL() throws Exception {
		TestDriver.appInstantiation(Apps.PublicWebsite);
	}

	@Test(priority = 0)
	public void newRegistrationVerificationPublicWebsiteToSMIL() throws Exception {

		try {
			pw = PageFactory.initElements(TestDriver.getInstance().getDriver(), PublicWebsite.class);
			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the Cource Detail from Public Website");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Public Website", pw.validateUI());
			Assert.assertTrue(pw.registrationData(), "The Registration Process through Public Website is completed Suscessfully");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Registration Completed", pw.validateUI());

			// SMIL Login and Verify the Registration Detail
			TestDriver.appInstantiation(Apps.SMIL);
			sm = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILLogin.class);
			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the SMIL Login");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Registration Completed", sm.validateUI());
			sm.login("SinghT", "Welcome@123");
			common = PageFactory.initElements(TestDriver.getInstance().getDriver(), Common.class);
			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the SMIL Home Page");

			// CPC Automation
			cpc = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILCPC.class);
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");
			ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the CPC");
			String PublicWebsiteWorkbook = new File("Framework\\Test_Data\\Integration.xls").getCanonicalPath();
			FileInputStream fis = new FileInputStream(PublicWebsiteWorkbook);
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("PublicWebsite");
			int lastRow = sheet.getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {
				cpc.advanceSearch(
						String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 0)),
						String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 4)), "", "", "", "",
						"", "", "", "");
			}
			workbook.close();
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
		// driver.close();
	}

	@AfterClass
	public void generateResult() {
		ExtentManager.getInstance().flush();
		ExtentManager.getInstance().close();
		// driver.close();
	}

}
