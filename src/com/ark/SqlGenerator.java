package com.ark;

import java.util.Vector;

public class SqlGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	public static String CreateInstValueBorrowCmd(
			String card_no,
			String ssn,
			String fname,
			String lname,
			String address,
			String phone
			){		
		if(phone==null) phone = "null";
		else if(phone.isEmpty()) phone = "null";
		else phone = "'"+ phone +"'";
		
		String sql = "INSERT INTO BORROWER VALUES("+
				"'"+ card_no +"'," +
				"'"+ ssn +"'," +
				"'"+ fname +"'," +
				"'"+ lname +"'," +
				"'"+ address +"'," +
				phone +");";	
		return sql;		
	}
	
	public static String CreateInstValueBookCmd(String isbn, String title){
		String sql = "INSERT INTO BOOK VALUES("+
					"'"+ isbn +"'," +
					"'"+ title +"');";	
		return sql;
	}
	
	public static String CreateInstValueBookAuthorCmd(String isbn, String authors){
		String sql = "INSERT INTO BOOK_AUTHORS VALUES("+
					"'"+ isbn +"'," +
					"'"+ authors +"');";	
		return sql;
	}
	
	public static String CreateInstValueLibraryBranchCmd(String branchId, String branchName, String address){
		String sql = "INSERT INTO LIBRARY_BRANCH VALUES("+
					"'"+ branchId +"'," +
					"'"+ branchName +"'," +
					"'"+ address +"');";	
		return sql;
	}
	
	public static String CreateInstValueBookCopiesCmd(String bookId, String branchId, String numCopies){
		String sql = "INSERT INTO BOOK_COPIES VALUES("+
					"'"+ bookId +"'," +
					"'"+ branchId +"'," +
					"'"+ numCopies +"');";	
		return sql;
	}	
	
	//INSERT INTO BOOK_LOANS VALUES('2','0195153448','1','1',
	//current_date(),date_add(current_date(), interval 14 day), NULL);""
	public static String CreateInstValueBookLoansCmd(
			String loanId, String isbn, 
			String branchId, String cardNo, 
			String dateOut, String dueDate, String dateIn
			){
		String sql = "INSERT INTO BOOK_LOANS VALUES("+
					"'"+ loanId +"'," +
					"'"+ isbn +"'," +
					"'"+ branchId +"'," +
					"'"+ cardNo +"'," +
					"'"+ dateOut+"'," +
					"'"+ dueDate+"'," +
					"'"+ dateIn+ "' );";	
		return sql;
	}
	
	public static String CreateInstValueBookLoansCmd(
			String loanId, String isbn, 
			String branchId, String cardNo, 
			String dateOut, String dueDate
			){
		String sql = "INSERT INTO BOOK_LOANS VALUES("+
					"'"+ loanId +"'," +
					"'"+ isbn +"'," +
					"'"+ branchId +"'," +
					"'"+ cardNo +"'," +
					"'"+ dateOut+"'," +
					"'"+ dueDate+"'," +
					" NULL);";	
		return sql;
	}
	
	public static String CreateInstValueBookLoansCmd(
			String loanId, String isbn, 
			String branchId, String cardNo			
			){
		//current_date(),date_add(current_date(), interval 14 day), NULL);
		String sql = "INSERT INTO BOOK_LOANS VALUES("+
					"'"+ loanId +"'," +
					"'"+ isbn +"'," +
					"'"+ branchId +"'," +
					"'"+ cardNo +"'," +
					"current_date(),date_add(current_date(), interval 14 day), NULL);";
		return sql;
	}
	
	public static String searchBookCmd(Vector<String> project, String whereStatement){
		//String projects = combineProjectStatment(project);
		//if(projects == null) return null;
		String sql1 = "select distinct bd.isbn,bd.title, "
				+ " bd.author_name,bd.branch_id,bd.branch_name,"
				+ " bd.no_of_copies, checkout, no_of_copies - checkout as available "
				+ " from (((select isbn, title, author_name,branch_id,branch_name,no_of_copies "
				+ " from ((book natural join book_authors)  natural join "
				+ " ( select book_id as isbn, branch_id, no_of_copies from book_copies) as bc)"
				+ " natural join library_branch) as bd )left join "
				+ "((select isbn, branch_id, count(*) as checkout "
				+ " from book_loans "
				+ " where date_in is null group by isbn, branch_id) as be) "
				+ " on bd.isbn = be.isbn and bd.branch_id = be.branch_id)";
		String sql = sql1 + " where " + whereStatement +";";
		return sql;
	}
	
	public static String searchBookLoansCmd(Vector<String> project, String whereStatement){
		String projects = combineProjectStatment(project);
		if(projects == null) return null;
		String sql ="select "+ projects +
					" from book_loans natural join borrower " + 
	    		    " where " + whereStatement +";";
		return sql;
	}
	
	public static String updateBookLoansCmd(String loanId){
		String sql = "UPDATE book_loans set date_in = current_date() "
				+ " where loan_id = "
				+ "'" + loanId+ "'"
				+ " and date_in is null;";
		return sql;
	}
	
	public static String updatePayFineCmd(String cardNo){
		String sql = "update book_loans as bl natural join  Fines as f " 
		  + " set f.paid = 1 "
		  + " where f.loan_id = bl.loan_id "
		  + " and bl.date_in is not null "
		  + " and f.paid = 0 "
		  + "and bl.card_no = "
		  + "'" + cardNo + "'; ";
		return sql;
	}
	
	public static String getMaxCardNoCmd(String project){
		String sql = "select max(card_no) as "
				+ project 
				+ " from borrower;";
		return sql;
	}	
	
	public static String getCountBookLoansByCardNoCmd(String project, String cardNo){
		String sql = "select count(*) as "
				+ project
				+ " from book_loans "
				+ " where card_no = '"+cardNo+"' "
				+ " and date_in is null ;";
		return sql;
	}
	
	public static String getMaxLoanNoCmd(String project){
		String sql = "select max(loan_id) as "
				+ project
				+ " from BOOK_LOANS;";
		return sql;
	}
	//get use no_of_copies checkout available
	public static String CreateCheckBookCopysAvailalbeCmd(String bookId, String branchId){
		String sql = "select no_of_copies, checkout, "
				+ "no_of_copies - checkout as available "
				+ "from (book_copies as bc left join "
				+ "((select isbn as book_id, branch_id, count(*) as checkout "
				+ "from book_loans "
				+ "where date_in is null group by isbn, branch_id) as ibc) "
				+ "on ibc.book_id = bc.book_id "
				+ "and ibc.branch_id = bc.branch_id) "
				+ "where bc.book_id = "
				+ " '"+bookId+"' "
				+" and bc.branch_id ="
				+ "'" + branchId + "'";
		return sql;		
	}	

	public static String CreateCheckExistIsbnCmd(String project, String isbn){
		String sql = "select count(*) as "
				+ project 
				+ " from book "
				+ " where isbn = "
				+ "'" +isbn+"';";
		return sql;
	}	
	
	public static String CreateCheckExistCardNoCmd(String project, String cardNo){
		String sql = "select count(*) as "
				+ project 
				+ " from borrower "
				+ " where card_no = "
				+ "'" +cardNo+"';";
		return sql;
	}
	
	public static String CreateCheckExistSsnCmd(String project, String ssn){
		String sql = "select count(*) as "
				+ project 
				+ " from borrower "
				+ "where ssn = "
				+ "'" +ssn+"';";
		return sql;
	}
	
	public static String CreatecheckAllowPayFineCmd(String project, String cardNo){
		//#'check allow payment fine'

		String sql = " select count(*) as " 
				+ project 
				+ " from fines natural join book_loans "
				+ " where paid = 0 "
				+ " and date_in is null "
				+ " and card_no = " 
				+ "'" +cardNo +"'"
				+ " group by card_no;";
		return sql;
	}
	
	public static String CreateCheckExistBranchIdCmd(String project, String branchId){
		String sql = "select count(*) as "
				+ project 
				+ " from library_branch "
				+ "where branch_id = "
				+ "'" +branchId+"';";
		return sql;
	}
	
	public static String CreateUpdateFineBookStillOutCmd(){
		//fine for book still out, fine have already existed, due date past'		  
		String sql = "update book_loans as bl, Fines as f set f.fine_amt = DATEDIFF(current_date, bl.due_date) *0.25 "+
		 " where f.loan_id = bl.loan_id and bl.date_in is null and f.paid = 0 "+
		 " and f.fine_amt <> DATEDIFF(current_date, bl.due_date) " +
		 " and DATEDIFF(current_date, bl.due_date) > 0 ;";
		return sql;
	}
	
	public static String CreateInsertFineBookStillOutCmd(){
		//'fine for book still out, fine have not  existed, due date past'
		String sql = " Insert into fines  select  bl.loan_id, DATEDIFF(current_date, bl.due_date) *0.25, 0 "+
           " from  book_loans as bl left join Fines as f on bl.loan_id = f.loan_id "+
           " where f.loan_id is null and bl.date_in is null and DATEDIFF(current_date, bl.due_date) > 0 ; ";
		return sql;
	}
	
	
	public static String CreateUpdateFineBookReturnedCmd(){	
		 //#'fine for book returned, fine have already existed, due date past'
		String sql = " update book_loans as bl, Fines as f set f.fine_amt = DATEDIFF(bl.date_in, bl.due_date) *0.25 "+
		 " where f.loan_id = bl.loan_id  and bl.date_in is not null and f.paid = 0 "+
		 " and f.fine_amt <> DATEDIFF(bl.date_in, bl.due_date) *0.25 and DATEDIFF(bl.date_in, bl.due_date) > 0 ;"; 
		return sql;
	}
	
	public static String CreateInsertFineBookReturnedCmd(){	
		  //#'fine for book return, fine have not  existed, due date past'
		 String sql = "Insert into fines(loan_id, fine_amt, paid) " + 
		 " select  bl.loan_id, DATEDIFF(bl.date_in, bl.due_date) *0.25, 0 " +
		 " from  book_loans as bl left join Fines as f on bl.loan_id = f.loan_id " +
		 " where  f.loan_id is null and bl.date_in is not null and DATEDIFF(bl.date_in, bl.due_date) > 0 ; ";
		 return sql;
	}	
	
	public static String CreateDisplayAllFinesGroupbyCardNo(boolean containPrevios,String cardNo, String project, String whereStatement){
		 //#'display all fines by card_no, filter out previously paid fines'
		String paidWhereStatement = null;
		
		if(containPrevios == true) paidWhereStatement = " ";
		else paidWhereStatement = "paid = 0 and ";
		
		 String sql = " select card_no as "
		 		+ cardNo +
		 		", sum(fine_amt) as "
		 		+ project +
		 " from fines natural join book_loans " +
		 " where " + paidWhereStatement
		  + whereStatement + 
		 " group by card_no; ";
		 return sql;
	}
	
	public static String combineProjectStatment(Vector<String> project){
		if(project.size() < 1) return null;
		if(project.size() == 1) return project.get(0);
		String projects = "";
		for(int i = 0; i < project.size()-1; i++){
			projects = projects + project.get(i) + ",";
		}
		projects += project.get(project.size()-1);
		return projects;
	}
	
	//getSingleResultFromDB sql should be like
	//select max(loan_id) as result from BOOK_LOANS;
	public static String getSingleResultFromDB(String oneProject, String sql){				
		Vector<String> project = new Vector<String>();
		project.add(oneProject);	
		DataBaseController db = new DataBaseController();
		Vector<Vector<String>> lines = db.executQuery(sql, project);
		boolean bResult = InputCheck.VectorVectorStringNotEmpty(lines);
		if(bResult == false) return null;
		String result = lines.get(0).get(0);		
		return result;		
	}
	

}
