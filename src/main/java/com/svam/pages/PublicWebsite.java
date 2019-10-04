package com.svam.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.svam.driver.TestDriver;
import com.svam.utils.ExcelUtils;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class PublicWebsite extends AbstractPage {

	public static String PublicWebsiteWorkbook;
	private static final String SCREENNAME = "PublicWebsite";
	ExtentTest extentTest;

	public static String publicData() throws IOException {
		return PublicWebsiteWorkbook = new File("Framework\\Test_Data\\Integration.xls").getCanonicalPath();
	}

	public PublicWebsite(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		return false;
	}

	@Override
	public boolean validateUI() {
		return UIUtils.isObjectExist(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "findACourse"));
	}

	public boolean findCource() throws Exception {
		LOGGER.info("Searching for a Cource");
		try {
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "findACourse"), driver);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "addToCart"), 30);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is searched", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is not searched", result);
		}
		return false;

	}

	public boolean addToCart() {
		LOGGER.info("Adding Cource to Cart");
		try {
			driver.findElement(By.xpath("//a[contains(text(),'Add To Cart')]")).click();
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is added in Cart", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is not added in Cart", result);
			return false;
		}
	}

	public boolean shoppingCart() throws Exception {
		LOGGER.info("Selecting the Course from the Cart");
		try {
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "shoppingCart"), 30);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "shoppingCart"), driver);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Cartnext"), 30);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Cartnext"), driver);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Shopping Cart detail", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Shopping Cart navigation issue", result);
			return false;
		}
	}

	public boolean newRegistration() {
		LOGGER.info("Clicking the New User button");
		try {
			Thread.sleep(5000);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "newUser"), 30);
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "newUser"), driver);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "New Registration Page", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "New Registration Page issue", result);
			return false;
		}

	}

	public boolean registrationData() throws Exception {

		try {
			FileInputStream fis = new FileInputStream(publicData());
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("PublicWebsite");
			int lastRow = sheet.getLastRowNum();
			Robot robot = new Robot();
			System.out.println("Registration has been started");
			for (int i = 1; i <= lastRow; i++) {
				findCource();
				addToCart();
				shoppingCart();
				newRegistration();

				System.out.println("Data inserting in Public Wesite");
				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 0)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "firstName"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 0)));

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 1)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "lastName"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 1)));

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 2)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "company"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 2)));

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 3)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "phone"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 3)));

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 4)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "email"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 4)));

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 5)).length() > 1)
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "newPassword"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 5)));
				driver.findElement(By.xpath("//button[@class='btn btn-next btn-next-personal']")).click();
				// UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,"PersonNext"),
				// driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "POOrder"), 30);

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "POOrder"), driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PONumber"), 30);

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PONumber"),
						String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 6)));
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PODate"), driver);
				driver.findElement(By.id("po-date")).sendKeys(Keys.ENTER);

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 7)).length() > 1)
					UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "country"),
							"text=" + String
									.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 7)));

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "address"), 30);

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 8)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "address"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 8)));

				if (String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 9)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "city"),
							String.valueOf(ExcelUtils.getCellValue(PublicWebsiteWorkbook, "PublicWebsite", i, 9)));

				driver.findElement(By.xpath("//button[@class='btn btn-next btn-next-billing']")).click();

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "finish"), driver);
				Thread.sleep(10000);
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is completed", result);
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "findACourse"), 30);

				UIUtils.scrollWindow(driver, "Up");
				UIUtils.scrollWindow(driver, "Up");
				UIUtils.scrollWindow(driver, "Up");
				UIUtils.scrollWindow(driver, "Up");
				UIUtils.scrollWindow(driver, "Up");
				UIUtils.scrollWindow(driver, "Up");

				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ADD);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ADD);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ADD);
				robot.keyRelease(KeyEvent.VK_ADD);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				if (i == lastRow)
					return true;
			}

			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not completed due to this error",
					result);
			return false;
		}
		boolean result = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not completed due to this error",
				result);
		return false;
	}

	public boolean newRegistrationProcess(String firstName, String email, String paymentMethod) throws Exception {

		try {
			LOGGER.info("---------------New Registration Process-------------");

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "firstName"),
					firstName);

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "lastName"),
					"Testing");

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "company"),
					"Testing");

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "phone"),
					"987654321");

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "email"), email);

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "newPassword"),
					"india");
			driver.findElement(By.xpath("//button[@class='btn btn-next btn-next-personal']")).click();
			// UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,"PersonNext"),
			// driver);
			Thread.sleep(2000);

			if (paymentMethod == "Purchase Order") {
				LOGGER.info("Selecting Purchase Order");
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "POOrder"), 30);

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "POOrder"), driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PONumber"), 30);

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PONumber"),
						"1234");
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PODate"), driver);
				driver.findElement(By.id("po-date")).sendKeys(Keys.ENTER);
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Purchase Order Payment method is completed",
						result);
			}

			if (paymentMethod == "Check") {
				LOGGER.info("Selecting Check");
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Check"), 30);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Check"), driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Check_Number"), 30);
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Check_Number"),
						"54321");
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Check Payment method is completed", result);
			}

			if (paymentMethod == "License") {
				LOGGER.info("Selecting License");
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "License"), 30);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "License"), driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "License_Number"), 30);
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "License_Number"),
						"12345");
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The License Payment method is completed", result);
			}

			UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "country"),
					"India");

			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "address"), 30);

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "address"), "Delhi");

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "city"), "Delhi");

			driver.findElement(By.xpath("//button[@class='btn btn-next btn-next-billing']")).click();
			Thread.sleep(5000);
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			System.out.println("Finsh button is getting to clicked");
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "finish"), driver);
			Thread.sleep(10000);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "finish_Result"), 80);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is completed", result);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not completed due to some error",
					result);
			return false;
		}
	}

}
