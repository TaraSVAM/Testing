package com.svam.driver;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.collections.Lists;

import com.svam.framework.TestReport;

public class TestRunner {
	static Logger LOGGER = Logger.getLogger(TestRunner.class);
	
	public static void main(String[] arg) {
		long startTime = System.currentTimeMillis();
		TestNG testNG = new TestNG();

		List<String> suites = Lists.newArrayList();
		suites.add("testng.xml");
		testNG.setTestSuites(suites);

		testNG.run();
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		TestReport.getInstance().duration = String.valueOf(duration / 60000) + " minutes, "
				+ String.valueOf((duration / 1000) % 60) + "Seconds";
		
		try {
			// TODO
			TestReport.getInstance().createEmailableReport(15, 10, 5);
		} catch (IOException e) {
			LOGGER.error("IOException occured while creating emailable report", e);
		}
		
		System.exit(0);
	}
}