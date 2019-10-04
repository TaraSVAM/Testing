package com.svam.driver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.svam.framework.Apps;
import com.svam.framework.ObjectRepository;
import com.svam.framework.TestReport;
import com.svam.utils.BrowserTypes;
import com.svam.utils.Config;
import com.svam.utils.ExcelUtils;
import com.svam.utils.UIUtils;

public class TestDriver {
	static Logger LOGGER = Logger.getLogger(TestDriver.class);

	private static TestDriver testDriver = new TestDriver();

	private static Config config;
	private static String configWorkbook;

	private ObjectRepository objRep;
	private String testModulesPath;
	private static String appBaseURL;
	private static String PublicWebsiteURL;
	private static String SMILURL;
	private static String ReSellerURLAustralia;
	private static String ReSellerURLGermany;
	private static String ReSellerURLSweden;
	private static String ReSellerURLSingapore;
	private static String ReSellerURLChina;
	private static String ReSellerURLIndia;
	private static String ReSellerURLUK;
	private static String ReSellerURLSouthAfrica;
	private static String ReSellerURLItaly;
	private static String ReSellerURLSpain;
	private static String MyESIURL;
	private static String IntacctURL;

	public static WebDriver driver;
	private String mainWindowHandle;

	private static boolean remoteExecution = false;
	private boolean ciExecution = false;
	private static String gridURL;

	private String execEnvironment;

	private static int totalTestScript;
	private static int currentTestScript;

	public TestDriver() {
	}

	static {
		config = new Config("Framework\\Test_Config\\config.properties");

		try {
			configWorkbook = new File("Framework\\Test_config\\Config.xls").getCanonicalPath();
		} catch (IOException e) {
			LOGGER.error("Unable to find Config workbook", e);
		}
	}

