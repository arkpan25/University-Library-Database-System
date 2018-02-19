package com.ark;

import java.util.Vector;

import com.ark.SearchOption;

public class LibraryController extends ErrorInfo{
	private BookSearch bookSearch = null;
	private BorrowerManagement borrowerManagement = null;
	private BookCheckOut bookCheckOut = null;
	private BookCheckIn bookCheckIn = null;
	private Fines fines = null;
	static private LibraryController instance = null;
	private Vector<Vector<String>> comResult = null;
	
	private LibraryController(){
		bookSearch = new BookSearch();
		borrowerManagement = new BorrowerManagement();
		bookCheckOut = new BookCheckOut();
		bookCheckIn = new BookCheckIn();
		fines = new Fines();
	}	
	
	public static LibraryController getInstnce(){
		if(instance==null){
			instance = new LibraryController();
		}
		return instance;
	}
	public Vector<Vector<String>> searchBook(SearchOption options[]){
		return bookSearch.searchBook(options);
	}
	
	public boolean createNewBorrower(
			String ssn,
			String fname,
			String lname,
			String address,
			String phone
			){
		clearErrorInf();
		boolean result = borrowerManagement.createNewBorrower(ssn,fname, lname, address, phone);
		if(result == false){
			errorInf += borrowerManagement.getErrorInf(); 
		}
		return result;
	}
	
	public String getNewBorrowerCardNo(){
		return borrowerManagement.getNewCardNo();
	}
	public boolean checkOutBook(String isbn, String branchId, String cardNo){
		bookCheckOut.clearErrorInf();
		boolean result = bookCheckOut.checkOutBook(isbn, branchId, cardNo);
		clearErrorInf();
		if(result == false) errorInf = bookCheckOut.getErrorInf();
		return result;
	}
	
	public Vector<Vector<String>> searchBookLoans(SearchOption options[]){
		return bookCheckIn.searchBookLoans(options);
	}
	
	public void updateBookLoans(String loanId){
		bookCheckIn.updateBookLoans(loanId);
	}
	
	public void updateFines(){
		fines.updateFineTatlbe();		
	}
	
	public boolean updatePayFine(String cardNo){
		boolean bResult = fines.updatePayFine(cardNo);
		if(bResult==false) errorInf = fines.getErrorInf();
		return bResult;
	}
	
	public  boolean displayAllFinesGroupByCardNo(boolean containPrevios, SearchOption options[]){
		boolean bResult = fines.displayAllFinesGroupByCardNo(containPrevios, options);
		if(bResult==true)comResult = fines.getResutls();
		else errorInf = fines.getErrorInf();
		return bResult;
	}
	
	public  Vector<Vector<String>> getResutls(){
	    	return comResult;
	}
}
