package com.svam.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.svam.driver.TestDriver;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class SMILResellerApprovals extends AbstractPage{
	
	ExtentTest extentTest;
	private static final String SCREENNAME = "SMIL_ResellerApprovals";
	public SMILResellerApprovals(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		return false;
	}

	@Override
	public boolean validateUI() {
		UIUtils.waitUntilElementExists(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ResellerApprovals"), 10);

		return isElementExists(SCREENNAME, "ResellerApprovals") && isElementExists("SMIL_Common", "ResellerApprovals");
	}
	
	public boolean resellerSearchResultVerification(String deliveryOffice, String emailID) {
		try {
			LOGGER.info("Searching the Reseller Registration");
			UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "DeliveryOffice"),deliveryOffice);
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			List<WebElement> resultsEmail = driver
					.findElements(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "result_Email"));
			
			for (WebElement webElement : resultsEmail) {
								
				if (webElement.getText().contains(emailID)) {
					boolean result = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration from Reseller in CPC", result);
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					UIUtils.scrollWindow(driver, "Up");
					System.out.println("The created registration from Reseller for email ID is------"+webElement.getText());
					return true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration from Reseller in CPC", result);
			return false;
		}
		boolean result = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration from Reseller in CPC", result);
		return false;
	}
	
	public boolean navigateResellerSearchResult(String emailID) {
		LOGGER.info("Clicking the Edit Icon of email ID---"+emailID);
		int count=0;
		try {
			List<WebElement> cells = driver.findElements(By.xpath("//tbody[@id='resultsRows']//td"));
			for(WebElement cell: cells) {
				if(cell.getText().contentEquals(emailID)) {
					int rowcount = Math.round(count/9);
				driver.findElement(By.xpath("//tbody[@id='resultsRows']//tr[@id='"+rowcount+"']//td[9]//a[2]")).click();
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Result is found and Edit button is clicked", result);
				return true;
				}
				else if(count<=cells.size()) {
					count++;
					continue;
				}
				else {
					boolean result = false;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Result is not found", result);
					return false;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The search Result", result);
			return false;
		}
		boolean result = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The search Result", result);
		return false;
		
	}
	
	public boolean clickSaveButton() {
		LOGGER.info("Clicking the Save button");
		try {
			UIUtils.scrollWindow(driver, "Up");
			Thread.sleep(8000);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ResellerSave"), 50);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ResellerSave"),
			driver);
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
	
	public boolean clickApproveButton() {
		LOGGER.info("Clicking the Approve button");
		try {
			UIUtils.scrollWindow(driver, "Up");
			Thread.sleep(8000);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ResellerApprove"), 50);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "ResellerApprove"),
			driver);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Approve button is clicked", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Approve button is clicked", result);
			return false;
		}
	}
	
	public boolean enterLicenceCode(String licenceCode) {
		LOGGER.info("Entering the Save button");
		try {
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Edit_LicenseCode"), 50);
			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Edit_LicenseCode"), licenceCode);
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Licence Code is entered", result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Licence Code is entered", result);
			return false;
		}
	}
	
	public boolean resellerLicenceCodeVerification() {
		LOGGER.info("Checking the error message of Licence Code");
		try {
			WebElement licenceCodeError = driver
					.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "LicenseCode_Error"));
			driver.findElement(By.xpath("//div[@id='PaymentInformation']//dd[2]//input")).click();
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			UIUtils.scrollWindow(driver, "Down");
			if(licenceCodeError.getText().contains("is invalid")) {
				System.out.println("The Licence Error Code is----"+licenceCodeError.getText());
				boolean result = true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Licence Code is verified", result);
				return true;
			}
			else {
				boolean result = false;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Licence Code is verified", result);
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