	public static TestDriver getInstance() {
		return testDriver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getMainWindowHandle() {
		return mainWindowHandle;
	}

	public static Config getConfig() {
		return config;
	}

	public static String getConfigWorkbook() {
		return configWorkbook;
	}

	public ObjectRepository getObjRep() {
		return objRep;
	}

	public String getTestModulesPath() {
		return testModulesPath;
	}

	public String getAppBaseURL() {
		return appBaseURL;
	}
	
	public String getPublicWebsiteURL() {
		return PublicWebsiteURL;
	}

	public String getSMILURL() {
		return SMILURL;
	}

	public String getReSellerURLAustralia() {
		return ReSellerURLAustralia;
	}

	public String getReSellerURLGermany() {
		return ReSellerURLGermany;
	}

	public String getReSellerURLSwedenL() {
		return ReSellerURLSweden;
	}

	public String getReSellerURLSingapore() {
		return ReSellerURLSingapore;
	}

	public String getReSellerURLChina() {
		return ReSellerURLChina;
	}

	public String getReSellerURLIndia() {
		return ReSellerURLIndia;
	}

	public String getReSellerURLUK() {
		return ReSellerURLUK;
	}

	public String getReSellerURLSouthAfrica() {
		return ReSellerURLSouthAfrica;
	}

	public String getReSellerURLItaly() {
		return ReSellerURLItaly;
	}

	public String getReSellerURLSpain() {
		return ReSellerURLSpain;
	}

	public String getMyESIURL() {
		return MyESIURL;
	}

	public String getIntacctURL() {
		return IntacctURL;
	}

	public boolean isRemoteExecution() {
		return remoteExecution;
	}

	public boolean isCiExecution() {
		return ciExecution;
	}

	public String getGridURL() {
		return gridURL;
	}

	public String getExecEnvironment() {
		return execEnvironment;
	}

	public static int getTotalTestScript() {
		return totalTestScript;
	}

	public static int getCurrentTestScript() {
		return currentTestScript;
	}

	// Framework Initialization
	@BeforeSuite
	public void suiteSetup() {
		try {
			System.out.println("------------Running @BeforeSuite of Driver-------------");
			// Reporting setup
			TestReport.getInstance().reportingSetup(configWorkbook);

			PropertyConfigurator.configure("log4j.properties");

			// Object Repository Population
			testDriver.objRep = new ObjectRepository("Framework\\OR\\ObjectRepository.xls");

			testDriver.frameworkSetup();
			driver = driverInstantiation(config.getPropertyValue("Browser").toUpperCase());
			testDriver.mainWindowHandle = driver.getWindowHandle();
		} catch (Exception e) {
			LOGGER.error("Exception " + e.getClass().getName() + " caught from suite setup method", e);
			e.printStackTrace();
		}
	}

	private void frameworkSetup() throws IOException {
		testModulesPath = new File("Framework\\OR").getCanonicalPath();

		if ("Yes".equalsIgnoreCase(String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=RemoteExecution")).trim())) {
			remoteExecution = true;
		}

		gridURL = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=GridURL")).trim();

		if ("Yes".equalsIgnoreCase(
				String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=CIExecution")).trim())) {
			ciExecution = true;
		}

		execEnvironment = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=Environment"))
				.trim();
	}

	public static WebDriver driverInstantiation(String browserName) throws Exception {
		WebDriver driver;
		if (remoteExecution) {
			driver = UIUtils.createDriverInstance(BrowserTypes.valueOf(browserName), "", gridURL);
		} else {
			driver = UIUtils.createDriverInstance(BrowserTypes.valueOf(browserName), "");
		}

//		appBaseURL = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=SMILURL"))
//				.trim();
//		driver.navigate().to(appBaseURL);
//		driver.manage().window().maximize();
//		UIUtils.waitForPageLoad(driver);

		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
		}

//		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
//		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		return driver;
	}

	// For Application Initialization
	public static void appInstantiation(Apps app) throws Exception {

		appBaseURL = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=AppBaseURL"))
				.trim();
		PublicWebsiteURL = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=PublicWebsiteURL")).trim();
		SMILURL = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=SMILURL")).trim();
		ReSellerURLAustralia = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLAustralia")).trim();
		ReSellerURLGermany = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLGermany")).trim();
		ReSellerURLSweden = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLSweden")).trim();
		ReSellerURLSingapore = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLSingapore")).trim();
		ReSellerURLChina = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLChina")).trim();
		ReSellerURLIndia = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLIndia")).trim();
		ReSellerURLUK = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLUK"))
				.trim();
		ReSellerURLSouthAfrica = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLSouthAfrica"))
				.trim();
		ReSellerURLItaly = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLItaly")).trim();
		ReSellerURLSpain = String
				.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=ReSellerURLSpain")).trim();
		MyESIURL = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=MyESIURL")).trim();
		IntacctURL = String.valueOf(ExcelUtils.getCellValue(configWorkbook, "Config", "Value", "Key=IntacctURL"))
				.trim();
		switch (app) {
		case PublicWebsite:
			driver.navigate().to(PublicWebsiteURL);
			break;
		case SMIL:
			driver.navigate().to(SMILURL);
			if(UIUtils.isObjectExist(driver, TestDriver.getInstance().getObjRep().getLocator("SMIL_Login", "txtUsername")))
			break;
			else
			driver.navigate().to(SMILURL);
			break;
		case ReSellerAustralia:
			driver.navigate().to(ReSellerURLAustralia);
			break;
		case ReSellerGermany:
			driver.navigate().to(ReSellerURLGermany);
			break;
		case ReSellerSweden:
			driver.navigate().to(ReSellerURLSweden);
			break;
		case ReSellerSingapore:
			driver.navigate().to(ReSellerURLSingapore);
			break;
		case ReSellerChina:
			driver.navigate().to(ReSellerURLChina);
			break;
		case ReSellerIndia:
			driver.navigate().to(ReSellerURLIndia);
			if(UIUtils.isObjectExist(driver, TestDriver.getInstance().getObjRep().getLocator("Reseller", "Home")))
			break;
			else
				driver.navigate().to(ReSellerURLIndia);
			break;
		case ReSellerUK:
			driver.navigate().to(ReSellerURLUK);
			break;
		case ReSellerSouthAfrica:
			driver.navigate().to(ReSellerURLSouthAfrica);
			break;
		case ReSellerItaly:
			driver.navigate().to(ReSellerURLItaly);
			break;
		case ReSellerSpain:
			driver.navigate().to(ReSellerURLSpain);
			break;
		case MyESI:
			driver.navigate().to(MyESIURL);
			break;
		case Intacct:
			driver.navigate().to(IntacctURL);
			break;
		default:
			Assert.fail("Application not found");

		}

		driver.manage().window().maximize();
		UIUtils.waitForPageLoad(driver);

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
		// return driver;
	}

	@AfterClass
	public void suiteTearDown() {
		System.out.println("------------Running @AfterClass of Driver-------------");
		TestReport.getInstance().endTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
				.format(Calendar.getInstance().getTime());

		LOGGER.info("All tests get executed");

		try {
			TestReport.getInstance().loginReport("Summary", "</table>");
			TestReport.getInstance().loginReport("Detail", "</table>");
		} catch (IOException e) {
			LOGGER.error("IOException caught while finalizing the summary & detail reports", e);
		}
	}

	@AfterSuite
	public void testTearDown() {
		// For XP & Win 7
		System.out.println("------------Running @AfterSuite of Driver-------------");

		System.out.println("After Suite");
		try {

			Runtime.getRuntime().exec("tskill chromedriver");

		} catch (Exception e) {
		}

		try {
			Runtime.getRuntime().exec("tskill GeckoDriver");
		} catch (Exception e) {
		}

		try {
			Runtime.getRuntime().exec("tskill IEDriverServer");
		} catch (Exception e) {
		}

		// For Win 8 & Above
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (Exception e) {
		}

		try {
			Runtime.getRuntime().exec("taskkill /F /IM GeckoDriver.exe");
		} catch (Exception e) {
		}

		try {
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		} catch (Exception e) {
		}
	}
}