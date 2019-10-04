package com.svam.utils;

import org.apache.log4j.Logger;

public class RandomNumber {
	static Logger LOGGER = Logger.getLogger(RandomNumber.class);
	public static long getRandomNumber(int size) {
		String tmp ="9";
		for(int i=0;i<size-1;i++) {
			tmp=tmp+"0";
		}
		Long number = Long.valueOf(tmp);
		return (long)(Math.random()*(int)Math.pow(10, size-1)+number);
	}
}
