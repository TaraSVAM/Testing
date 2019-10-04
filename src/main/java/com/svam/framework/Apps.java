package com.svam.framework;


public enum Apps {
	
	
	// Windows in Unifier9x/10x
	
	//Unifier_Login_Window
	PublicWebsite("PublicWebsite"),
    SMIL("SMIL"),
	ReSellerAustralia("ReSellerAustralia"),
	ReSellerGermany("ReSellerGermany"),
	ReSellerSweden("ReSellerSweden"),
	ReSellerSingapore("ReSellerSingapore"),
	ReSellerChina("ReSellerChina"),
	ReSellerIndia("ReSellerIndia"),
	ReSellerUK("ReSellerUK"),
	ReSellerSouthAfrica("ReSellerSouthAfrica"),
	ReSellerItaly("ReSellerItaly"),
	ReSellerSpain("ReSellerSpain"),
	MyESI("MyESI"),
	Intacct("Intacct");
		
	private String m_appName;

	Apps(String appName) {
		m_appName = appName;
	}
	
	public String getAppName() {
		return m_appName;
	}
}
