package com.svam.twentyeighty.tests;

import org.testng.TestNG;

public class TwentyEightyRunner {
	static TestNG testNG;
	public static void main(String[] args) {
		testNG = new TestNG();
		testNG.setTestClasses(new Class[] {com.svam.driver.TestDriver.class});
		testNG.setTestClasses(new Class[] {ResellerToSMIL.class});
		testNG.run();
	}

}
