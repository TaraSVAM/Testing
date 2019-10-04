package com.svam.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.svam.driver.TestDriver;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class SMILLogin extends AbstractPage {
	
	private final String LoginTitle = "Service Management Integration for Learning - Login";

	public SMILLogin(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		if (driver.getTitle().equalsIgnoreCase(LoginTitle)) {
			boolean result = true;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Login Page", result);
			return true;
		}
		else
		{
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The SMIL Login Page", result);
			return false;
		}
	}

	@Override
	public boolean validateUI() {
		return isElementExists("SMIL_Login", "txtUsername") && isElementExists("SMIL_Login", "txtPassword");

	}
	
	// UIUtils.openNewTab();
			// ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			// driver.switchTo().window(tabs.get(0));
			// driver.get(TestDriver.appInstantiation(Apps.SMIL));

	public SMILCPC login(String username, String password){
		try {
				LOGGER.info("Login into SMIL Application");
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername"),
						username);
				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtPassword"),
						password);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "btnLogin"), driver);
				return PageFactory.initElements(driver, SMILCPC.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			boolean result=false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Server is down", result);
		}
		return null;
				
	}
	public void logout(){
		try {	
			LOGGER.info("Logout from SMIL Application");
			Thread.sleep(5000);
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator("SMIL_LogOut", "User_Photo"), 30);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_LogOut", "User_Photo"), driver);
				Thread.sleep(5000);
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator("SMIL_LogOut", "Logout_Button"), 50);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_LogOut", "Logout_Button"), driver);
				Thread.sleep(5000);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_LogOut", "Logout_Button_Confirmation"), driver);
				boolean result=true;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "SMIL Logout sucessful", result);
		} catch (Exception e) {
			e.printStackTrace();
			boolean result=false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Server is down", result);
		}
		
				
	}

}
