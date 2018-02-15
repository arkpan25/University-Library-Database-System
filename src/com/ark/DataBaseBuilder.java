package com.leo;

import java.io.IOException;
import java.util.Vector;

public class DataBaseBuilder {	
	static String regex = "\t";
	public static void main(String[] args) throws IOException {
		//buildDataBase();
		buildTestDataBase();
	}
	
	public static void buildTestDataBase(){
		
		setRegex("\t");
		String bookFileName = "1books.csv";
		//createBookTable(bookFileName);
		System.out.println("input all books successfully!");
		
		String bookAuthorFileName = "1books.csv";
		//createBookAuthorTable(bookAuthorFileName);
		System.out.println("input all bookAuthor successfully!");
		
		String libraryBranchFileName = "1library_branch.csv";
		//createLibraryBranchTable(libraryBranchFileName);
		System.out.println("input all libraryBranch successfully!");
		
		String bookCopiesFileName = "1book_copies.csv";
		//createBookCopiesTable(bookCopiesFileName);
		System.out.println("input all bookCopies successfully!");
		
		setRegex(",");
		String borrowersFileName = "1borrowers.csv";
		//createBorrowersTable(borrowersFileName);
		System.out.println("input all borrowers successfully!");
		setRegex("\t");
		String bookLoanFileName = "book_loans.csv";
		createLoanTable(bookLoanFileName);
		System.out.println("input all bookLoan successfully!");
	}
	
	public static void buildDataBase(){
		setRegex("\t");
		String bookFileName = "books.csv";
		//createBookTable(bookFileName);
		System.out.println("input all books successfully!");
		
		String bookAuthorFileName = "books.csv";
		//createBookAuthorTable(bookAuthorFileName);
		System.out.println("input all bookAuthor successfully!");
		
		String libraryBranchFileName = "library_branch.csv";
		//createLibraryBranchTable(libraryBranchFileName);
		System.out.println("input all libraryBranch successfully!");
		
		String bookCopiesFileName = "book_copies.csv";
		createBookCopiesTable(bookCopiesFileName);
		System.out.println("input all bookCopies successfully!");
		
		setRegex(",");
		String borrowersFileName = "borrowers.csv";
		createBorrowersTable(borrowersFileName);
		System.out.println("input all borrowers successfully!");	
	}
	
	
	interface TableActionCreat{
		public String createSqlCommand(String values[]);
	}
	
	static class BookTableActionCreat implements TableActionCreat{
		public String createSqlCommand(String values[]){
			//ISBN10	ISBN13	Title	Authro	Cover	Publisher	Pages
			return SqlGenerator.CreateInstValueBookCmd(values[0],values[2]);
		}
	}
	
	static class BookAuthorsTableActionCreat implements TableActionCreat{
		public String createSqlCommand(String values[]){
			//ISBN10	ISBN13	Title	Authro	Cover	Publisher	Pages
			return SqlGenerator.CreateInstValueBookAuthorCmd(values[0],values[3]);
		}
	}
	
	static class LibraryBranchTableActionCreat implements TableActionCreat{
		public String createSqlCommand(String values[]){
			//branch_id	branch_name	address
			return SqlGenerator.CreateInstValueLibraryBranchCmd(values[0],values[1],values[2]);
		}
	}
	
	static class BookCopiesTableActionCreat implements TableActionCreat{
		public String createSqlCommand(String values[]){
			//book_id	branch_id	no_of_copies
			return SqlGenerator.CreateInstValueBookCopiesCmd(values[0],values[1],values[2]);
		}
	}	
	
	static class BorrowersTableActionCreat implements TableActionCreat{
		public String createSqlCommand(String values[]){
			//id,ssn,first_name,last_name,email,address,city,state,phone
			return SqlGenerator.CreateInstValueBorrowCmd(values[0],values[1],values[2],values[3],values[5],values[8]);
		}
	}
	
	static class LoanTableActionCreat implements TableActionCreat{
		public String createSqlCommand(String values[]){
			//String loanId, String isbn, 
			//String branchId, String cardNo, 
			//String dateOut, String dueDate, String dateIn
			if(values.length == 7)
				return SqlGenerator.CreateInstValueBookLoansCmd(values[0],values[1],values[2],values[3],values[4],values[5],values[6]);
			
			else//values.length == 6
				return SqlGenerator.CreateInstValueBookLoansCmd(values[0],values[1],values[2],values[3],values[4],values[5]);
		}
	}
	
	public static void createBorrowersTable(String filename){
		BorrowersTableActionCreat brt = new BorrowersTableActionCreat();
		createTable(filename, brt);
	}
	
	public static void createBookCopiesTable(String filename){
		BookCopiesTableActionCreat bct = new BookCopiesTableActionCreat();
		createTable(filename, bct);
	}

	public static void createLibraryBranchTable(String filename){
		LibraryBranchTableActionCreat lbt = new LibraryBranchTableActionCreat();
		createTable(filename, lbt);
	}
	
	public static void createBookAuthorTable(String filename){
		BookAuthorsTableActionCreat bkat = new BookAuthorsTableActionCreat();
		createTable(filename, bkat);
	}	
	
	public static void createBookTable(String filename){
		BookTableActionCreat bkt = new BookTableActionCreat();
		createTable(filename, bkt);
	}

	public static void createLoanTable(String filename){
		LoanTableActionCreat lt = new LoanTableActionCreat();
		createTable(filename, lt);
	}
	
	public static void createTable(String filename, TableActionCreat action){
		Vector<String> lines = DataReader.readLines(filename);
		DataBaseController db = new DataBaseController();
		
		String line;
		for(int i=1; i< lines.size(); i++){			
			line = lines.get(i);
			line = line.replace("'", "''");
			String values[] = line.split(regex);	
			String sql = action.createSqlCommand(values);
			db.execut(sql);
		}
	}
	
	public static void setRegex(String re){
		regex = re;
	}
	
	public static void printBookMaxSize(String filename) throws IOException{
		int titleMaxLength = 0;
	    int authorNameMaxLength = 0;
		Vector<String> lines = DataReader.readLines(filename);
		String line;

		for(int i=1; i< lines.size(); i++){
			//ISBN10	ISBN13	Title	Authro	Cover	Publisher	Pages
			line = lines.get(i);
			String values[] = line.split("\t");
			if(values[2].length() > titleMaxLength){
				titleMaxLength = values[2].length();
			}
			if(values[3].length()>authorNameMaxLength){
				authorNameMaxLength = values[2].length();
			}			
		}
		System.out.println("titleMaxLength: "+titleMaxLength);
		System.out.println("authorNameMaxLength: "+authorNameMaxLength);
		//titleMaxLength: 202
		//authorNameMaxLength: 70
	}	
}
