package com.svam.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;
import com.svam.driver.TestDriver;
import com.svam.utils.ExcelUtils;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class IntacctGems extends AbstractPage {
	private static final String SCREENNAME = "Intacct_Gems_NewPerson";
	public static String IntacctWorkbook;

	public IntacctGems(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		return false;
	}

	@Override
	public boolean validateUI() {
		return UIUtils.isObjectExist(driver,
				TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems", "newMainMenu"));
	}

	public static String RegistrationData() throws IOException {
		return IntacctWorkbook = new File("Framework\\Test_Data\\IntacctGems.xls").getCanonicalPath();
	}

	public boolean newPerson() {
		try {
			driver.switchTo().frame("iamain");
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems", "Person"), driver);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_NewPerson", "LEGACY_ID"), 120);
			return isElementExists("Intacct_Gems_NewPerson", "savePerson")
					&& isElementExists("Intacct_Gems_NewPerson", "LEGACY_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean intacctProcess() {
		try {
			FileInputStream fis = new FileInputStream(RegistrationData());
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("Data");
			int lastRow = sheet.getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {

				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 0)).contentEquals("Insert")) {
					boolean result = newPersonRegistration(i);
					if (result) {
						String.valueOf(ExcelUtils.setCellValue(IntacctWorkbook, "Data", i, 77, "Done"));
					} else {
						String.valueOf(ExcelUtils.setCellValue(IntacctWorkbook, "Data", i, 77, "Not Completed"));
					}
					driver.switchTo().defaultContent();
					Common common = PageFactory.initElements(driver, Common.class);
					common.clickOnNewMenuOptions("GEMS", "People", "People");
					continue;
				} else if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 0))
						.contentEquals("Update")) {
					driver.switchTo().defaultContent();
					driver.switchTo().frame("iamain");
//					UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PersonFilter"),
//							"text=Quality Control Complete");
//					UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PersonFilter"),
//							"text=All People-DO NOT DELETE");
					boolean result = updatePersonRegistration(i);
					if (result) {
						String.valueOf(ExcelUtils.setCellValue(IntacctWorkbook, "Data", i, 77, "Done"));
					} else {
						String.valueOf(ExcelUtils.setCellValue(IntacctWorkbook, "Data", i, 77, "Not Completed"));
					}
					driver.switchTo().defaultContent();
					Common common = PageFactory.initElements(driver, Common.class);
					common.clickOnNewMenuOptions("GEMS", "People", "People");
					continue;
				} else
					System.out.println("Please enter the action command");
			}
			workbook.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean newPersonRegistration(int i) {
		try {
			newPerson();
			ExtentManager.extentTest.log(LogStatus.INFO, "Entering the New Person Data for Family Name '"
					+ String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 5)) + "'");
			boolean newPersonResult = isElementExists(SCREENNAME, "FamilyName");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "New Person Page of Stage Intacct", newPersonResult);
			// Data filling in New Person

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 2)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "LEGACY_ID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 2)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 3)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MyESI_ID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 3)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 4)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "External_ID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 4)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 5)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "FamilyName"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 5)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 6)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "GivenName"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 6)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 7)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MiddleInitial"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 7)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 8)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Gender"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 8)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 9)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Salutation"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 9)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 10)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Suffix"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 10)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 11)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Region"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 11)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 12)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "JobStatus"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 12)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 13)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PersonStatus"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 13)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 14)).length() > 1) {
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Company"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 14)));
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SelectedCompany"),
						driver);
			}

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 15)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Department"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 15)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 16)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "JobTitle"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 16)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 17)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PrintAs"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 17)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 18)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BadgeName"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 18)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 19)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Type"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 19)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 20)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "EmailID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 20)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 21)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "EmailOptIn"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 21)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 22)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "WorkPhone"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 22)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 23)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MobilePhone"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 23)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 24)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "TimeZone"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 24)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 25)).length() > 1) {
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Supervisor"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 25)));
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Selected_Supervisor"),
						driver);
			}

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 26)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "EmployeeID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 26)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 27)).length() > 1)
				UIUtils.selectValue(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingCountry"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 27)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 28)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingStreet1"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 28)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 29)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingStreet2"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 29)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 30)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingCity"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 30)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 31)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingState"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 31)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 32)).length() > 1)
				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingPostalCode"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 32)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 33)).length() > 1)
				UIUtils.selectValue(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingCountry"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 33)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 34)).length() > 0)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingStreet1"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 34)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 35)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingStreet2"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 35)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 36)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingCity"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 36)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 37)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingState"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 37)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 38)).length() > 1)
				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingPostalCode"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 38)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 39)).contains("Y"))
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CopyMailToBilling"),
						driver);
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 40)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SMS_Text"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 40)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 41)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Other_Phone"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 41)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 42)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Fax"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 42)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 43)).length() > 1)
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ESI_Alum"), driver);

			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);

			boolean newPersonCreated = isElementExists(SCREENNAME, "SearchByEmailAddress");

			if (newPersonCreated) {
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchByEmailAddress"), 32);

				// Searching the created person
				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 20)).length() > 1)
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchByEmailAddress"),
							String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 20)));

				UIUtils.clickElement(
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchByEmailAddressGo"), driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchResult"), 30);

				// Navigating to the search Result and update the WorkFlow Status
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchResult"),
						driver);

				ExtentManager.extentTest.log(LogStatus.INFO, "The Created Person detail:");
				boolean createdPersonResult = isElementExists(SCREENNAME, "SystemInfoTab");
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The created Person Page Detail",
						createdPersonResult);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SystemInfoTab"), 32);

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPerson"), driver);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SystemInfoTab"),
						driver);
				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 44)).length() > 1)
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "WorkflowStatus"),
							"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 44)));

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SystemInfoTab"), 30);
				boolean OEOrderCreated = isElementExists(SCREENNAME, "SystemInfoTab");
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "OrderHistoryTab"),
						driver);

				if (OEOrderCreated) {

					if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 45)).contentEquals("OL")) {
						// Data filling in New OE Order

						UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewOEOrder"),
								driver);

						ExtentManager.extentTest.log(LogStatus.INFO, "Creating the New OE Order");
						boolean OEOrderResult = isElementExists(SCREENNAME, "PaymentMethod");
						UIUtils.attachScreenShot(ExtentManager.extentTest, "New OE Page of Stage Intacct",
								OEOrderResult);
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 46)).length() > 1) {
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Bill_To"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 46)));
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Selected_Bill_To"),
									driver);
						}
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 47)).length() > 1) {
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Booking_Contact"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 47)));
							UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
									"Selected_Booking_Contact"), driver);
						}
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 48)).length() > 1)
							UIUtils.selectValue(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Source"),
									"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 48)));

						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 49)).length() > 1)
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Use_Customer_Terms"),
									driver);

						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 50)).length() > 1)
							UIUtils.selectValue(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PaymentMethod"),
									"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 50)));
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 51)).length() > 1)
							UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Taxable"),
									driver);
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 52)).length() > 1)
							UIUtils.selectValue(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Tax_Basis"),
									"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 52)));
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 53)).length() > 1)
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Voucher_ID"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 53)));
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 54)).length() > 1)
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Charge_Date"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 54)));

						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 55)).length() > 1)
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ReferenceNumber"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 55)));
						UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"),
								driver);

						UIUtils.waitUntilElementExists(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedOEOrder"), 30);
						boolean EnrollmentCreated = isElementExists(SCREENNAME, "CreatedOEOrder");

						if (EnrollmentCreated) {

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedOEOrder"),
									driver);

							ExtentManager.extentTest.log(LogStatus.INFO, "The Created OE detail:");
							boolean createdOEResult = isElementExists(SCREENNAME, "editPerson");
							UIUtils.attachScreenShot(ExtentManager.extentTest, "The created OE Page Detail",
									createdOEResult);

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPerson"), driver);
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NotesTab"), driver);
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 56)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "WorkflowStatus"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 56)));
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);

							// New Enrollment Process
							UIUtils.waitUntilElementExists(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewOEEnrollment"), 30);
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewOEEnrollment"),
									driver);

							ExtentManager.extentTest.log(LogStatus.INFO, "Creating the New Enrollment");
							boolean EnrollmentResult = isElementExists(SCREENNAME, "Delegate");
							UIUtils.attachScreenShot(ExtentManager.extentTest, "New Enrollment Page of Stage Intacct",
									EnrollmentResult);

							UIUtils.waitUntilElementExists(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Delegate"), 30);
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 57)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Delegate"),
										driver);

								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Delegate"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 57)));
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SelectedDelegate"),
										driver);
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 58)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Deliverable"),
										driver);

								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Deliverable"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 58)));
								UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
										"SelectedDeliverable"), driver);
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 59)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PriorityCode"),
										driver);

								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PriorityCode"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 59)));
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 60)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
												"Enrollment_Status"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 60)));
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 61)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "TAD_AE"), driver);

								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "TAD_AE"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 61)));
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Selected_TAD_AE"),
										driver);
							}
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 62)).length() > 1) {
								UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
										"ExpectedStartDate"), driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
												"ExpectedStartDate"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 62)));
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 63)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Ship_to_Person"),
										driver);

								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Ship_to_Person"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 63)));
								UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
										"Selected_Ship_to_Person"), driver);
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 64)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "DiscountType"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 64)));

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 65)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Percent_Off"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 65)));

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 66)).length() > 1) {
								UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
										"Additional_Discount"), driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
												"Additional_Discount"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 66)));
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 67)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Value_Price"),
										driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Value_Price"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 67)));
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 68)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Discount_Note"),
										driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Discount_Note"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 68)));
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 69)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "TA_Program"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 69)));

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 70)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "GratisReason"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 70)));
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 71)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Promo_Type"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 71)));

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 72)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NRIC"), driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NRIC"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 72)));
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 73)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Local_Tax"),
										driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Local_Tax"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 73)));
							}
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 74)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "State_Tax"),
										driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "State_Tax"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 74)));
							}
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 75)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Other_Tax"),
										driver);
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Other_Tax"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 75)));
							}

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);
							UIUtils.waitUntilElementExists(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedEnrollment"),
									30);
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedEnrollment"),
									driver);

							ExtentManager.extentTest.log(LogStatus.INFO, "The Enrollment detail:");
							boolean createdEnrollmentResult = isElementExists(SCREENNAME, "editPerson");
							UIUtils.attachScreenShot(ExtentManager.extentTest, "The created Enrollement Page Detail",
									createdEnrollmentResult);

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPerson"), driver);
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SystemInfoTab"),
									driver);
							UIUtils.scrollWindow(driver, "Down");
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 76)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "WorkflowStatus"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 76)));
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);
							ExtentManager.extentTest.log(LogStatus.INFO,
									"The Enrollment process is completed for Family Name '"
											+ String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 5))
											+ "'");
							return true;
						} else {
							UIUtils.attachScreenShot(ExtentManager.extentTest,
									"Enrollment is not created due to some error", EnrollmentCreated);
							return false;

						}

					} else if (!String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 45))
							.contentEquals("OL")) {
						// Data filling in New DD Order
						UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewDDOrder"),
								driver);

						ExtentManager.extentTest.log(LogStatus.INFO, "Creating the New DD Order");
						boolean OEOrderResult = isElementExists(SCREENNAME, "PaymentMethod");
						UIUtils.attachScreenShot(ExtentManager.extentTest, "New Person Page of Stage Intacct",
								OEOrderResult);

						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 46)).length() > 1) {
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Bill_To"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 46)));
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Selected_Bill_To"),
									driver);
						}
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 47)).length() > 1) {
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Booking_Contact"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 47)));
							UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
									"Selected_Booking_Contact"), driver);
						}

						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 50)).length() > 1)
							UIUtils.selectValue(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PaymentMethod"),
									"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 50)));
						if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 55)).length() > 1)
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ReferenceNumber"),
									String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 55)));
						UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"),
								driver);
						UIUtils.waitUntilElementExists(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedDDOrder"), 30);
						boolean EnrollmentCreated = isElementExists(SCREENNAME, "CreatedDDOrder");

						if (EnrollmentCreated) {

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedDDOrder"),
									driver);

							ExtentManager.extentTest.log(LogStatus.INFO, "The detail of DD Order:");
							boolean createdDDResult = isElementExists(SCREENNAME, "editPerson");
							UIUtils.attachScreenShot(ExtentManager.extentTest, "The detail of DD Order",
									createdDDResult);

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPerson"), driver);
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SystemInfoTab"),
									driver);
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 56)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "WorkflowStatus"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 56)));
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);

							// New Enrollment Process
							UIUtils.waitUntilElementExists(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewDDEnrollment"), 30);
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewDDEnrollment"),
									driver);

							ExtentManager.extentTest.log(LogStatus.INFO, "Creating the New DD Enrollment");
							boolean EnrollmentResult = isElementExists(SCREENNAME, "Deliverable");
							UIUtils.attachScreenShot(ExtentManager.extentTest, "Creating the New DD Enrollment",
									EnrollmentResult);

							UIUtils.waitUntilElementExists(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Deliverable"), 30);
							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 58)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Deliverable"),
										driver);

								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Deliverable"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 58)));
								UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
										"SelectedDeliverable"), driver);
							}

							if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 57)).length() > 1) {
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Delegate"),
										driver);

								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Delegate"),
										String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 57)));
								UIUtils.clickElement(
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SelectedDelegate"),
										driver);
							}

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);
							UIUtils.waitUntilElementExists(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedEnrollment"),
									30);
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatedEnrollment"),
									driver);

							ExtentManager.extentTest.log(LogStatus.INFO, "The Enrollment detail:");
							boolean createdEnrollmentResult = isElementExists(SCREENNAME, "editPerson");
							UIUtils.attachScreenShot(ExtentManager.extentTest, "The created Enrollement Page Detail",
									createdEnrollmentResult);
							return true;
						} else {
							UIUtils.attachScreenShot(ExtentManager.extentTest,
									"Enrollment is not created due to some error", EnrollmentCreated);
							return false;
						}

					}

				}

				else {
					UIUtils.attachScreenShot(ExtentManager.extentTest, "Order is not created due to some error",
							OEOrderCreated);
					return false;
				}

			} else {
				UIUtils.attachScreenShot(ExtentManager.extentTest, "New Person is not created due to some error",
						newPersonCreated);
				return false;
			}

			// return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updatePersonRegistration(int i) {
		try {
			driver.findElement(By.xpath("//input[@id='f14927_email' and @class='rbf_inlfilter']")).clear();
			driver.findElement(By.xpath("//input[@id='f14927_id' and @class='rbf_inlfilter']")).clear();
			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchByIntacctID"),
					String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 1)));

			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchByIntacctIDGo"),
					driver);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchResult"), driver);
			
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SystemInfoTab"), 30);

			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPerson"), driver);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PersonInfoTab"), driver);

			ExtentManager.extentTest.log(LogStatus.INFO, "Editing the existing Person Data for Intacct ID '"
					+ String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 1)) + "'");
			boolean existingPersonResult = isElementExists(SCREENNAME, "FamilyName");
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Existing Person Page of Stage Intacct",
					existingPersonResult);
			// Updating the existing Person

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 2)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "LEGACY_ID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 2)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 3)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MyESI_ID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 3)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 4)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "External_ID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 4)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 5)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "FamilyName"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 5)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 6)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "GivenName"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 6)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 7)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MiddleInitial"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 7)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 8)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Gender"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 8)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 9)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Salutation"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 9)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 10)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Suffix"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 10)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 11)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Region"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 11)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 12)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "JobStatus"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 12)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 13)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PersonStatus"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 13)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 14)).length() > 1) {
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Company"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 14)));
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SelectedCompany"),
						driver);
			}

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 15)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Department"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 15)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 16)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "JobTitle"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 16)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 17)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PrintAs"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 17)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 18)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BadgeName"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 18)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 19)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Type"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 19)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 20)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "EmailID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 20)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 21)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "EmailOptIn"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 21)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 22)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "WorkPhone"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 22)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 23)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MobilePhone"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 23)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 24)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "TimeZone"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 24)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 25)).length() > 1) {
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Supervisor"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 25)));
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Selected_Supervisor"),
						driver);
			}

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 26)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "EmployeeID"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 26)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 27)).length() > 1)
				UIUtils.selectValue(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingCountry"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 27)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 28)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingStreet1"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 28)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 29)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingStreet2"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 29)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 30)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingCity"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 30)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 31)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingState"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 31)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 32)).length() > 1)
				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "MailingPostalCode"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 32)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 33)).length() > 1)
				UIUtils.selectValue(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingCountry"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 33)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 34)).length() > 0)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingStreet1"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 34)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 35)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingStreet2"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 35)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 36)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingCity"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 36)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 37)).length() > 1)
				UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingState"),
						"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 37)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 38)).length() > 1)
				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BillingPostalCode"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 38)));

			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 39)).contains("Y"))
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CopyMailToBilling"),
						driver);
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 40)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SMS_Text"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 40)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 41)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Other_Phone"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 41)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 42)).length() > 1)
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Fax"),
						String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 42)));
			if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 43)).length() > 1)
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ESI_Alum"), driver);

			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);

			return true;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}
	
	public boolean newCompany() {
		try {
			driver.switchTo().frame("iamain");
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "New_Company"), driver);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Company"), 120);
			return isElementExists("Intacct_Gems_NewPerson", "savePerson")
					&& isElementExists("Intacct_Gems_Companies", "Company");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public boolean createNewCompany() {
		try {
			FileInputStream fis = new FileInputStream(RegistrationData());
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("CompanyData");
			int lastRow = sheet.getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {
				newCompany();
				ExtentManager.extentTest.log(LogStatus.INFO,
						"The new Company '" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "Data", i, 0))
								+ "'" + " is going to create");
				boolean newCompanyResult = isElementExists("Intacct_Gems_Companies", "Company");
				UIUtils.attachScreenShot(ExtentManager.extentTest, "New Company is going to create", newCompanyResult);

				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 0)).length() > 1)
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Company"),
							String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 0)));

				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 1)).length() > 1)
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Region"),
							"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 1)));

				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 2)).length() > 1)
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Global_Code"),
							String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 2)));
				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 3)).length() > 1)
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Client_ID"),
							String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 3)));
				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 4)).length() > 1)
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Client_Code"),
							String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 4)));

				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 5)).length() > 1)
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Payment_Term"),
							"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 5)));

				if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 6)).length() > 1)
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Tax_ID"),
							String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 6)));
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);
				

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "CompanySearch"), 30);
				
				boolean result = isElementExists("Intacct_Gems_Companies", "CompanySearch");
				if(result) {
					
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "Client_ID"),
							String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 3)));
					UIUtils.clickElement(
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "ClientIDSearchGo"),
							driver);

					UIUtils.waitUntilElementExists(driver, TestDriver.getInstance().getObjRep()
							.getLocator("Intacct_Gems_Companies", "CompanySearchResult"), 30);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies",
							"CompanySearchResult"), driver);

					ExtentManager.extentTest.log(LogStatus.INFO, "The Created Company detail:");
					boolean createdCompanyResult = isElementExists("Intacct_Gems_Companies", "CompanyInfoTab");
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The created Company Page Detail",
							createdCompanyResult);

					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPerson"), driver);
					UIUtils.clickElement(
							TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems_Companies", "SystemInfoTab"),
							driver);
					if (String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 7)).length() > 1)
						UIUtils.selectValue(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "WorkflowStatus"),
								"text=" + String.valueOf(ExcelUtils.getCellValue(IntacctWorkbook, "CompanyData", i, 7)));
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "savePerson"), driver);
				}
				else if (i<=lastRow) {
					driver.switchTo().defaultContent();
					Common common = PageFactory.initElements(driver, Common.class);
					common.clickOnNewMenuOptions("GEMS","Companies","Companies");
				}
				

			}
			workbook.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
