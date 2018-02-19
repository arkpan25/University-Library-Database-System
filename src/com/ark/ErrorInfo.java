package com.ark;

public class ErrorInfo {
	
	protected String errorInf = "";//record errorInf
	ErrorInfo(){
		errorInf = "";
	}
	
	public String getErrorInf(){
		return errorInf;
	}
	
	protected void clearErrorInf(){
		errorInf = "";
	}
}
