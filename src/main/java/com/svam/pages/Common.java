package com.svam.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.svam.driver.TestDriver;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class Common extends AbstractPage {
	static Logger LOGGER = Logger.getLogger(Common.class);

	public Common(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		return UIUtils.isObjectExist(driver, TestDriver.getInstance().getObjRep().getLocator("Common", "menuProducts"));
	}

	@Override
	public boolean validateUI() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean switchWindow(WebDriver driver) throws InterruptedException {
		UIUtils.waitForPageLoad(driver, 10);
		Thread.sleep(5000);
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			if (!window.equals(TestDriver.getInstance().getMainWindowHandle())) {
				driver.switchTo().window(window);
				UIUtils.waitForPageLoad(driver, 10);
				break;
			}
		}
		LOGGER.info(windows.size() + " windows are opened.");
		return (windows.size() > 1); /* returns no. of windows */
	}

	public BasePage clickMenuTab(String menuTab, String... subMenu) throws Exception {
		LOGGER.info("Clicking to " + menuTab + " Menu........");
		UIUtils.waitForPageLoad(driver, 10);
		Thread.sleep(1000);
		UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("SMIL_Common", menuTab), driver);

		UIUtils.waitForPageLoad(driver, 20);
		boolean result = true;

		switch (menuTab.toUpperCase()) {
		case "COURSES":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Courses is clicked", result);
			return PageFactory.initElements(driver, SMILCourses.class);
		case "ITEMS":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Items is clicked", result);
			return PageFactory.initElements(driver, SMILItems.class);
		case "SESSIONS":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Sessions is clicked", result);
			return PageFactory.initElements(driver, SMILSessions.class);
		case "FACILITIES":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Facilities is clicked", result);
			return PageFactory.initElements(driver, SMILFacilities.class);
		case "INSTRUCTORS":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Instructors is clicked", result);
			return PageFactory.initElements(driver, SMILInstructors.class);
		case "RESELLERAPPROVALS":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Reseller Approvals is clicked", result);
			return PageFactory.initElements(driver, SMILResellerApprovals.class);
		case "PROCESSINGCENTER":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Processing Center is clicked", result);
			return PageFactory.initElements(driver, SMILCPC.class);
		case "ENROLLMENTS":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Enrollments is clicked", result);
			return PageFactory.initElements(driver, SMILEnrollment.class);
		case "LICENSES":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Licenses is clicked", result);
			return PageFactory.initElements(driver, SMILLicenses.class);
		case "REPORTS":
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The menu Reports is clicked", result);
			return PageFactory.initElements(driver, SMILReports.class);
		default:
			break;
		}
		return null;
	}

	public void clickFooterMenu(String menu) throws Exception {
		LOGGER.info("clicking on " + menu);
		UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("Common", menu), driver);
	}

	public void search(String data) throws Exception {
		LOGGER.info("Entering " + data + " to search box");
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator("Common", "searchBox"), data);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		LOGGER.info(data + " entered and enter is hit");
	}

	public void clickOnMenuOptions(String mainMenu, String menu, String subMenu) throws Exception {

		String[] mainMenulocators = TestDriver.getInstance().getObjRep().getObject("Intacct_Gems", "mainMenu");
		String mainlocator = mainMenulocators[1].replaceAll("mmenu", mainMenu);

		String[] menulocators = TestDriver.getInstance().getObjRep().getObject("Intacct_Gems", "Menu");
		String menulocator = menulocators[1].replaceAll("mmenu", menu);

		String[] locators = TestDriver.getInstance().getObjRep().getObject("Intacct_Gems", "subMenu");
		String submenulocator = locators[1].replaceAll("mmenu", menu).replaceAll("subMenu", subMenu);

		UIUtils.clickElement(By.xpath(mainlocator), driver);
		UIUtils.clickElement(By.xpath(menulocator), driver);
		UIUtils.clickElement(By.xpath(submenulocator), driver);
	}

	public void clickOnNewMenuOptions(String mainMenu, String subMenu, String menu) throws Exception {
		UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator("Intacct_Gems", "newMainMenu"), driver);
		String[] mainMenulocators = TestDriver.getInstance().getObjRep().getObject("Intacct_Gems", "mainMenu");
		String mainlocator = mainMenulocators[1].replaceAll("mainMenuName", mainMenu);

		String[] locators = TestDriver.getInstance().getObjRep().getObject("Intacct_Gems", "subMenu");
		String submenulocator = locators[1].replaceAll("subMenuName", subMenu);

		String[] menulocators = TestDriver.getInstance().getObjRep().getObject("Intacct_Gems", "Menu");
		String menulocator = menulocators[1].replaceAll("menuName", menu);

		UIUtils.clickElement(By.xpath(mainlocator), driver);
		UIUtils.clickElement(By.xpath(submenulocator), driver);
		UIUtils.clickElement(By.xpath(menulocator), driver);
	}
	
	public static String decryption(String password) {
		byte[] decoded = Base64.decodeBase64(password);
		return new String(decoded); 
	}
	
	public static String encryption(String password) {
		byte[] encode = Base64.encodeBase64(password.getBytes());
		return new String(encode); 
	}
}