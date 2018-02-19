package com.ark;

 public class SearchOption{
	String searchType;
	String opCompare;
	String content;
	String op;
	public SearchOption(String searchType, String opCompare, String content, String op){
		this.searchType = searchType;
		this.opCompare = opCompare;
		this.content = content;
		this.op = op;
	}
	public boolean checkContent(){
		return checkContent(content);
	}
	
	static public boolean checkContent(String con){
		if(con==null)
			return false;
		if(con.isEmpty())
			return false;
		return true;
	}
	
	public String getWhereStatement(){
		String whereStatement = "";
		if(checkContent()){
			content.replace("'", "''");
			whereStatement = " " + searchType + 
					" " +opCompare + 
					" '"+content+"' ";
		}
		return whereStatement;
	}
	
	public static String generateWhereStatments(SearchOption options[]){
		String whereStatements = options[0].getWhereStatement();
		String op = options[0].op;
		for(int i = 1; i < options.length; i++){
			if(options[i].checkContent()){
				if(SearchOption.checkContent(whereStatements)){
					whereStatements = whereStatements + op 
							+ options[i].getWhereStatement();
					op = options[i].op;
				}else{
					whereStatements = options[i].getWhereStatement();
					op = options[i].op;
				}
			}
		}
		return whereStatements;
	}
}