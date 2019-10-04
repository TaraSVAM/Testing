package com.svam.twentyeighty.tests;

import java.util.Arrays;

import org.testng.annotations.Test;

public class Support {
	/* function to check whether two strings are  

    anagram of each other */

    public static boolean areAnagram(String str1, String str2) {   	
    	try {
    		
    		if (str1==null || str2==null) {
    			 throw new NullPointerException("The argument cannot be null"); 
    		}
    		
    			int m1 = str1.length();
            	int m2 = str2.length();

            // If length of both strings is not same, 

            // then they cannot be anagram 
            if (m1 != m2) 
                return false;
            // For case sensitivity issue we can turn string to lower case or upper case
            str1 = str1.toLowerCase();
            str2 = str2.toLowerCase();

            char [] charArrayFromString1 = str1.toCharArray();
        	char [] charArrayFromString2 = str2.toCharArray();

            // I am Sorting both strings 
            Arrays.sort(charArrayFromString1);
            Arrays.sort(charArrayFromString2); 

            // I am Compare sorted strings 
            for (int i = 0; i < m1; i++) 
                if (charArrayFromString1[i] != charArrayFromString2[i]) 
                    return false; 
            return true; 
			
		} catch (NullPointerException e) {
			System.out.println("The String Value is Null. NullPointerException Caught");
		}
		return false;
    } 

 @org.testng.annotations.DataProvider(name="anagramData")
 public Object[][] anagramTest() {
	 return new Object[][] {
		 {"army","mary"},
		 {"ARMY","MARY"},
		 					{"god","dog"},
		 					{null,"army"},
		 					{"army",null},
		 					{"army1","mary"},
		 					{"a rmy", "m ary"},
		 					{"army","m ary"},
		 					{"","mary"}
		 				};		 

 }
 
 @Test(testName="testAnagram", dataProvider = "anagramData")
 public void testAnagram(String first, String second) {
	 if(areAnagram(first,second))
		 System.out.println("The first "+ first+" and Second "+second+" strings are anagram of each other"); 
     else
         System.out.println("The first "+ first+" and Second "+second+" strings are not anagram of each other");   	
 }
}
