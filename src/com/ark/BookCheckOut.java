package com.leo;

import java.util.Vector;

public class BookCheckOut extends ErrorInfo{

	static int MaximumBookLoans = 3;
	private String isbn;
	private String branchId; 
	private String cardNo;
	private String loanId;
	
	public static void main(String[] args) {

		test();
	}
	
	private static void test(){
		BookCheckOut bco = new BookCheckOut();
		String isbn = "0195153448";
		String branchId = "2";
		String cardNo = "10";
		boolean result;
		for(int i = 0; i < 1; i++){
			result = bco.checkOutBook(isbn, branchId, cardNo);			
			System.out.println(result);		
			if(result == false){
				System.out.println(bco.getErrorInf());
			}
		}	
	}
	
	public String getLoanId(){
		return loanId;
	}
	
	public boolean checkInputAvaliable(){	
		InputCheck.clearErrorInf();
		if(!InputCheck.checkExistIsbn(isbn)
				||!InputCheck.checkExistBranchId(branchId)
				||!InputCheck.checkExistCardNo(cardNo)
				){
			errorInf = InputCheck.getErrorInf();
			return false;
		}	
		return true;
	}
	
	public boolean checkOutBook(String isbn, 
			String branchId, String cardNo){
		this.isbn = isbn;
		this.branchId = branchId;
		this.cardNo = cardNo;
		clearErrorInf();
		if(!checkInputAvaliable()){
			return false;
		}
		
		if(!checkMaximumBookLoans()){
			return false;
		}		
		if(!checkBookCopysAvailalbe()){
			return false;
		}
		loanId = generateNewLoadNo();
		String sql = SqlGenerator.CreateInstValueBookLoansCmd(loanId,isbn,branchId,cardNo);
		DataBaseController db = new DataBaseController();
		db.execut(sql);
		return true;
	}
	
	private String generateNewLoadNo(){
		String newLoadNo = "";
		String project = "__max";
		String sql = SqlGenerator.getMaxLoanNoCmd(project);
		String curMaxCardNo = SqlGenerator.getSingleResultFromDB(project, sql);
		boolean bResult = InputCheck.checkStringNotEmpty(curMaxCardNo);
		if(bResult == false) {
			newLoadNo = "1";
			return newLoadNo;
		}
		newLoadNo = String.valueOf(Integer.parseInt(curMaxCardNo)+1);
		return newLoadNo;		
	}
	
	private boolean checkMaximumBookLoans(){
		String project = "__num";
		String sql = SqlGenerator.getCountBookLoansByCardNoCmd(project,cardNo);
		String maximumBookLoans = SqlGenerator.getSingleResultFromDB(project, sql);
		if(!InputCheck.checkStringNotEmpty(maximumBookLoans)){
			//errorInf += "maximumBookLoans could not be empty\n";
			//return false;
			maximumBookLoans = "0";
		}
		
		if(Integer.parseInt(maximumBookLoans) >= MaximumBookLoans){
			errorInf += "each borrower is permitted a maximum of 3 book_loans\n";
			return false;
		}
		return true;
	}
	
	private boolean checkBookCopysAvailalbe(){
		String sql = SqlGenerator.CreateCheckBookCopysAvailalbeCmd(isbn,branchId);
		//no_of_copies checkout available
		Vector<String> project = new Vector<String>();
		project.add("no_of_copies");	
		project.add("checkout");
		project.add("available");
		DataBaseController db = new DataBaseController();
		Vector<Vector<String>> lines = db.executQuery(sql, project);
		//VectorVectorStringNotEmpty()
		if(lines.isEmpty()) return false;
		if(lines.get(0).isEmpty()) return false;
		String no_of_copies = lines.get(0).get(0);
		String checkout = lines.get(0).get(1);
		String available = lines.get(0).get(2);
		
		System.out.println("no_of_copies is "+ no_of_copies
				+"  checkout is "+ checkout
				+"  available is "+ available);
		if(available!=null){
			if(!available.equalsIgnoreCase("null")){
				System.out.println("available is "+ available);
				if(Integer.parseInt(available) > 0) 
				return true;
			} 
		}
		else if (no_of_copies!= null){			
			if (Integer.parseInt(no_of_copies) > 0){
				System.out.println("no_of_copies is "+ no_of_copies);
				return true;
			}
		}
		errorInf += "there are no more book copies avaliable at your library_branch";
		return false;
	}

}
