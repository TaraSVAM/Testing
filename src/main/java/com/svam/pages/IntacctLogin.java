package com.svam.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.svam.driver.TestDriver;
import com.svam.utils.UIUtils;

public class IntacctLogin extends AbstractPage {

	public IntacctLogin(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		return false;
	}

	@Override
	public boolean validateUI() {
		return UIUtils.isObjectExist(driver,
				TestDriver.getInstance().getObjRep().getLocator("Intacct_Login", "txtCompany"));
	}

	public IntacctGems login(String company, String userID, String password) throws Exception {
		try {
			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator("Intacct_Login", "txtCompany"),
					company);
			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator("Intacct_Login", "txtLogin"),
					userID);
			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator("Intacct_Login", "txtPassword"),
					password);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("Intacct_Login", "btnSignIn"), driver);

			/*
			 * if(isElementExists("Intacct_Login", "verificationCode")) {
			 * UIUtils.waitUntilElementExists(driver,
			 * TestDriver.getInstance().getObjRep().getLocator("Intacct_Login",
			 * "NameSerach"), 100); }
			 */

			return PageFactory.initElements(driver, IntacctGems.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, IntacctGems.class);
	}

}
