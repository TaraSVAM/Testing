package com.svam.utils;

public enum BrowserTypes {
	CHROME, IE, FIREFOX, SAFARI, HTMLUNIT;
	String m_browser;

	BrowserTypes() {
		m_browser = toString();
	}

	BrowserTypes(String browser) {
		m_browser = browser;
	}

	public String getBrowser() {
		return m_browser;
	}

	public static BrowserTypes fromString(String browser) {
		if (browser == null) {
			return null;
		}
		for (BrowserTypes browserName : BrowserTypes.values()) {
			if (browser.equalsIgnoreCase(browserName.getBrowser())) {
				return browserName;
			}
		}
		return null;
	}
}