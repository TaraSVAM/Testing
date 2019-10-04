package com.svam.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.svam.driver.TestDriver;
import com.svam.utils.Config;
import com.svam.utils.ExcelUtils;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class SMILCPC extends AbstractPage {
	static Logger LOGGER = Logger.getLogger(SMILCPC.class);
	private final String HOMETITLE = "SMIL-Home Page";
	private static final String SCREENNAME = "SMIL_ProcessingCenter";
	ExtentTest extentTest;
	public static String SMILWorkbook;
	public static String testCaseWorkbook;
	Config config = new Config("Framework\\Test_Config\\config.properties");

	public SMILCPC(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		if (driver.getTitle().equalsIgnoreCase(HOMETITLE))
			return true;
		else
			return false;
	}

	@Override
	public boolean validateUI() {
		UIUtils.waitUntilElementExists(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "processingCenter"), 10);

		return isElementExists(SCREENNAME, "processingCenter") && isElementExists("SMIL_Common", "ResellerApprovals");

	}

	public static String CPCData() throws IOException {
		return SMILWorkbook = new File("Framework\\Test_Data\\StratexData.xls").getCanonicalPath();
	}

	public static String testCases() throws IOException {
		return testCaseWorkbook = new File("Framework\\Test_Data\\TestCasesCPC.xls").getCanonicalPath();
	}

	public boolean basicSerach(String cartID) throws Exception {
		LOGGER.info("Searching by Card ID----"+ cartID);
		try {
			WebElement basicBlock = driver.findElement(By.xpath("//div[@id='search-simple']"));
			if (basicBlock.getAttribute("style").contains("display: block")) {
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "cartID"),
						cartID);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "basicSearch"),
						driver);
				// driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				WebElement resultsCard = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_CartId"));
				if (resultsCard.getText().contains(cartID)) {
					boolean serachCPCResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Cart ID " + cartID + "  is searched",
							serachCPCResult);
					return true;
				}
			} else {
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "BasicButton"),
						driver);
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "cartID"), 50);
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "cartID"),
						cartID);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "basicSearch"),
						driver);
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				WebElement resultsCard = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_CartId"));
				if (resultsCard.getText().contains(cartID)) {
					boolean serachCPCResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Cart ID " + cartID + "  is searched",
							serachCPCResult);
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean serachCPCResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Cart ID " + cartID + "  is not searched",
					serachCPCResult);
			return false;
		}
		boolean serachCPCResult = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The Cart ID " + cartID + "  is not searched",
				serachCPCResult);
		return false;
	}

	/*---------- Filling data in Processing Center - Registrations---------*/
	public boolean advanceSearch(String name, String emailID, String LicenceCode, String DeliveryOffice, String status,
			String Modality, String RegistrationSource, String ProviderSource, String DateFrom, String DateTo)
			throws Exception {
		LOGGER.info("Advance Searching Process-------");
		// String status1 = (status != null && status.length >= 1) ? status[0]: "";
		try {
			WebElement advanceBlock = driver.findElement(By.xpath("//div[@id='search-advanced']"));
			if (advanceBlock.getAttribute("style").contains("display: block")) {

				if (name.length() > 1) {
					LOGGER.info("Filling Name");
					this.fillName(name);
				}

				if (emailID.length() > 1) {
					LOGGER.info("Filling emailID");
					this.fillEmail(emailID);
				}

				if (LicenceCode.length() > 1) {
					LOGGER.info("Filling Licence Code");
					this.fillLicenceCode(LicenceCode);
				}

				if (DeliveryOffice.length() > 1) {
					LOGGER.info("Filling Delivery Office");
					this.fillDeliveryOffice(DeliveryOffice);
				}

				if (status.length() > 1) {
					LOGGER.info("Filling status");
					this.fillStatus(status);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceStatus"),
							driver);
				}

				if (Modality.length() > 1) {
					LOGGER.info("Filling Modality");
					this.fillModality(Modality);
				}

				if (RegistrationSource.length() > 1) {
					LOGGER.info("Filling Registration Source");
					this.fillRegistrationSource(RegistrationSource);
				}

				if (ProviderSource.length() > 1) {
					LOGGER.info("Filling Provider Source");
					this.fillProviderSource(ProviderSource);
				}

				if (DateFrom.length() > 1) {
					LOGGER.info("Filling Date From");
					this.fillDateFrom(DateFrom);
				}

				if (DateTo.length() > 1) {
					LOGGER.info("Filling Date To");
					this.fillDateTo(DateTo);
				}

				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchClick"),
						driver);

				if (emailID.length() > 1) {
					List<WebElement> results = driver
							.findElements(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Email"));
					for (WebElement webElement : results) {
						if (webElement.getText().contains(emailID)) {
							ExtentManager.extentTest.log(LogStatus.INFO, "The Advance serach by EmailID");
							boolean serachCPCResult = true;
							UIUtils.attachScreenShot(ExtentManager.extentTest, "The Advance serach by EmailID",
									serachCPCResult);
							return true;
						}
					}
				}
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				boolean serachCPCResult = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Advance serach is completed", serachCPCResult);
				return true;
			} else {
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceSearch"),
						driver);
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NameSerach"), 50);
				if (name.length() > 1) {
					LOGGER.info("Filling Name");
					this.fillName(name);
				}

				if (emailID.length() > 1) {
					LOGGER.info("Filling emailID");
					this.fillEmail(emailID);
				}

				if (LicenceCode.length() > 1) {
					LOGGER.info("Filling Licence Code");
					this.fillLicenceCode(LicenceCode);
				}

				if (DeliveryOffice.length() > 1) {
					LOGGER.info("Filling Delivery Office");
					this.fillDeliveryOffice(DeliveryOffice);
				}

				if (status.length() > 1) {
					LOGGER.info("Filling status---");
					this.fillStatus(status);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceStatus"),
							driver);
				}

				if (Modality.length() > 1) {
					LOGGER.info("Filling status");
					this.fillModality(Modality);
				}

				if (RegistrationSource.length() > 1) {
					LOGGER.info("Filling status");
					this.fillRegistrationSource(RegistrationSource);
				}

				if (ProviderSource.length() > 1) {
					LOGGER.info("Filling status");
					this.fillProviderSource(ProviderSource);
				}

				if (DateFrom.length() > 1) {
					LOGGER.info("Filling status");
					this.fillDateFrom(DateFrom);
				}

				if (DateTo.length() > 1) {
					LOGGER.info("Filling status");
					this.fillDateTo(DateTo);
				}

				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchClick"),
						driver);
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Email"), 90);
				
				if (emailID.length() > 1) {
					List<WebElement> results = driver
							.findElements(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Email"));
					for (WebElement webElement : results) {
						if (webElement.getText().contains(emailID)) {
							ExtentManager.extentTest.log(LogStatus.INFO, "The Advance serach by EmailID");
							boolean serachCPCResult = true;
							UIUtils.attachScreenShot(ExtentManager.extentTest, "The Advance serach by EmailID",
									serachCPCResult);
							return true;
						}
					}
				}
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				boolean serachCPCResult = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Advance serach is completed", serachCPCResult);
				return true;
			}

		} catch (Exception e) {
			LOGGER.error("Error on saving data" + e.toString());
			e.printStackTrace();
			boolean serachCPCResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Advance serach is not completed", serachCPCResult);
			return false;
		}

	}

	/*------- Saving the filled data -------*/
	public void fillName(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceName"), data);
	}

	public void fillEmail(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceEmail"), data);
	}

	public void fillLicenceCode(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceLicenceCode"),
				data);
	}

	public void fillDeliveryOffice(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.selectValue(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceDeliveryOffice"), FinalData);
	}

	public void fillStatus(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceStatus"), driver);
		UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceStatus"),
				"text=Any");
		UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceStatus"),
				FinalData);
	}

	public void fillModality(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceModality"),
				FinalData);
	}

	public void fillRegistrationSource(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.selectValue(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceRegistrationSource"), FinalData);
	}

	public void fillProviderSource(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.selectValue(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceProviderSource"), FinalData);
	}

	public void fillDateFrom(String data) throws Exception {
		UIUtils.inputText(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceDateAddedRangeFrom"), data);
	}

	public void fillDateTo(String data) throws Exception {
		UIUtils.inputText(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceDateAddedRangeTo"), data);
	}

	public boolean clickSearchEdit() {
		try {
			LOGGER.info("Clicking the Edit icon of serached result");
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"), 50);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"), driver);
			boolean testResult = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Searched result is opened in editable mode",
					testResult);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			boolean testResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Edit icon is not editable or Present", testResult);
			return false;
		}
	}

	/**
	 * @param action
	 * @return
	 */
	@SuppressWarnings("resource")
	public boolean CPCProcess(String action) {
		try {
			FileInputStream fis = new FileInputStream(CPCData());
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheetSearch = workbook.getSheet("CPCSearch");
			int lastRowSearch = sheetSearch.getLastRowNum();

			String type = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", 1, 0));
			if (action.toUpperCase().contentEquals("SEARCH")) {
				for (int i = 1; i <= lastRowSearch; i++) {
					if (type.toUpperCase().contentEquals("BASIC")) {
						String CartID = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 1));
						basicSerach(CartID);
						UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "backButton"),
								driver);
						return true;
					}
					if (type.toUpperCase().contentEquals("ADVANCE")) {
						String name = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 2));
						String emailID = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 3));
						String LicenceCode = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 4));
						String DeliveryOffice = String
								.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 5));
						String status = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 6));
						String Modality = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 7));
						String RegistrationSource = String
								.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 8));
						String ProviderSource = String
								.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 9));
						String dateFrom = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 10));
						String dateTo = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 11));
						advanceSearch(name, emailID, LicenceCode, DeliveryOffice, status, Modality, RegistrationSource,
								ProviderSource, dateFrom, dateTo);
						UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "backButton"),
								driver);
						return true;
					}

				}

			}

			else if (action.toUpperCase().contentEquals("UPDATE")) {

				Sheet sheet = workbook.getSheet("CPCSearch");
				int lastRow = sheet.getLastRowNum();
				for (int i = 1; i <= lastRow; i++) {
					String typeUpdate = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 0));
					if (typeUpdate.toUpperCase().contentEquals("BASIC")) {
						String CartID = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 1));
						Assert.assertTrue(basicSerach(CartID), "The serached Registration Detail:");
						if (UIUtils.isObjectExist(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"))) {
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"), driver);
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 0)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editIntacctID"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 0)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 1)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editFirstName"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 1)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 2)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editLastName"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 2)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 3)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editEmailAddress"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 3)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 4)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editClientID"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 4)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 5)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
												"editPaymentMethod"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 5)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 6)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPONumber"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 6)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 7)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPODate"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 7)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 8)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editLicenseCode"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 8)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 9)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editCheckNumber"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 9)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 10)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editBatchId"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 10)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 11)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editInvoicePrice"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 11)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 12)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editStatus"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 12)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 13)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
												"editExpectedStartDate"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 13)));

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "saveButton"), driver);

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "backButton"), driver);
						} else {
							System.out.println("The Regitration can't be edited");
							boolean serachCPCResult = false;
							UIUtils.attachScreenShot(ExtentManager.extentTest,
									"The searched registration is not editable", serachCPCResult);
							continue;
						}
						return true;
					}

					if (typeUpdate.toUpperCase().contentEquals("ADVANCE")) {
						String name = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 2));
						String emailID = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 3));
						String LicenceCode = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 4));
						String DeliveryOffice = String
								.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 5));
						String status = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 6));
						String Modality = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 7));
						String RegistrationSource = String
								.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 8));
						String ProviderSource = String
								.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 9));
						String dateFrom = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 10));
						String dateTo = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCSearch", i, 11));
						Assert.assertTrue(
								advanceSearch(name, emailID, LicenceCode, DeliveryOffice, status, Modality,
										RegistrationSource, ProviderSource, dateFrom, dateTo),
								"The serached Registration Detail:");

						if (UIUtils.isObjectExist(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"))) {

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"), driver);

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 0)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editIntacctID"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 0)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 1)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editFirstName"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 1)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 2)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editLastName"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 2)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 3)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editEmailAddress"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 3)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 4)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editClientID"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 4)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 5)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
												"editPaymentMethod"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 5)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 6)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPONumber"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 6)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 7)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPODate"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 7)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 8)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editLicenseCode"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 8)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 9)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editCheckNumber"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 9)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 10)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editBatchId"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 10)));
							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 11)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editInvoicePrice"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 11)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 12)).length() > 1)
								UIUtils.selectValue(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editStatus"),
										"text=" + String
												.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 12)));

							if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 13)).length() > 1)
								UIUtils.inputText(driver,
										TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
												"editExpectedStartDate"),
										String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CPCUpdate", i, 13)));

							UIUtils.scrollWindow(driver, "Up");
							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "saveButton"), driver);

							UIUtils.clickElement(
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "backButton"), driver);
							Robot robot = new Robot();
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_ADD);
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_ADD);
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_ADD);
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_ADD);
						}
						return true;
					} else {
						System.out.println("The search option mentioned in the data sheet is " + type);
						System.out.println("The search option should be Basic or Advance");
						continue;
					}
				}
			} else {
				System.out.println("The action command should be Serach or Create");
				return false;

			}
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean registrationStatusVerification(String status) {
		LOGGER.info("Verifying the Status---"+status);
		try {

			if (status.contentEquals("Processed/Access Granted") || status.contentEquals("Deleted")) {
				if (UIUtils.isObjectExist(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_View"))
						&& !UIUtils.isObjectExist(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"))) {
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					boolean serachCPCResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The searched registration is not editable",
							serachCPCResult);
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					return true;
				} else {
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					boolean serachCPCResult = false;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The searched registration is not editable",
							serachCPCResult);
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					return false;
				}
			}
			if (status.contentEquals("Awaiting Access") || status.contentEquals("Pending")) {

				if (UIUtils.isObjectExist(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit")))

					UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The searched result is completed", result);

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Edit"),
						driver);

				Select statusList = new Select(
						driver.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editStatus")));
				List<WebElement> webelement = statusList.getOptions();

				int length = webelement.size();
				for (WebElement web : webelement) {

					// System.out.println("The Value of first status is "+web.getText());
					for (int i = 0; i < length; i++) {
						if (web.getText().contains("Pending") || web.getText().contains("Deleted")
								|| web.getText().contains("Awaiting Access")
								|| web.getText().contains("Processed/Access Granted")) {
							boolean serachCPCResult = true;
							UIUtils.attachScreenShot(ExtentManager.extentTest,
									"The searched registration is not editable", serachCPCResult);
							System.out.println("The Registration Status process is correct");
							return true;
						} else {
							boolean serachCPCResult = false;
							UIUtils.attachScreenShot(ExtentManager.extentTest,
									"The searched registration is not editable", serachCPCResult);
							return false;
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean serachCPCResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The searched registration is not editable",
					serachCPCResult);
			return false;
		}
		return false;
	}

	public boolean fieldExistanceVerification(String fieldName) {
		try {
			switch (fieldName.toUpperCase()) {
			case "INTACCTID":
				if (isElementExists(SCREENNAME, "editIntacctID")) {
					boolean fieldExist = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct ID is exist", fieldExist);
					return true;
				} else {
					boolean fieldExist = false;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct ID is exist", fieldExist);
					return false;
				}
			case "FIRST NAME":
				break;
			default:
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean regressionTestCases() {
		try {
			FileInputStream fis = new FileInputStream(testCases());
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("CPC");
			int lastRow = sheet.getLastRowNum();

			for (int i = 1; i <= lastRow; i++) {
				String status = (String) ExcelUtils.getCellValue(testCaseWorkbook, "CPC", i, 5);
				if (status.contentEquals("Yes")) {
					System.out.println("The Automation status of " + i + " is " + status);
				} else if (status.contentEquals("No")) {
					System.out.println("The Automation status of " + i + " is " + status);
				} else {
					System.out.println("The Automation status of " + i + " is Blank");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean clickSaveButton() {
		try {
//			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "saveButton"),
//					driver);
			LOGGER.info("Clicking the Save button");
			UIUtils.scrollWindow(driver, "Up");
			Thread.sleep(8000);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "saveButton"), 50);
			driver.findElement(By.xpath("//button[@id='btnApprove']")).click();
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Save button is clicked", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Save button is clicked", result);
			return false;
		}
	}

	public boolean clickEditButton() {
		try {
			LOGGER.info("Clicking the Edit/Cancel button");
//			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "saveButton"),
//					driver);
			UIUtils.scrollWindow(driver, "Up");
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "cancelButton"), 20);
			driver.findElement(By.xpath("(//a[@class='btn btn-default'])[1]")).click();
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Edit button is clicked", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Edit button is clicked", result);
			return false;
		}
	}

	public boolean enterIntacctID(String intacctID) {
		try {
			LOGGER.info("Entering the Intacct ID "+intacctID);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editIntacctID"), 20);
			UIUtils.inputText(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editIntacctID"),
					intacctID);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct ID is entered", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct ID is entered", result);
			return false;
		}
	}

	public boolean selectStatus(String Status) {
		try {
			LOGGER.info("Selecting the Status");
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editStatus"), 20);
			UIUtils.selectValue(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editStatus"),
					"text=" + Status);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Status is selected", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Status is selected", result);
			return false;
		}
	}

	public boolean enterExpectedDate(String expectedDate) {
		try {
			LOGGER.info("Entering the Expected Date");
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editExpectedStartDate"),
					20);
			UIUtils.inputText(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editExpectedStartDate"),
					expectedDate);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Expected Start Date is entered", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Expected Start Date is entered", result);
			return false;
		}
	}

	public boolean enterNotes(String notes) {
		try {
			LOGGER.info("Entering the Notes");
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editNotes"), 50);
			driver.switchTo().frame(0);
			UIUtils.inputText(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "editNotes"), notes);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Note is entered", result);
			driver.switchTo().defaultContent();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Note is entered", result);
			return false;
		}
	}

	public boolean intacctIDVerification(String condition, String intacctID) {
		try {
			LOGGER.info("Intacct ID verification Process-------");
			enterIntacctID(intacctID);
			//clickSaveButton();
			//clickEditButton();
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "IntacctIcon"), driver);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "IntacctDetail_Header"), 20);
			switch (condition.toUpperCase()) {
			case "VALID":
				if (UIUtils.isObjectExist(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "IntacctDetail"))) {
					List<WebElement> intacctResult = driver
							.findElements(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "IntacctDetail"));
					for (WebElement webElement : intacctResult) {
						if (webElement.getText().contains("Intacct Id")) {
							boolean serachIntacctResult = true;
							UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail",
									serachIntacctResult);
							UIUtils.clickElement(TestDriver.getInstance().getObjRep()
									.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_Close"), driver);
							return true;
						}
					}
					break;
				} else {
					boolean serachIntacctResult = false;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail", serachIntacctResult);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter",
							"SearchedIntacct_Close"), driver);
					return false;
				}
			case "INVALID":
				if (UIUtils.isObjectExist(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "IntacctDetail"))) {
					boolean serachIntacctResult = false;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail", serachIntacctResult);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter",
							"SearchedIntacct_Close"), driver);
					return false;
				} else {
					boolean serachIntacctResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail", serachIntacctResult);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter",
							"SearchedIntacct_Close"), driver);
					return true;
				}
			case "BLANK":
				if (UIUtils.isObjectExist(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "SearchedIntacct_Blank"))) {
					boolean serachIntacctResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail", serachIntacctResult);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter",
							"SearchedIntacct_Close"), driver);
					return true;
				} else {
					boolean serachIntacctResult = false;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail", serachIntacctResult);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter",
							"SearchedIntacct_Close"), driver);
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean serachIntacctResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail", serachIntacctResult);
			return false;
		}
		boolean serachIntacctResult = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The Intacct Detail", serachIntacctResult);
		return false;
	}

	public boolean intacctIDValidation() {
		try {
			LOGGER.info("Intacct ID Validation Process-------");
			if (isElementExists("SMIL_ProcessingCenter", "IntacctID_Error")) {
				WebElement intacctIDError = driver.findElement(
						TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "IntacctID_Error"));
				if (intacctIDError.getText()
						.contentEquals("Intacct id is required and must be a number with valid minimum 5 digits.")
						&& isElementExists("SMIL_ProcessingCenter", "IntacctIcon")
						&& isElementExists("SMIL_ProcessingCenter", "saveButton")) {
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					boolean intacctValidationResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The validation of Intacct ID",
							intacctValidationResult);
					return true;
				}
			} else {
				boolean intacctValidationResult = false;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The validation of Intacct ID",
						intacctValidationResult);
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean intacctValidationResult = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The validation of Intacct ID", intacctValidationResult);
			return false;
		}
		boolean intacctValidationResult = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The validation of Intacct ID", intacctValidationResult);
		return false;
	}

	public Map<String, String> getIntacctDetail() {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "IntacctIcon"), driver);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "IntacctDetail_Header"), 20);
			WebElement PersonID = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_PersonID"));
			WebElement IntacctID = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_IntacctID"));
			WebElement Salutation = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_Salutation"));
			WebElement FirstName = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_FirstName"));
			WebElement MiddleInitial = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_MiddleInitial"));
			WebElement LastName = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_LastName"));
			WebElement EmailAddress = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_EmailAddress"));
			WebElement WorkPhone = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_WorkPhone"));
			WebElement MobilePhone = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_MobilePhone"));
			WebElement Fax = driver.findElement(
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "SearchedIntacct_Fax"));
			WebElement Suffix = driver.findElement(
					TestDriver.getInstance().getObjRep().getLocator("SMIL_ProcessingCenter", "SearchedIntacct_Suffix"));
			WebElement JobStatus = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_JobStatus"));
			WebElement Company = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_Company"));
			WebElement JobTitle = driver.findElement(TestDriver.getInstance().getObjRep()
					.getLocator("SMIL_ProcessingCenter", "SearchedIntacct_JobTitle"));
			String value = PersonID.getText();
			map.put("PersonID", value);

			value = IntacctID.getText();
			map.put("IntacctID", value);

			value = Salutation.getText();
			map.put("Salutation", value);

			value = FirstName.getText();
			map.put("FirstName", value);

			value = MiddleInitial.getText();
			map.put("MiddleInitial", value);

			value = LastName.getText();
			map.put("LastName", value);

			value = EmailAddress.getText();
			map.put("EmailAddress", value);

			value = WorkPhone.getText();
			map.put("WorkPhone", value);

			value = MobilePhone.getText();
			map.put("MobilePhone", value);

			value = Fax.getText();
			map.put("Fax", value);

			value = Suffix.getText();
			map.put("Suffix", value);

			value = JobStatus.getText();
			map.put("JobStatus", value);

			value = Company.getText();
			map.put("Company", value);

			value = JobTitle.getText();
			map.put("JobTitle", value);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}
