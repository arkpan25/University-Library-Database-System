package com.leo;

import java.util.Vector;

public class BookCheckIn {		
	private Vector<String> project = null;
	public BookCheckIn(){
		project = new Vector<String>();
		project.add("loan_id");
		project.add("card_no");
		project.add("fname");
		project.add("lname");		
		project.add("isbn");
		project.add("date_out");
		project.add("due_date");
		project.add("date_in");		
	}
	
	public static void main(String[] args) {
				
	}
	// assumption input is lower-case
	// could return null 
	public Vector<Vector<String>> searchBookLoans(SearchOption options[]){
		String whereStatement = SearchOption.generateWhereStatments(options);
		if(!SearchOption.checkContent(whereStatement))
			return null;					
		String sql = SqlGenerator.searchBookLoansCmd(project,whereStatement);
		DataBaseController db = new DataBaseController();		
		return db.executQuery(sql, project);			 
	}
	
	public void updateBookLoans(String loanId){
		String sql = SqlGenerator.updateBookLoansCmd(loanId);
		DataBaseController db = new DataBaseController();		
		db.execut(sql);	
	}

}
