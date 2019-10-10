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
import com.svam.pages.PublicWebsite;
import com.svam.pages.Reseller;
import com.svam.pages.SMILCPC;
import com.svam.pages.SMILCourses;
import com.svam.pages.SMILLogin;
import com.svam.pages.SMILResellerApprovals;
import com.svam.utils.ExtentManager;
import com.svam.utils.RandomNumber;
import com.svam.utils.UIUtils;

public class Regression extends TestDriver {

	static Logger LOGGER = Logger.getLogger(Regression.class);
	Common common;
	ExtentTest extentTest;
	PublicWebsite pw;
	SMILLogin sm;
	SMILCPC cpc;
	SMILCourses cou;
	Reseller rp;
	SMILResellerApprovals cpcr;
	Date d = new Date();
	long randomNumber = RandomNumber.getRandomNumber(4);

	@BeforeTest
	public void appInitilization() throws Exception {
		try {
			sm = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILLogin.class);
			cpc = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILCPC.class);
			common = PageFactory.initElements(TestDriver.getInstance().getDriver(), Common.class);
			rp = PageFactory.initElements(TestDriver.getInstance().getDriver(), Reseller.class);
			cpcr = PageFactory.initElements(TestDriver.getInstance().getDriver(), SMILResellerApprovals.class);
			pw = PageFactory.initElements(TestDriver.getInstance().getDriver(), PublicWebsite.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @CPC_001 Verify that Intacct ID field is added in CPC
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 29-July-2019
	 */

	@Test(priority = 0)
	public void CPC_Intacct_Id_Added() {

		try {
			LOGGER.info("------------Running Test Case 1-------------");
			String cartID = "13478";
			String intacctID = "3839829";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");

			}
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\" menu");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CartID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Verify whether Intacct ID field is visible in the record");
			Assert.assertTrue(cpc.fieldExistanceVerification("IntacctID"), "The Presence of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Verify the Intacct Detail");
			Assert.assertTrue(cpc.intacctIDVerification("Valid", intacctID), "Intacct Detail");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 1 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_003 Verify the intacct look up functionality work for the valid intacct
	 *          id/dummy intacct id
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 29-July-2019
	 */
	@Test(priority = 1)
	public void CPC_Intacct_ID_Details_Work() {
		try {
			LOGGER.info("------------Running Test Case 2-------------");
			String cartID = "13478";
			String intacctID_Valid = "3839829";
			String intacctID_InValid = "123";
			String intacctID_Blank = " ";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"), 50);
			sm.login("SinghT", "Welcome@321");
			WebElement results = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
			Assert.assertEquals(results.getText(), "Service Management Integration for Learning");

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CarID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Verify whether Intacct ID field is visible in the record");
			Assert.assertTrue(cpc.fieldExistanceVerification("IntacctID"), "The Presence of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Verify the Intacct Detail");
			Assert.assertTrue(cpc.intacctIDVerification("Valid", intacctID_Valid), "Intacct Detail");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Verify the Invalid Intacct ID");
			Assert.assertTrue(cpc.intacctIDVerification("Invalid", intacctID_InValid), "Intacct Detail");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Verify the Blank Intacct ID");
			Assert.assertTrue(cpc.intacctIDVerification("Blank", intacctID_Blank), "Intacct Detail");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 2 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_005 Verify the intacct look up functionality work for the valid intacct
	 *          id/dummy intacct id
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 31-July-2019
	 */
	@Test(priority = 2)
	public void verifyIntacctID() {
		try {
			LOGGER.info("------------Running Test Case 3-------------");
			String cartID = "13478";
			String IntacctId = "3839";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CarID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Verify whether Intacct ID field is visible in the record");
			Assert.assertTrue(cpc.fieldExistanceVerification("IntacctID"), "The Presence of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Provide Intacct id less than 5 numbers");
			Assert.assertTrue(cpc.enterIntacctID(IntacctId), "The validation of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Click the Save Button");
			Assert.assertTrue(cpc.clickSaveButton(), "The Registration is saved");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 9. Verify the validation of Invalid Intacct IDIntacct id less than 5 numbers");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Click the Save Button");
//			Assert.assertTrue(cpc.selectStatus("Awaiting Access"), "The Registration is saved");
//						
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 11. Click the Save Button");
//			Assert.assertTrue(cpc.clickSaveButton(), "The Registration is saved");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 3 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_006 and CPC_020 Verify that system accepts dummy Intacct ID and saves
	 *          the record
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 31-July-2019
	 */
	@Test(priority = 3)
	public void CPC_Dummy_Intacct_ID_Save_Record() {
		try {
			LOGGER.info("------------Running Test Case 4-------------");
			String cartID = "13478";
			String IntacctID_LessThan5 = "321";
			String IntacctID_GreaterThan5 = "123433356789";
			String IntactID_Special = "12@#3";
			String IntacctID_symbols = "1$♥42";
			String IntacctID_alphabets = "Test";

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CarID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Verify whether Intacct ID field is visible in the record");
			Assert.assertTrue(cpc.fieldExistanceVerification("IntacctID"), "The Presence of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Provide a dummy Intacct ID (minimum 5 numbers)");
			Assert.assertTrue(cpc.enterIntacctID(IntacctID_GreaterThan5), "The validation of Intacct ID");
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Click the Save Button");
			cpc.clickSaveButton();
			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 9. Verify the validation of dummy Intacct ID (minimum 5 numbers)");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Provide Intacct id less than 5 numbers");
			Assert.assertTrue(cpc.enterIntacctID(IntacctID_LessThan5), "The validation of Intacct ID");
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 11. Click the Save Button");
			cpc.clickSaveButton();
			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 12. Verify the validation of Invalid Intacct IDIntacct id less than 5 numbers");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 13. Provide Intacct id as Special characters(_,!@#$%^&*)");
			Assert.assertTrue(cpc.enterIntacctID(IntactID_Special), "The validation of Intacct ID");
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 14. Click the Save Button");
			cpc.clickSaveButton();
			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 15. Verify the validation of Intacct id as Special characters(_,!@#$%^&*)");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 16. Provide Intacct ID as symbols");
			Assert.assertTrue(cpc.enterIntacctID(IntacctID_symbols), "The validation of Intacct ID");
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 17. Click the Save Button");
			cpc.clickSaveButton();
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 18. Verify the validation of Intacct ID as symbols");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 19. Provide Intacct ID as alphabets");
			Assert.assertTrue(cpc.enterIntacctID(IntacctID_alphabets), "The validation of Intacct ID");
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 20. Click the Save Button");
			cpc.clickSaveButton();
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 21. Verify the validation of Intacct ID as alphabetss");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 22. Enter blanck Intacct ID");
			Assert.assertTrue(cpc.enterIntacctID(" "), "The validation of Intacct ID");
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 23. Click the Save Button");
			cpc.clickSaveButton();
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 24. Verify the validation of Intacct ID");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 4 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * CPC_020 Verify that Intacct ID field is manadatory and is only mandatory
	 * while changing the status to "Processed/Access Granted" and other editable
	 * fields can also be edited without changing status
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 31-July-2019
	 */
	@Test(priority = 4)
	public void CPC_Intacct_ID_Manadtory_Field() {
		try {
			LOGGER.info("------------Running Test Case 5-------------");
			String cartID = "13478";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CarID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Verify whether Intacct ID field is visible in the record");
			Assert.assertTrue(cpc.fieldExistanceVerification("IntacctID"), "The Presence of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Enter blanck Intacct ID");
			Assert.assertTrue(cpc.enterIntacctID(" "), "The validation of Intacct ID");
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Click the Save Button");
			cpc.clickSaveButton();
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Verify the validation of Intacct ID");
			Assert.assertTrue(cpc.intacctIDValidation(), "The validation of Intacct ID");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 5 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_023 Verify if the session mapping changes once the Expected start date
	 *          is changed
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 13-Aug-2019
	 */

	@Test(priority = 5)
	public void CPC_Expectedstartdate() {

		try {
			LOGGER.info("------------Running Test Case 6-------------");
			String cartID = "13489";
			String expectedDate = "01/01/2020";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "cartID"), 20);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CartID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			WebElement beforeLegacyID = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "LegacyID"));
			WebElement beforeListPrice = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "ListPrice"));

			String bLegacyID = beforeLegacyID.getText();
			String bListPrice = beforeListPrice.getText();

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Change the Expected start date to future.");
			Assert.assertTrue(cpc.enterExpectedDate(expectedDate), "The Expected start date");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Click the Save Button");
			cpc.clickSaveButton();

			WebElement afterLegacyID = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "LegacyID"));
			WebElement afterListPrice = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "ListPrice"));

			LOGGER.info("The Legacy ID Before-------" + bLegacyID);
			LOGGER.info("The List Price Before-------" + bListPrice);
			LOGGER.info("The Legacy ID After-------" + afterLegacyID.getText());
			LOGGER.info("The List Price After-------" + afterListPrice.getText());

			if (!bLegacyID.contentEquals(afterLegacyID.getText())) {
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest,
						"The Legacy ID before Future Expected Date - \"" + bLegacyID
								+ "\" and the Legacy ID after Expected Date - \"" + afterLegacyID.getText() + "\"",
						result);
			} else {
				boolean result = false;
				UIUtils.attachScreenShot(ExtentManager.extentTest,
						"The before Legacy ID should be different than the After Legacy ID. The Legacy ID before Future Expected Date - \""
								+ bLegacyID + "\" and the Legacy ID after Expected Date - \"" + afterLegacyID.getText()
								+ "\"",
						result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 6 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_023(C) Verify if the session mapping is done as per the future start
	 * date
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 19-Aug-2019
	 */

	@Test(priority = 6)
	public void Futurestartdate_sessionmapping() {

		try {
			LOGGER.info("------------Running Test Case 7-------------");
			String cartID = "13489";
			String expectedStartDate = "10/7/2019";
			String expectedLegacyID = "ADX-GTW/D20191001A";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "cartID"), 20);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CartID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Change the Expected start date from CPC");
			Assert.assertTrue(cpc.enterExpectedDate(expectedStartDate), "The Expected start date");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Click the Save Button");
			cpc.clickSaveButton();

			WebElement afterLegacyID = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "LegacyID"));
			String actualLegacyID = afterLegacyID.getText();
			LOGGER.info("The Legacy ID After-------" + afterLegacyID.getText());

			if (actualLegacyID.contentEquals(expectedLegacyID)) {
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The record is mapped to valid session", result);
			} else {
				boolean result = false;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The record is mapped to valid session", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 7 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_021 Verify and match the UI elements of Intacct ID details page date
	 * 
	 * @author Tara Singh Bhandari
	 * @throws Exception
	 * @authored_date 19-Aug-2019
	 */

	@Test(priority = 7)
	public void CPC_Intacct_ID_Details_UIElements() throws Exception {

		try {
			LOGGER.info("------------Running Test Case 8-------------");
			String cartID = "13478";
			String expected_PersonID = "G3839829";
			String expected_IntacctID = "3839829";
			String expected_Salutation = "";
			String expected_FirstName = "Dhaval";
			String expected_MiddleInitial = "";
			String expected_LastName = "Shah";
			String expected_EmailAddress = "dhaval.shah@astellas.com.test";
			String expected_WorkPhone = "224-205-8576";
			String expected_MobilePhone = "";
			String expected_Fax = "";
			String expected_Suffix = "";
			String expected_JobStatus = "";
			String expected_Company = "Astellas US";
			String expected_JobTitle = "";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "cartID"), 20);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the CartID and verify the serach Result");
			Assert.assertTrue(cpc.basicSerach(cartID), "Verifying the Basic Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			Map<String, String> intacctDetail = cpc.getIntacctDetail();
			if (intacctDetail.containsKey("PersonID")) {
				Assert.assertEquals(expected_PersonID, intacctDetail.get("PersonID"), "The Person ID is matched");
				Assert.assertEquals(expected_IntacctID, intacctDetail.get("IntacctID"), "The Intacct ID is matched");
				Assert.assertEquals(expected_Salutation, intacctDetail.get("Salutation"), "The Salutation is matched");
				Assert.assertEquals(expected_FirstName, intacctDetail.get("FirstName"), "The First Name is matched");
				Assert.assertEquals(expected_MiddleInitial, intacctDetail.get("MiddleInitial"),
						"The MiddleInitial is matched");
				Assert.assertEquals(expected_LastName, intacctDetail.get("LastName"), "The LastName is matched");
				Assert.assertEquals(expected_EmailAddress, intacctDetail.get("EmailAddress"),
						"The EmailAddress is matched");
				Assert.assertEquals(expected_WorkPhone, intacctDetail.get("WorkPhone"), "The WorkPhone is matched");
				Assert.assertEquals(expected_MobilePhone, intacctDetail.get("MobilePhone"),
						"The MobilePhone is matched");
				Assert.assertEquals(expected_Fax, intacctDetail.get("Fax"), "The Fax is matched");
				Assert.assertEquals(expected_Suffix, intacctDetail.get("Suffix"), "The Suffix is matched");
				Assert.assertEquals(expected_JobStatus, intacctDetail.get("JobStatus"), "The JobStatus is matched");
				Assert.assertEquals(expected_Company, intacctDetail.get("Company"), "The Company is matched");
				Assert.assertEquals(expected_JobTitle, intacctDetail.get("JobTitle"), "The JobTitle is matched");
				boolean serachIntacctResult = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail verification",
						serachIntacctResult);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter",
						"SearchedIntacct_Close"), driver);
			} else {
				boolean serachIntacctResult = false;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail verification",
						serachIntacctResult);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter",
						"SearchedIntacct_Close"), driver);
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 8 is failed due to below problem",
					testResult);
		}

	}

	/**
	 * @CPC_013(a) Verify that after processing a a record , the CPC locks for the
	 * particular record
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 02-Aug-2019
	 */

	@Test(priority = 8)
	public void CPC_ProcessedAccesGranted() {

		try {
			LOGGER.info("------------Running Test Case 9-------------");
			String status = "Processed/Access Granted";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the Status as \"Processed/Access Granted\"");
			Assert.assertTrue(cpc.advanceSearch("", "", "", "", status, "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 5. Check if the edit icon is present and detail icon is present");
			Assert.assertTrue(cpc.registrationStatusVerification(status), "The Serached Result");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 9 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_013(b) Verify that after deleting a record , the CPC locks for the
	 * particular record.
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 03-Aug-2019
	 */

	@Test(priority = 9)
	public void CPC_Deleted() {

		try {
			LOGGER.info("------------Running Test Case 10-------------");
			String status = "Deleted";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the Status as \"Deleted\"");
			Assert.assertTrue(cpc.advanceSearch("", "", "", "", status, "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 5. Check if the edit icon is present and detail icon is present");
			Assert.assertTrue(cpc.registrationStatusVerification(status), "The Serached Result");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 10 is failed due to below problem",
					testResult);
		}
	}

	@Test(priority = 10)
	public void CPC_Pending() {

		try {
			LOGGER.info("------------Running Test Case 11-------------");
			String status = "Pending";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the Status as \"Pending\"");
			Assert.assertTrue(cpc.advanceSearch("", "", "", "", status, "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 5. Check if the edit icon is present and detail icon is present");
			Assert.assertTrue(cpc.registrationStatusVerification(status), "The Serached Result");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 11 is failed due to below problem",
					testResult);
		}
	}

	@Test(priority = 11)
	public void CPC_AwaitingAccess() {

		try {
			LOGGER.info("------------Running Test Case 12-------------");
			String status = "Awaiting Access";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the Status as \"Awaiting Access\"");
			Assert.assertTrue(cpc.advanceSearch("", "", "", "", status, "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 5. Check if the edit icon is present and detail icon is present");
			Assert.assertTrue(cpc.registrationStatusVerification(status), "The Serached Result");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 12 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_26 Verify if the user is able to edit notes with and without changing
	 *         the status.
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 03-Aug-2019
	 */

	@Test(priority = 12)
	public void Notes_CPC_Pending() {

		try {
			LOGGER.info("------------Running Test Case 13-------------");
			String status = "Pending";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the Status as \"Pending\"");
			Assert.assertTrue(cpc.advanceSearch("", "", "", "", status, "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 5. Check if the edit icon is present and detail icon is present");
			Assert.assertTrue(cpc.registrationStatusVerification("Awaiting Access"), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Add some notes without changing the status");
			Assert.assertTrue(cpc.enterNotes("This is Testing Note"), "The Note Result");

			cpc.clickSaveButton();

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Again go to edit");
			Assert.assertTrue(cpc.clickEditButton(), "The Record is Edited");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Update notes again with changing the status");
			Assert.assertTrue(cpc.enterNotes("The Note is Updated again."), "The Note Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Update the Status");
			Assert.assertTrue(cpc.selectStatus("Pending"), "The Record is Edited");

			cpc.clickSaveButton();

			String expectedResult = "The Note is Updated again.";
			WebElement actualResult = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "Notes"));

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Verify the Updated Note is Savd or not");
			Assert.assertEquals(actualResult.getText(), expectedResult, "The Note is verified");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 13 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * @CPC_26(a) Verify if the user is able to edit notes with and without changing
	 * the status.
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 03-Aug-2019
	 */

	@Test(priority = 13)
	public void Notes_CPC_AwaitingAccess() {

		try {
			LOGGER.info("------------Running Test Case 14-------------");
			String name = "Jones";
			String status = "Awaiting Access";
			String note = "This is Testing Note";
			String updatedNote = "The Note is Updated again.";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click on \"Processing Center\"");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Enter the Status as \"Awaiting Access\"");
			Assert.assertTrue(cpc.advanceSearch(name, "", "", "", status, "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 5. Check if the edit icon is present and detail icon is present");
			Assert.assertTrue(cpc.registrationStatusVerification("Awaiting Access"), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Add some notes without changing the status");
			Assert.assertTrue(cpc.enterNotes(note), "The Note Result");

			cpc.clickSaveButton();

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Again go to edit");
			Assert.assertTrue(cpc.clickEditButton(), "The Record is Edited");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Update notes again with changing the status");
			Assert.assertTrue(cpc.enterNotes(updatedNote), "The Note Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Update the Status");
			Assert.assertTrue(cpc.selectStatus("Pending"), "The Record is Edited");

			cpc.clickSaveButton();

			WebElement actualResult = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "Notes"));
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Verify the Updated Note is Savd or not");
			Assert.assertEquals(actualResult.getText(), updatedNote, "The Note is verified");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 14 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * Verifying the Registration from Reseller to CPC
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 03-Aug-2019
	 */

	@Test(priority = 14)
	public void Registration_Verification_ResellerToCPC() {
		try {
			LOGGER.info("------------Running Test Case 15-------------");
			String emailID = "TSB_" + randomNumber + "@mailinator.com";
			String userID = "TSB_" + randomNumber;
			String licenseCode = "AUDIT-001";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Navigate to Reseller site");
			TestDriver.appInstantiation(Apps.ReSellerIndia);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the home page of Reseller");
			Assert.assertTrue(rp.loginReseller("India"), "The Reseller Home Page");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Select the Course");
			Assert.assertTrue(rp.courseSelection("India", "PMC-CPM MPS520: Managing Projects"),
					"The course is selected");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Complete the Registrations");
			rp.resellerCourseRegistration(emailID, userID, "India", "Delhi", licenseCode);

			// SMIL Login
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Click the Reseller Approval tab");
			cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 8. Verifying the created Registration from Reseller is present in CPC or not");
			cpcr.resellerSearchResultVerification("IN004 (AIN)", emailID);

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 15 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * CPC_004, CPC_004(a),CPC_004(b), CPC_004(c) and CPC_004(d)
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 23-Aug-2019
	 */

	@Test(priority = 15)
	public void CPC_License_validation() {
		try {
			LOGGER.info("------------Running Test Case 16-------------");
			String emailID = "TSB_9076@mailinator.com";

			// SMIL Login
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Click the Reseller Approval tab");
			cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Verifying the serach Result");
			cpcr.resellerSearchResultVerification("IN004 (AIN)", emailID);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Edit the Registration having invalid Licence Code");
			cpcr.navigateResellerSearchResult(emailID);

			// Expiration Date
			String licenceCode = "ACALA-001";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Enter the Licence Code");
			Assert.assertTrue(cpcr.enterLicenceCode(licenceCode), "Enter the Licence Code");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Click the Save Button");
			Assert.assertTrue(cpcr.clickSaveButton(), "Save button is clicked");

			WebElement actualResult = driver.findElement(
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ResellerApprovals", "LicenseCode_Error"));
			String expectedResult = "The Expiration Date on license has been reached. Please adjust the License Expiration Date in License Admin as applicable to process this enrollment.";
			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 8. Verifying the Error message for Expiration Date Licence Code");
			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Error Message");

			// Expiration Date and Number of Seats
			licenceCode = "ABECK-001";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Enter the Licence Code");
			Assert.assertTrue(cpcr.enterLicenceCode(licenceCode), "Enter the Licence Code");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Click the Save Button");
			Assert.assertTrue(cpcr.clickSaveButton(), "Save button is clicked");

			actualResult = driver.findElement(
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ResellerApprovals", "LicenseCode_Error"));
			expectedResult = "Number of Seats on license has been reached and license is expired. Please adjust the Total Seats and Expiration Date in License Admin as applicable to process this enrollment.";
			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 11. Verifying the Error message for Expiration Date Licence Code and No Seat is remaining");
			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Error Message");

			// Number of Seats
			licenceCode = "EVERH-003";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 12. Enter the Licence Code");
			Assert.assertTrue(cpcr.enterLicenceCode(licenceCode), "Enter the Licence Code");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 13. Click the Save Button");
			Assert.assertTrue(cpcr.clickSaveButton(), "Save button is clicked");

			actualResult = driver.findElement(
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ResellerApprovals", "LicenseCode_Error"));
			expectedResult = "Number of Seats on license has been reached. Please adjust the Total Seats in License Admin as applicable to process this enrollment.";
			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 14. Verifying the Error message for No Seat is available on Licence Code");
			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Error Message");

			// Invalid Licence Code
			licenceCode = "3212312345";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 15. Enter the Licence Code");
			Assert.assertTrue(cpcr.enterLicenceCode(licenceCode), "Enter the Licence Code");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 16. Click the Save Button");
			Assert.assertTrue(cpcr.clickSaveButton(), "Save button is clicked");

			actualResult = driver.findElement(
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ResellerApprovals", "LicenseCode_Error"));
			expectedResult = "License code \"" + licenceCode + "\" is invalid.";
			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 17. Verifying the Error message for Invalid Licence Code");
			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Error Message");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 16 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * CPC_014(c) Verify after Processing a record from CPC,the email is sent for
	 * the Account Setup and the student is able to set up and use the Learning
	 * Portal Account.
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 26-Aug-2019
	 */

	@Test(priority = 16)
	public void ResellerApproval_CPC_processedAccess_Granted_Learning() {
		try {
			LOGGER.info("------------Running Test Case 17-------------");
			String emailID = "TSB_" + randomNumber + "@mailinator.com";
			String userID = "TSB_" + randomNumber;
			String licenseCode = "AUDIT-001";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Navigate to Reseller site");
			TestDriver.appInstantiation(Apps.ReSellerIndia);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the home page of Reseller");
			Assert.assertTrue(rp.loginReseller("India"), "The Reseller Home Page");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Select the Course");
			Assert.assertTrue(rp.courseSelection("India", "PMC-CPM MPS520: Managing Projects"),
					"The course is selected");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Complete the Registrations");
			rp.resellerCourseRegistration(emailID, userID, "India", "Delhi", licenseCode);

			// SMIL Login
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Click the Reseller Approval tab");
			cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Seraching the Registration by using Delivery Office");
			cpcr.resellerSearchResultVerification("IN004 (AIN)", emailID);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Edit the Registration having invalid Licence Code");
			Assert.assertTrue(cpcr.navigateResellerSearchResult(emailID), "Edit the Registration");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Click the Approve Button");
			Assert.assertTrue(cpcr.clickApproveButton(), "Approve button is clicked");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 11. Click on \"Processing Center\" tab");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 12. Enter the email ID as " + emailID + "");
			Assert.assertTrue(cpc.advanceSearch("", emailID, "", "", "", "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 11. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 13. Enter the valid Intacct ID");
			Assert.assertTrue(cpc.enterIntacctID("123123"), "The validation of Intacct ID");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 14. Select the Status as \"Processed/Access Granted\"");
			Assert.assertTrue(cpc.selectStatus("Processed/Access Granted"),
					"The Status Processed/Access Granted is selected");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 15. Click the Save Button");
			cpc.clickSaveButton();

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 17 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * CPC_022 Verify Reseller Registration and Reseller Approval works the same.
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 26-Aug-2019
	 */

	@Test(priority = 17)
	public void Reseller_sessionmapping() {
		try {
			LOGGER.info("------------Running Test Case 18-------------");

			String emailID = "TSB_" + randomNumber + "@mailinator.com";
			String userID = "TSB_" + randomNumber;
			String licenseCode = "AUDIT-001";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Navigate to Reseller site");
			TestDriver.appInstantiation(Apps.ReSellerIndia);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the home page of Reseller");
			Assert.assertTrue(rp.loginReseller("India"), "The Reseller Home Page");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Select the Course");
			Assert.assertTrue(rp.courseSelection("India", "PMC-CPM MPS520: Managing Projects"),
					"The course is selected");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Complete the Registrations");
			rp.resellerCourseRegistration(emailID, userID, "India", "Delhi", licenseCode);

			// SMIL Login
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Click the Reseller Approval tab");
			cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Seraching the Registration by using Delivery Office");
			cpcr.resellerSearchResultVerification("IN004 (AIN)", emailID);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Edit the Registration");
			Assert.assertTrue(cpcr.navigateResellerSearchResult(emailID), "Edit the Registration");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Click the Approve Button");
			Assert.assertTrue(cpcr.clickApproveButton(), "Approve button is clicked");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 11. Click on \"Processing Center\" tab");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 12. Enter the email ID as " + emailID + "");
			Assert.assertTrue(cpc.advanceSearch("", emailID, "", "", "", "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			WebElement actualResult = driver.findElement(
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "result_status"));
			String expectedResult = "Pending";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 13. Verifying the result is in Pending Status or not");
			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Registration Status");
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Verifying the result is in Pending Status or not",
					result);

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 18 is failed due to below problem",
					testResult);
		}
	}

	/**
	 * CPC_022(a) Verify Reseller Registration and Reseller Approval works for
	 * Future Expected Date.
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 26-Aug-2019
	 */

	@Test(priority = 18)
	public void Reseller_sessionmapping_FutureExpectedDate() {
		try {
			LOGGER.info("------------Running Test Case 19-------------");
			String emailID = "TSB_" + randomNumber + "@mailinator.com";
			String userID = "TSB_" + randomNumber;
			String licenseCode = "AUDIT-001";
			String expectedStartDate = "01/01/2020";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Navigate to Reseller site");
			TestDriver.appInstantiation(Apps.ReSellerIndia);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Verifying the home page of Reseller");
			Assert.assertTrue(rp.loginReseller("India"), "The Reseller Home Page");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Select the Course");
			Assert.assertTrue(rp.courseSelection("India", "PMC-CPM MPS520: Managing Projects"),
					"The course is selected");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Complete the Registrations");
			rp.resellerCourseRegistration(emailID, userID, "India", "Delhi", licenseCode);

			// SMIL Login
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "ProcessingCenter"))) {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			}

			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Click the Reseller Approval tab");
			cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Seraching the Registration by using Delivery Office");
			cpcr.resellerSearchResultVerification("IN004 (AIN)", emailID);

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Edit the Registration");
			Assert.assertTrue(cpcr.navigateResellerSearchResult(emailID), "Edit the Registration");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Click the Approve Button");
			Assert.assertTrue(cpcr.clickApproveButton(), "Approve button is clicked");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 11. Click on \"Processing Center\" tab");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 12. Enter the email ID as " + emailID + "");
			Assert.assertTrue(cpc.advanceSearch("", emailID, "", "", "", "", "", "", "", ""),
					"Verifying the Advance Serach Result");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 13. Click on \"Edit\" icon for one of the serach record");
			Assert.assertTrue(cpc.clickSearchEdit(), "The Serached Result");

			WebElement beforeLegacyID = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "LegacyID"));

			String bLegacyID = beforeLegacyID.getText();

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 14. Change the Expected start date to future.");
			Assert.assertTrue(cpc.enterExpectedDate(expectedStartDate), "The Expected start date");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 15. Click the Save Button");
			cpc.clickSaveButton();

			WebElement afterLegacyID = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "LegacyID"));

			if (!bLegacyID.contentEquals(afterLegacyID.getText())) {
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The record is mapped to a new session", result);
			} else {
				boolean result = false;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The record is mapped to a new session", result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 19 is failed due to below problem",
					testResult);
		}
	}

