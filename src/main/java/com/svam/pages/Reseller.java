package com.svam.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;
import com.svam.driver.TestDriver;
import com.svam.framework.Apps;
import com.svam.utils.ExcelUtils;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class Reseller extends AbstractPage {
	private final static String SCREENNAME = "Reseller";
	public static String IntegrationWorkbook;

	public static String resellerData() throws IOException {
		return IntegrationWorkbook = new File("Framework\\Test_Data\\Integration.xls").getCanonicalPath();
	}

	public Reseller(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean isPageOpen() {
		return isElementExists(SCREENNAME, "Home");
	}

	@Override
	public boolean validateUI() {
		return isElementExists(SCREENNAME, "Home");
	}

	public boolean loginReseller(String country) {
		try {

			// ExtentManager.extentTest.log(LogStatus.INFO, "Verifying the home page of
			// Reseller");
			LOGGER.info("Login into Reseller");
			switch (country.toUpperCase()) {
			case "AUSTRALIA":
				TestDriver.appInstantiation(Apps.ReSellerAustralia);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "GERMANY":
				TestDriver.appInstantiation(Apps.ReSellerGermany);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "SWEDEN":
				TestDriver.appInstantiation(Apps.ReSellerSweden);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "SINGAPORE":
				TestDriver.appInstantiation(Apps.ReSellerSingapore);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "CHINA":
				TestDriver.appInstantiation(Apps.ReSellerChina);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "INDIA":
				TestDriver.appInstantiation(Apps.ReSellerIndia);
				if(isPageOpen()) {
					UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
					return true;
				}
				else {
					UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
					return false;
				}
			case "UK":
				TestDriver.appInstantiation(Apps.ReSellerUK);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "SOUTHAFRICA":
				TestDriver.appInstantiation(Apps.ReSellerSouthAfrica);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "ITALY":
				TestDriver.appInstantiation(Apps.ReSellerItaly);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			case "SPAIN":
				TestDriver.appInstantiation(Apps.ReSellerSpain);
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			default:
				UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			UIUtils.attachScreenShot(ExtentManager.extentTest, "Home Page of Reseller Website", validateUI());
			return false;
		}

	}

	public boolean courseSelection(String country, String course) {
		LOGGER.info("Course is selecting in Reseller");
		List<WebElement> result = driver.findElements(By.xpath("//tbody//tr"));

		for (WebElement webElement : result) {
			if (webElement.getText().contains(course)) {
				switch (course) {

				// Project Management Core Courses
				case "PMC-CPM MPS520: Managing Projects":
					driver.findElement(By.xpath("//tr[2]//a")).click();
					return true;
				case "PMC-BQQ PLM520: Project Leadership, Management and Communications":
					driver.findElement(By.xpath("//tr[3]//a")).click();
					return true;
				case "PMC-BGK CON520: Contract Management Principles and Practices":
					driver.findElement(By.xpath("//tr[4]//a")).click();
					return true;
				case "PMC-BVF QTY520: Quality for Project Managers":
					driver.findElement(By.xpath("//tr[5]//a")).click();
					return true;
				case "PMC-BVZ SCC520: Scheduling and Cost Control":
					driver.findElement(By.xpath("//tr[6]//a")).click();
					return true;
				case "PMC-CTL RSK520: Risk Management":
					driver.findElement(By.xpath("//tr[7]//a")).click();
					return true;
				case "ITP-DBR MIT520: Managing IT Projects":
					driver.findElement(By.xpath("//tr[8]//a")).click();
					return true;
				case "PMC-BTP PMA520: Project Management Applications":
					driver.findElement(By.xpath("//tr[9]//a")).click();
					return true;

				case "PMC-BSJ PMP520: PMP Exam Preparation":
					if (country.contentEquals("Germany") || country.contentEquals("Sweden")
							|| country.contentEquals("Singapore") || country.contentEquals("China")
							|| country.contentEquals("India") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[10]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

				case "PMC-DJ4: PMP Exam Power Prep":
					if (country.contentEquals("Germany") || country.contentEquals("Sweden")
							|| country.contentEquals("Singapore") || country.contentEquals("China")
							|| country.contentEquals("India") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[11]//a")).click();
						return true;
					}

					else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[11]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {
						driver.findElement(By.xpath("//tr[10]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

				case "ITP-DBP ITR520: IT Risk Management":
					if (country.contentEquals("Germany") || country.contentEquals("Sweden")
							|| country.contentEquals("Singapore") || country.contentEquals("China")
							|| country.contentEquals("India") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[12]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[10]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {
						driver.findElement(By.xpath("//tr[11]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

					// Business Analysis
				case "BAP-DK6 TVR520: Testing Techniques for Tracing and Validating Requirements":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[16]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {
						driver.findElement(By.xpath("//tr[14]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {
						driver.findElement(By.xpath("//tr[17]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[13]//a")).click();
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {
						driver.findElement(By.xpath("//tr[15]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}
				case "BAP-D2J: Developing Use Cases":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[17]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {
						driver.findElement(By.xpath("//tr[18]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {
						driver.findElement(By.xpath("//tr[15]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[14]//a")).click();
						return true;
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {
						driver.findElement(By.xpath("//tr[16]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

				case "BAP-DWM: Business Process Modeling":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[18]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[19]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[16]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[15]//a")).click();
						return true;
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[17]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "BAP-GSR: Eliciting and Managing Requirements":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[19]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[20]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[17]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[17]//a")).click();
						return true;
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[18]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "BAP-GST: Analyzing Benefits and Refining Solutions":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[20]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[21]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[18]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[16]//a")).click();
						return true;
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[19]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "BAP-GSQ: Fundamentals of Business Analysis":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[21]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[22]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[19]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[18]//a")).click();
						return true;
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[20]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "BAP-GSW: Enterprise Business Analysis":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[22]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[23]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[20]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[19]//a")).click();
						return true;
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[21]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "BAP-GSS: Defining Business Needs and Solution Scope":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[23]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[24]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[21]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[20]//a")).click();
						return true;
					} else if (country.contentEquals("Spain") || country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[22]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

					// Specialized Offerings
				case "PMC-CVL: PMAppraise: A Knowledge and Skills Assessment":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[26]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[24]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[27]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[23]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[22]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {

						driver.findElement(By.xpath("//tr[25]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "BAP-DCW: BAAppraise: A Knowledge Appraisal":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[27]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[25]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[28]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[24]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[23]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {

						driver.findElement(By.xpath("//tr[26]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "PM360: A PM Competency Assessment":
					if (country.contentEquals("Sweden") || country.contentEquals("China") || country.contentEquals("UK")
							|| country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {

						driver.findElement(By.xpath("//tr[28]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[26]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[29]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[25]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[24]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {

						driver.findElement(By.xpath("//tr[27]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

					// Adaptive Strategic Execution Program
				case "ADX-GTW: Aligning Work with Strategy":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {

						driver.findElement(By.xpath("//tr[30]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[28]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[31]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[27]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[26]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "ADX-GTX: Influencing Without Authority":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {

						driver.findElement(By.xpath("//tr[31]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[29]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[32]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[28]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[27]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "ADX-GTZ: Making Sense of Complexity ":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {

						driver.findElement(By.xpath("//tr[32]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[30]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[33]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[29]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[28]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "ADX-GVC: Driving and Influencing Change":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {

						driver.findElement(By.xpath("//tr[33]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[31]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[34]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[30]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[29]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "ADX-GTY: Design Thinking for Results":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {

						driver.findElement(By.xpath("//tr[34]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[32]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[35]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[31]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[30]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "ADX-GVB: Building Effective Teams":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {

						driver.findElement(By.xpath("//tr[35]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[33]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[36]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[32]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[31]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

				case "ADX-GVF: Managing Critical Relationships":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {

						driver.findElement(By.xpath("//tr[36]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {

						driver.findElement(By.xpath("//tr[34]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {

						driver.findElement(By.xpath("//tr[37]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {

						driver.findElement(By.xpath("//tr[33]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {

						driver.findElement(By.xpath("//tr[32]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");

						return false;
					}

					// Lean and Agile
				case "AGX-GTB: Fundamentals of Lean and Agile":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {
						driver.findElement(By.xpath("//tr[38]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {
						driver.findElement(By.xpath("//tr[36]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {
						driver.findElement(By.xpath("//tr[39]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {
						driver.findElement(By.xpath("//tr[35]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[34]//a")).click();
						return true;
					} else if (country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[30]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {
						driver.findElement(By.xpath("//tr[29]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

				case "AGX-GWF: Continuous Improvement for with Lean and Kanban":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {
						driver.findElement(By.xpath("//tr[39]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {
						driver.findElement(By.xpath("//tr[37]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {
						driver.findElement(By.xpath("//tr[40]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {
						driver.findElement(By.xpath("//tr[36]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[35]//a")).click();
						return true;
					} else if (country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[31]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {
						driver.findElement(By.xpath("//tr[30]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

				case "AGX-GWB: Iterative Delivery with Scrum and Kanban":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {
						driver.findElement(By.xpath("//tr[40]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {
						driver.findElement(By.xpath("//tr[38]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {
						driver.findElement(By.xpath("//tr[41]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {
						driver.findElement(By.xpath("//tr[37]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[36]//a")).click();
						return true;
					} else if (country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[32]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {
						driver.findElement(By.xpath("//tr[31]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

				case "AGX-GWD: Lean and Agile Project Management":
					if (country.contentEquals("Sweden") || country.contentEquals("China")
							|| country.contentEquals("UK")) {
						driver.findElement(By.xpath("//tr[41]//a")).click();
						return true;
					} else if (country.contentEquals("Singapore")) {
						driver.findElement(By.xpath("//tr[39]//a")).click();
						return true;
					} else if (country.contentEquals("Germany")) {
						driver.findElement(By.xpath("//tr[42]//a")).click();
						return true;
					} else if (country.contentEquals("India")) {
						driver.findElement(By.xpath("//tr[38]//a")).click();
						return true;
					} else if (country.contentEquals("Australia")) {
						driver.findElement(By.xpath("//tr[37]//a")).click();
						return true;
					} else if (country.contentEquals("SouthAfrica") || country.contentEquals("Italy")) {
						driver.findElement(By.xpath("//tr[33]//a")).click();
						return true;
					} else if (country.contentEquals("Spain")) {
						driver.findElement(By.xpath("//tr[32]//a")).click();
						return true;
					} else {
						System.out.println("The Country Name is " + country + " which is not listed");
						return false;
					}

				default:
					System.out.println("Course is not correct");
					return false;
				}
			}
		}
		return false;
	}

	public boolean courseRegistration() {
		try {
			LOGGER.info("Student Registration Process through Reseller");

			FileInputStream fis = new FileInputStream(resellerData());
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("Reseller");
			int lastRow = sheet.getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {

				loginReseller(String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 0)));
				courseSelection(String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 0)),
						String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 1)));
				System.out.println("Searching for Policy Page");
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Discription"), 30);

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Discription"),
						driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PolicyAgree"), 30);

				driver.findElement(By.xpath("//input[@type='submit' and contains(@value,'Agree')]")).click();

				// UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME,
				// "PolicyAgree"), driver);

				// Existing Registration Process
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "email"), 30);
				if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 3)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "email"),
							String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 3)));
				if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 4)).length() > 1)
					UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "password"),
							String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 4)));

				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Continue"), driver);

				// New Registration Process
				if (isElementExists(SCREENNAME, "NewRecord")) {
					boolean result = isElementExists(SCREENNAME, "NewRecord");
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The New Registration is started", result);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewRecord"),
							driver);
					UIUtils.waitUntilElementExists(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateEmail"), 30);
					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 3)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateEmail"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 3)));
					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 3)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateConfirmEmail"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 3)));
					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 4)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatePassword"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 4)));
					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 4)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateConfirmPassword"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 4)));
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateRecord"),
							driver);

					// Filling the Student Information
					UIUtils.waitUntilElementExists(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentNameTitle"), 30);
					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 5)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentNameTitle"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 5)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 6)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentFirstName"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 6)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 7)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentMiddleInitial"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 7)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 8)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentLastName"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 8)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 9)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentSuffix"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 9)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 10)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentJobTitle"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 10)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 11)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentCompany"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 11)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 12)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentUsername"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 12)));
					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 12)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentReEnterUsername"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 12)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 13)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentStreet"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 13)));
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 14)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentCity"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 14)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 17)).length() > 1)
						UIUtils.selectValue(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentCounty"),
								"text=" + String
										.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 17)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 15)).length() > 1) {
						String country = String
								.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 17));
						if (country.contains("United States")) {
							UIUtils.selectValue(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentUSState"),
									"text=" + String
											.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 15)));
						} else {
							UIUtils.inputText(driver,
									TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentState"),
									String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 15)));
						}
					}

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 16)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentPostalCode"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 16)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 18)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentPhone"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 18)));

					if (String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 19)).length() > 1)
						UIUtils.inputText(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentLicenseNumber"),
								String.valueOf(ExcelUtils.getCellValue(IntegrationWorkbook, "Reseller", i, 19)));

					driver.findElement(By.xpath("//input[@type='submit' and contains(@value,'Continue')]")).click();
//			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentContinue"), driver);

					UIUtils.waitUntilElementExists(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CompleteRegistration"), 30);
					boolean completeResult = isElementExists(SCREENNAME, "CompleteRegistration");
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The New Registration is almost completed",
							completeResult);
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.clickElement(
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CompleteRegistration"),
							driver);
				}

				else if (isElementExists(SCREENNAME, "CompleteRegistration")) {
					boolean result = isElementExists(SCREENNAME, "CompleteRegistration");
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is already created", result);
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.scrollWindow(driver, "Down");
					UIUtils.clickElement(
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CompleteRegistration"),
							driver);
				} else if (isElementExists(SCREENNAME, "AlreadyRegistered")) {
					boolean result = isElementExists(SCREENNAME, "AlreadyRegistered");
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is already created", result);
					UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "LoginCourse"),
							driver);
				} else {
					System.out.println("Page is not loaded completely");
					boolean result = false;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not started or completed",
							result);
					return false;
				}
				if (i < lastRow) {
					TestDriver.appInstantiation(Apps.ReSellerIndia);
				} else
					return true;
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not started", result);
			return false;
		}
		boolean result = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not started", result);
		return false;
	}

	public boolean resellerCourseRegistration(String emailID, String userName, String country, String state, String licenceCode) {
		try {
			LOGGER.info("Student Registration Process through Reseller");

			System.out.println("Searching for Policy Page");
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Discription"), 30);

			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Discription"), driver);

			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "PolicyAgree"), 30);

			driver.findElement(By.xpath("//input[@type='submit' and contains(@value,'Agree')]")).click();

			// Existing Registration Process
			UIUtils.waitUntilElementExists(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "email"),
					30);

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "email"), emailID);

			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "password"), "india");

			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Continue"), driver);

			// New Registration Process
			if (isElementExists(SCREENNAME, "NewRecord")) {
				LOGGER.info("Creating new Student Registration");
				boolean result = isElementExists(SCREENNAME, "NewRecord");
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The New Registration is started", result);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "NewRecord"), driver);
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateEmail"), 30);

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateEmail"),
						emailID);

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateConfirmEmail"), emailID);

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreatePassword"),
						"india");

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateConfirmPassword"), "india");
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CreateRecord"),
						driver);

				// Filling the Student Information
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentNameTitle"), 30);

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentNameTitle"), "Dr");

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentFirstName"), "Test");

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentMiddleInitial"), "T");

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentLastName"), "Test");

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentSuffix"),
						"Jr.");

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentJobTitle"), "Testing");

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentCompany"),
						"Testing");

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentUsername"), userName);

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentReEnterUsername"), userName);

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentStreet"),
						"Delhi");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentCity"),
						"Delhi");

				UIUtils.selectValue(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentCounty"),
						"text=" + country);

				if (country.contains("United States")) {
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentUSState"),
							"text=" + state);
				} else {
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentState"), state);
				}

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentPostalCode"), "101010");

				UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentPhone"),
						"987654321");

				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentLicenseNumber"), licenceCode);

				driver.findElement(By.xpath("//input[@type='submit' and contains(@value,'Continue')]")).click();
//			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "StudentContinue"), driver);

				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CompleteRegistration"), 30);
				boolean completeResult = isElementExists(SCREENNAME, "CompleteRegistration");
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The New Registration is almost completed",
						completeResult);
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.clickElement(
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CompleteRegistration"), driver);
				return true;
			}

			else if (isElementExists(SCREENNAME, "CompleteRegistration")) {
				boolean result = isElementExists(SCREENNAME, "CompleteRegistration");
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is already created", result);
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.scrollWindow(driver, "Down");
				UIUtils.clickElement(
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "CompleteRegistration"), driver);
				return true;
			} else if (isElementExists(SCREENNAME, "AlreadyRegistered")) {
				boolean result = isElementExists(SCREENNAME, "AlreadyRegistered");
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is already created", result);
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "LoginCourse"),
						driver);
				return true;
			} else {
				System.out.println("Page is not loaded completely");
				boolean result = false;
				UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not started or completed",
						result);
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			boolean result = false;
			UIUtils.attachScreenShot(ExtentManager.extentTest, "The Registration is not started", result);
			return false;
		}
	}

}
