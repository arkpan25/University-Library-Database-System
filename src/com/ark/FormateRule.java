package com.ark;
import java.util.regex.Pattern; 
import java.util.regex.Matcher; 
public class FormateRule {
	private static String regExSSN = "\\d{3}-\\d{2}-\\d{4}";
	private static String regExCardNo = "id\\d{6}";
	public static void main(String[] args) {
		String ssn = "2222222";
		System.out.println(CheckSsn(ssn));
		System.out.println(CheckSsn("256-95-4382"));
		System.out.println(CheckSsn("256-95-43824"));

	}
	
	public static boolean CheckSsn(String ssn){
		 Pattern pt = Pattern.compile(regExSSN); 
		 Matcher mt = pt.matcher(ssn);
		 boolean result = mt.matches(); 	        
		 return result;
	}
	
	public static boolean CheckCardNo(String cardNo){
		 cardNo = cardNo.toLowerCase();
		 Pattern pt = Pattern.compile(regExCardNo); 
		 Matcher mt = pt.matcher(cardNo);
		 boolean result = mt.matches(); 	        
		 return result;
	}

}
