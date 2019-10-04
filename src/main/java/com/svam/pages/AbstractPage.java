package com.svam.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.svam.driver.TestDriver;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public abstract class AbstractPage implements BasePage {
	public WebDriver driver;
	static Logger LOGGER = Logger.getLogger(AbstractPage.class);

	public AbstractPage(WebDriver driver) {
		this.driver = driver;

	}

	public boolean isElementExists(String screenName, String elementKey) {
		LOGGER.info("verifing the presence of " + elementKey + " in " + screenName + " page..");
		boolean result=UIUtils.isObjectExist(driver, TestDriver.getInstance().getObjRep().getLocator(screenName, elementKey));
		if(result)
			ExtentManager.extentTest.log(LogStatus.PASS, elementKey+" is available in "+screenName.replaceAll("_", " "));
		else
			ExtentManager.extentTest.log(LogStatus.FAIL, elementKey+" is available in "+screenName.replaceAll("_", " "));
		return result;
	}

	public boolean isElementEnabled(String screenName, String elementKey) {
		WebElement element = null;

		try {
			element = UIUtils.funcFindElement(driver,
					TestDriver.getInstance().getObjRep().getLocator(screenName, elementKey));
		} catch (Exception e) {
		}

		return element.isEnabled();
	}

	public abstract boolean isPageOpen();

	public abstract boolean validateUI();
}