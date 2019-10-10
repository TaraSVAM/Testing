package com.svam.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.svam.driver.TestDriver;
import com.svam.utils.Config;
import com.svam.utils.ExcelUtils;
import com.svam.utils.ExtentManager;
import com.svam.utils.UIUtils;

public class SMILCourses extends AbstractPage {
	private final String HOMETITLE = "SMIL-Home Page";
	private static final String SCREENNAME = "SMIL_Courses";
	public static String SMILWorkbook;
	Config config = new Config("Framework\\Test_Config\\config.properties");

	public SMILCourses(WebDriver driver) {
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
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourses"), 10);

		return isElementExists(SCREENNAME, "createCourses") && isElementExists("SMIL_Common", "Courses");
	}

	public static String courcesData() throws IOException {
		return SMILWorkbook = new File("Framework\\Test_Data\\StratexData.xls").getCanonicalPath();
	}

	public static String courcesCDSData() throws IOException {
		return SMILWorkbook = new File("Framework\\Test_Data\\CDSMapping.xls").getCanonicalPath();
	}

	public void clickOnCourses() throws Exception {
		LOGGER.info("Clicking on Courses Tab.");
		UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "Courses"), driver);
	}

	@SuppressWarnings("resource")
	public boolean courseProcess(String action) {
		try {
			FileInputStream fis = new FileInputStream(courcesData());
			Workbook workbook = new HSSFWorkbook(fis);
			if (action.toUpperCase().contentEquals("SEARCH")) {
				String type = (String) String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseSearch", 1, 0));
				if (action.toUpperCase().contentEquals("SEARCH")) {
					String CourseCode = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseSearch", 1, 1));
					basicSerach(CourseCode);
					return true;
				}
				if (type.toUpperCase().contentEquals("ADVANCE")) {
					String CourseCode = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseSearch", 1, 1));
					String CourseTitle = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseSearch", 1, 2));
					String status = String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseSearch", 1, 3));
					advancesearch(CourseCode, CourseTitle, status);
					return true;
				}
			} else if (action.toUpperCase().contentEquals("CREATE")) {
				createCourse();
				return true;
			} else {
				System.out.println("The action command should be Search or Create");
				return false;
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean basicSerach(String courseCode) {
		LOGGER.info("Searching by Course Code");
		try {
			UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "searchCourseCode"),
					courseCode);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "basicSearchButton"),
					driver);
			List<WebElement> resultCourseCode = driver
					.findElements(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "resultCourse"));
			for (WebElement webElement : resultCourseCode) {
				if (webElement.getText().contains(courseCode)) {
					ExtentManager.extentTest.log(LogStatus.INFO, "The Course detail");
					boolean serachCourseResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is serached", serachCourseResult);
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean serachCourseResult = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is serached", serachCourseResult);
		return false;
	}

	public boolean advancesearch(String CourseCode, String CourseTitle, String status) {

		try {
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceSearch"), driver);

			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceCourseCode"), 10);
			if (CourseCode.length() > 1) {
				LOGGER.info("Filling Course Code");
				this.searchCourseCode(CourseCode);
			}
			if (CourseTitle.length() > 1) {
				LOGGER.info("Filling Course Title");
				this.searchCourseTitle(CourseTitle);
			}

			if (status.length() > 1) {
				LOGGER.info("Filling Course Title");
				this.searchCourseStatus(status);
			}

			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceSearchButton"),
					driver);
			UIUtils.scrollWindow(driver, "down");
			UIUtils.scrollWindow(driver, "down");
			List<WebElement> results = driver
					.findElements(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "resultCourse"));
			for (WebElement webElement : results) {
				if (webElement.getText().contains(CourseCode)) {
					ExtentManager.extentTest.log(LogStatus.INFO, "The Course detail");
					boolean serachCourseResult = true;
					UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is serached", serachCourseResult);
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean serachCourseResult = false;
		UIUtils.attachScreenShot(ExtentManager.extentTest, "The Course is serached", serachCourseResult);
		return false;
	}

	/*------- Searching the filled data -------*/
	public void searchCourseCode(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceCourseCode"),
				data);
	}

	public void searchCourseTitle(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceCourseTitle"),
				data);
	}

	public void searchCourseStatus(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceStatus"),
				FinalData);
	}

	/*---------- Filling data in Courses data---------*/
	public boolean createCourse() throws Exception {
		try {
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourses"), 10);
			UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourses"), driver);
			FileInputStream fis = new FileInputStream(courcesData());
			Workbook workbook = new HSSFWorkbook(fis);
			Sheet sheet = workbook.getSheet("CourseData");
			int lastRow = sheet.getLastRowNum();
			for (int i = 1; i <= lastRow; i++) {

				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 0)).length() > 1) {
					LOGGER.info("Filling Course Code");
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseCode"),
							String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 0)));
					// this.fillCourseCode(code);
				}

				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "Data", i, 1)).length() > 1) {
					LOGGER.info("Filling Course Title");
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseTitle"),
							String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 1)));
				}

				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 2)).length() > 1) {
					LOGGER.info("Filling Course Provider");
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseProvider"),
							"text=" + String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 2)));
				}

				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 3)).length() > 1) {
					LOGGER.info("Filling Course CDS");
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseCDS"),
							"text=" + String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 3)));
				}

				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 4)).length() > 0) {
					LOGGER.info("Filling Course Access Days");
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseAccessDays"),
							String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 4)));
				}
				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseDataData", i, 5)).length() > 0) {
					LOGGER.info("Filling Course Extension Days");
					UIUtils.inputText(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseExtensionDays"),
							String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 5)));
				}
				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 6)).length() > 1) {
					LOGGER.info("Filling Course Online Email1");
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseOnlineEmail1"),
							"text=" + String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 6)));
				}
				if (String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 7)).length() > 1) {
					LOGGER.info("Filling Course Online Email2");
					UIUtils.selectValue(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseOnlineEmail2"),
							"text=" + String.valueOf(ExcelUtils.getCellValue(SMILWorkbook, "CourseData", i, 7)));
				}
				UIUtils.scrollWindow(driver, "down");
				UIUtils.scrollWindow(driver, "down");
				String CoursePath = config.getPropertyValue("IMAGE");
				UIUtils.inputText(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createCourseImage"), CoursePath);
				// driver.findElement(By.xpath("//input[@id='Image']")).sendKeys("D:\\Personal\\tara.png");
				UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "createButton"),
						driver);
			}
			workbook.close();
			return true;

		} catch (Exception e) {
			LOGGER.error("Error on saving data" + e.toString());
			e.printStackTrace();
			return false;
		}

	}

	// ------------Saving the Course data-------------
	public void fillCourseCode(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseCode"), data);
	}

	public void fillCourseTitle(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseTitle"), data);
	}

	public void fillCourseProvider(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseProvider"),
				FinalData);
	}

	public void fillCourseCDS(String data) throws Exception {
		String FinalData = "text=" + data;
		UIUtils.selectValue(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseCDS"),
				FinalData);
	}

	public void fillCourseAccessDays(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseAccessDays"),
				data);
	}

	public void fillCourseExtensionDays(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseExtensionDays"),
				data);
	}

	public void fillCourseOnlineEmail1(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseOnlineEmail1"),
				data);
	}

	public void fillCourseOnlineEmail2(String data) throws Exception {
		UIUtils.inputText(driver, TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "courseOnlineEmail2"),
				data);
	}

	public void advanceSerachForAllCourses() throws Exception {
		LOGGER.info("Advance serach for all Courses----");
		UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceSearch"), driver);
		UIUtils.waitUntilElementExists(driver,
				TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceCourseCode"), 10);

		UIUtils.clickElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "advanceSearchButton"),
				driver);
		// return true;
	}

	public void clickEditforAllSerachResult() {
		LOGGER.info("Clicking the Serach Result----");
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			UIUtils.waitUntilElementExists(driver,
					TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "searchResult_Edit"), 50);

			List<WebElement> result = driver
					.findElements(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "searchResult_Edit"));
			FileInputStream fis = new FileInputStream(courcesCDSData());
			Workbook workbook = new HSSFWorkbook(fis);
			for (int i = 1; i <= result.size(); i++) {
				LOGGER.info("The Course number " + i + " is executing----");
				driver.findElement(By.xpath("(//tbody[@id='resultsRows']//td//a[2])[" + i + "]")).click();
				UIUtils.waitUntilElementExists(driver,
						TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "editPage"), 50);
				WebElement CourseCode = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_CourseCode"));
				WebElement Title = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_Title"));
				WebElement Modality = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_Modality"));
				WebElement Provider = driver
						.findElement(TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_Provider"));

				String providerName = Provider.getText();
				switch (providerName) {
				case "Moodle Sandbox":
					LOGGER.info("----Provider is Moodle Sandbox-------");

					String value = CourseCode.getText();
					map.put("CourseCode_" + i, value);

					value = Title.getText();
					map.put("Title_" + i, value);

					value = Modality.getText();
					map.put("Modality_" + i, value);

					value = Provider.getText();
					map.put("Provider_" + i, value);

					if (UIUtils.isObjectExist(driver,
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_CDSCourse_Selected"))) {

						WebElement CDSCourse = driver.findElement(
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_CDSCourse_Selected"));
						value = CDSCourse.getText();
						map.put("CDSCourse_Previous_" + i, value);

						String.valueOf(
								ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 0, map.get("CourseCode_" + i)));
						String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 1, map.get("Title_" + i)));
						String.valueOf(
								ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 2, map.get("Modality_" + i)));
						String.valueOf(
								ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 3, map.get("Provider_" + i)));
						String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 4,
								map.get("CDSCourse_Previous_" + i)));
						String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 5,
								map.get("CDSCourse_Previous_" + i)));

						UIUtils.clickElement(
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_BackToSearchButton"),
								driver);
						break;

					} else {

						map.put("CDSCourse_Previous_" + i, "Please Select");
						UIUtils.selectValue(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_CDSCourse"),
								"index=2");

						UIUtils.clickElement(
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_SaveButton"), driver);

						Thread.sleep(2000);

						UIUtils.clickElement(
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_CancelButton"),
								driver);

						UIUtils.waitUntilElementExists(driver,
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_SaveButton"), 50);

						WebElement CDSCourse = driver.findElement(
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_CDSCourse_Selected"));

						value = CDSCourse.getText();
						map.put("CDSCourse_New_" + i, value);

						String.valueOf(
								ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 0, map.get("CourseCode_" + i)));
						String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 1, map.get("Title_" + i)));
						String.valueOf(
								ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 2, map.get("Modality_" + i)));
						String.valueOf(
								ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 3, map.get("Provider_" + i)));
						String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 4,
								map.get("CDSCourse_Previous_" + i)));
						String.valueOf(
								ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 5, map.get("CDSCourse_New_" + i)));

						UIUtils.clickElement(
								TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_BackToSearchButton"),
								driver);
						break;
					}

				case "Data Solutions":
					LOGGER.info("----Provider is Data Solutions-------");
					value = CourseCode.getText();
					map.put("CourseCode_" + i, value);

					value = Title.getText();
					map.put("Title_" + i, value);

					value = Modality.getText();
					map.put("Modality_" + i, value);

					value = Provider.getText();
					map.put("Provider_" + i, value);

					map.put("CDSCourse_Previous_" + i, "Not Required");

					UIUtils.clickElement(
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_BackToSearchButton"),
							driver);
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 0, map.get("CourseCode_" + i)));
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 1, map.get("Title_" + i)));
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 2, map.get("Modality_" + i)));
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 3, map.get("Provider_" + i)));
					String.valueOf(
							ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 4, map.get("CDSCourse_Previous_" + i)));
					String.valueOf(
							ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 5, map.get("CDSCourse_Previous_" + i)));

					break;

				case "Moodle":
					LOGGER.info("----Provider is Moodle-------");
					value = CourseCode.getText();
					map.put("CourseCode_" + i, value);

					value = Title.getText();
					map.put("Title_" + i, value);

					value = Modality.getText();
					map.put("Modality_" + i, value);

					value = Provider.getText();
					map.put("Provider_" + i, value);

					map.put("CDSCourse_Previous_" + i, "Please Select a CDS Course");
					UIUtils.clickElement(
							TestDriver.getInstance().getObjRep().getLocator(SCREENNAME, "edit_BackToSearchButton"),
							driver);
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 0, map.get("CourseCode_" + i)));
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 1, map.get("Title_" + i)));
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 2, map.get("Modality_" + i)));
					String.valueOf(ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 3, map.get("Provider_" + i)));
					String.valueOf(
							ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 4, map.get("CDSCourse_Previous_" + i)));
					String.valueOf(
							ExcelUtils.setCellValue(SMILWorkbook, "Courses", i, 5, map.get("CDSCourse_Previous_" + i)));
					break;

				default:
					LOGGER.info("There is some issue with Provider Name---" + providerName);
					break;
				}
			}
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