//	/**
//	 * CPC_004(b)) CPC_License_validation(Invalid)
//	 * 
//	 * @author Tara Singh Bhandari
//	 * @authored_date 03-Aug-2019
//	 */	
//
//	@Test(priority = 15)
//	public void CPC_License_validation_Expiration_RemainingSeats() {
//		try {
//			LOGGER.info("------------Running Test Case 16-------------");
//			TestDriver.appInstantiation(Apps.SMIL);
//
//			if (UIUtils.isObjectExist(driver,
//				TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
//				ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the SMIL Login");
//				UIUtils.attachScreenShot(ExtentManager.extentTest, "Registration Completed", sm.validateUI());
//				sm.login("SinghT", "Welcome@321");
//				ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the Reseller Approval tab");
//				cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");
//			} else {
//				ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the Reseller Approval tab");
//				cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");
//			}
//
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the serach Result");
//			cpcr.resellerSearchResultVerification("IN004 (AIN)", "TSB_Test51@mailinator.com");
//			
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Edit the Registration having invalid Licence Code");
//			cpcr.navigateResellerSearchResult("TSB_Test51@mailinator.com");
//			
//			String licenceCode = "ABECK-001";
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Enter the Licence Code");
//			Assert.assertTrue(cpcr.enterLicenceCode(licenceCode), "Enter the Licence Code");
//			
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Click the Save Button");
//			Assert.assertTrue(cpcr.clickSaveButton(), "Save button is clicked");
//			
//			WebElement actualResult = driver.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ResellerApprovals", "LicenseCode_Error"));
//			String expectedResult = "Number of Seats on license has been reached and license is expired. Please adjust the Total Seats and Expiration Date in License Admin as applicable to process this enrollment.";
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Verifying the Error message for Expiration Date Licence Code and No Seat is remaining");
//			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Error Message");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			boolean testResult = false;
//			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 16 is failed due to below problem",
//					testResult);
//		}
//	}

