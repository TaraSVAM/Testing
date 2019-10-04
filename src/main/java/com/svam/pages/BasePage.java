package com.svam.pages;

public interface BasePage {
	public boolean isPageOpen();

	public boolean validateUI();

	public boolean isElementExists(String screenName, String elementKey);

	public boolean isElementEnabled(String screenName, String elementKey);
}