//	@Test(priority = 16)
//	public void CPC_License_validation_RemainingSeats() {
//		try {
//			LOGGER.info("------------Running Test Case 17-------------");
//			TestDriver.appInstantiation(Apps.SMIL);
//
//			if (UIUtils.isObjectExist(driver,
//				TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
//				ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the SMIL Login");
//				UIUtils.attachScreenShot(ExtentManager.extentTest, "Registration Completed", sm.validateUI());
//				sm.login("SinghT", "Welcome@321");
//				ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the Reseller Approval tab");
//				cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");
//			} else {
//				ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the Reseller Approval tab");
//				cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");
//			}
//
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the serach Result");
//			cpcr.resellerSearchResultVerification("IN004 (AIN)", "TSB_Test51@mailinator.com");
//			
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Edit the Registration having invalid Licence Code");
//			cpcr.navigateResellerSearchResult("TSB_Test51@mailinator.com");
//			
//			String licenceCode = "EVERH-003";
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Enter the Licence Code");
//			Assert.assertTrue(cpcr.enterLicenceCode(licenceCode), "Enter the Licence Code");
//			
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Click the Save Button");
//			Assert.assertTrue(cpcr.clickSaveButton(), "Save button is clicked");
//			
//			WebElement actualResult = driver.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ResellerApprovals", "LicenseCode_Error"));
//			String expectedResult = "Number of Seats on license has been reached. Please adjust the Total Seats in License Admin as applicable to process this enrollment.";
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Verifying the Error message for No Seat is available on Licence Code");
//			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Error Message");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			boolean testResult = false;
//			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 17 is failed due to below problem",
//					testResult);
//		}
//	}
//	
//	/**
//	 * CPC_004(b)) CPC_License_validation(Invalid)
//	 * 
//	 * @author Tara Singh Bhandari
//	 * @authored_date 03-Aug-2019
//	 */	
//
//	@Test(priority = 17)
//	public void CPC_License_validation_InvalidLicenceCode() {
//		try {
//			LOGGER.info("------------Running Test Case 18-------------");
//
//			TestDriver.appInstantiation(Apps.SMIL);
//
//			if (UIUtils.isObjectExist(driver,
//				TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
//				ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the SMIL Login");
//				UIUtils.attachScreenShot(ExtentManager.extentTest, "Registration Completed", sm.validateUI());
//				sm.login("SinghT", "Welcome@321");
//				ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the Reseller Approval tab");
//				cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");
//			} else {
//				ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the Reseller Approval tab");
//				cpcr = (SMILResellerApprovals) common.clickMenuTab("RESELLERAPPROVALS");
//			}
//
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 6. Verifying the serach Result");
//			cpcr.resellerSearchResultVerification("IN004 (AIN)", "TSB_Test51@mailinator.com");
//			
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Edit the Registration having invalid Licence Code");
//			cpcr.navigateResellerSearchResult("TSB_Test51@mailinator.com");
//			
//			String licenceCode = "3212312345";
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Enter the Licence Code");
//			Assert.assertTrue(cpcr.enterLicenceCode(licenceCode), "Enter the Licence Code");
//			
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Click the Save Button");
//			Assert.assertTrue(cpcr.clickSaveButton(), "Save button is clicked");
//			
//			WebElement actualResult = driver.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ResellerApprovals", "LicenseCode_Error"));
//			String expectedResult = "License code \""+licenceCode+"\" is invalid.";
//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Verifying the Error message for Invalid Licence Code");
//			Assert.assertEquals(actualResult.getText(), expectedResult, "Verifying the Error Message");
//			
////			ExtentManager.extentTest.log(LogStatus.INFO, "Step 10. Verifying the Error message for Invalid Licence Code");
////			Assert.assertTrue(cpcr.resellerLicenceCodeVerification(), "The Licence Code Error Message");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			boolean testResult = false;
//			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 18 is failed due to below problem",
//					testResult);
//		}
//	}

	/**
	 * CPC_002(b) CPC_Verify registrations(purchase order) from public website with
	 * more than one courses & for someone else can be processed in CPC.
	 * 
	 * @author Tara Singh Bhandari
	 * @authored_date 05-Sep-2019
	 */

	@Test(priority = 19)
	public void CPC_PublicWebsite_Registration_purchaseorder() throws Exception {

		try {
			LOGGER.info("------------Running Test Case 20-------------");
			String name = "TSB_" + randomNumber;
			String emailID = "TSB_" + randomNumber + "@mailinator.com";
			String paymentMethod = "Purchase Order";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Navigate into Public Website");
			TestDriver.appInstantiation(Apps.PublicWebsite);
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Public Website", pw.validateUI());

//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Find a Course");
//			Assert.assertTrue(pw.findCource(), "Find a Course");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Add to Cart any course");
			Assert.assertTrue(pw.addToCart(), "Course is added in Cart");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Select the Course from the Cart");
			Assert.assertTrue(pw.shoppingCart(), "Course is added in Cart");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the New Registration");
			Assert.assertTrue(pw.newRegistration(), "New Registration Process");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Complete the registration with payment method as " + paymentMethod);
			Assert.assertTrue(pw.newRegistrationProcess(name, emailID, paymentMethod),
					"The Registration Process through Public Website is completed Suscessfully");

			// SMIL Login and Verify the Registration Detail
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");

			}
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Click on \"Processing Center\" menu");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 10. Verifying the created Registration from Public Website in CPC");
			Assert.assertTrue(cpc.advanceSearch(name, emailID, "", "", "", "", "", "", "", ""),
					"Searching for created record in CPC from Public Website");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 20 is failed due to below problem",
					testResult);
		}

	}

	@Test(priority = 20)
	public void CPC_PublicWebsite_Registration_Check() throws Exception {

		try {
			LOGGER.info("------------Running Test Case 21-------------");
			System.out.println("The random value is----" + randomNumber);
			String name = "TSB_" + randomNumber;
			String emailID = "TSB_" + randomNumber + "@mailinator.com";
			String paymentMethod = "Check";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Navigate into Public Website");
			TestDriver.appInstantiation(Apps.PublicWebsite);
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Public Website", pw.validateUI());

//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Find a Course");
//			Assert.assertTrue(pw.findCource(), "Find a Course");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Add to Cart any course");
			Assert.assertTrue(pw.addToCart(), "Course is added in Cart");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Select the Course from the Cart");
			Assert.assertTrue(pw.shoppingCart(), "Course is added in Cart");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the New Registration");
			Assert.assertTrue(pw.newRegistration(), "New Registration Process");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Complete the registration with payment method as " + paymentMethod);
			Assert.assertTrue(pw.newRegistrationProcess(name, emailID, paymentMethod),
					"The Registration Process through Public Website is completed Suscessfully");

			// SMIL Login and Verify the Registration Detail
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");

			}
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Click on \"Processing Center\" menu");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 10. Verifying the created Registration from Public Website in CPC");
			Assert.assertTrue(cpc.advanceSearch(name, emailID, "", "", "", "", "", "", "", ""),
					"Searching for created record in CPC from Public Website");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 21 is failed due to below problem",
					testResult);
		}

	}

	@Test(priority = 21)
	public void CPC_PublicWebsite_Registration_License() throws Exception {

		try {
			LOGGER.info("------------Running Test Case 22-------------");
			System.out.println("The random value is----" + randomNumber);
			String name = "TSB_" + randomNumber;
			String emailID = "TSB_" + randomNumber + "@mailinator.com";
			String paymentMethod = "License";
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 1. Navigate into Public Website");
			TestDriver.appInstantiation(Apps.PublicWebsite);
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Public Website", pw.validateUI());

//			ExtentManager.extentTest.log(LogStatus.INFO, "Step 2. Find a Course");
//			Assert.assertTrue(pw.findCource(), "Find a Course");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 3. Add to Cart any course");
			Assert.assertTrue(pw.addToCart(), "Course is added in Cart");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 4. Select the Course from the Cart");
			Assert.assertTrue(pw.shoppingCart(), "Course is added in Cart");

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 5. Click the New Registration");
			Assert.assertTrue(pw.newRegistration(), "New Registration Process");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 6. Complete the registration with payment method as " + paymentMethod);
			Assert.assertTrue(pw.newRegistrationProcess(name, emailID, paymentMethod),
					"The Registration Process through Public Website is completed Suscessfully");

			// SMIL Login and Verify the Registration Detail
			ExtentManager.extentTest.log(LogStatus.INFO, "Step 7. Login into SMIL Application");
			TestDriver.appInstantiation(Apps.SMIL);
			if (UIUtils.isObjectExist(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"))) {
				sm.login("SinghT", "Welcome@321");
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");
			} else {
				WebElement results = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", "SMIL_Home_Page"));
				ExtentManager.extentTest.log(LogStatus.INFO, "Step 8. Verifying the SMIL Home Page");
				Assert.assertEquals(results.getText(), "Service Management Integration for Learning");

			}
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Home Page", cpc.isPageOpen());

			ExtentManager.extentTest.log(LogStatus.INFO, "Step 9. Click on \"Processing Center\" menu");
			cpc = (SMILCPC) common.clickMenuTab("ProcessingCenter");

			ExtentManager.extentTest.log(LogStatus.INFO,
					"Step 10. Verifying the created Registration from Public Website in CPC");
			Assert.assertTrue(cpc.advanceSearch(name, emailID, "", "", "", "", "", "", "", ""),
					"Searching for created record in CPC from Public Website");

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Test case 22 is failed due to below problem",
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
		// ExtentManager.extentTest.addScreenCapture(screenShotPath);
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
		System.out.println("------------Running @AfterClass of Regression-------------");
		ExtentManager.getInstance().flush();
		ExtentManager.getInstance().close();
		driver.close();
	}

}
