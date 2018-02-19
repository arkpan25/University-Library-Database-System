package com.ark;

public class BorrowerManagement extends ErrorInfo{
	//DataBaseController db = null;
	String new_card_no = null;
	public static void main(String[] args) {
		BorrowerManagement bm = new BorrowerManagement();
		String newCardNo = bm.generateNewCardNo();
		System.out.println(newCardNo);
		String ssn = "438-78-7656";
		String fname = "h";
		String lname = "hi";
		String address = "123 road park way dallas";
		String phone = "";
		
		System.out.println(bm.createNewBorrower(ssn,fname,lname,address,phone));
		System.out.println(bm.getErrorInf());
	}
	
	public BorrowerManagement(){		
	}
	
	public String getNewCardNo(){
		return new_card_no;
	}
	public String generateNewCardNo(){
		String newCardNo = "";
		String oneProject = "__max";
		String sql = SqlGenerator.getMaxCardNoCmd(oneProject);
		String curMaxCardNo = "";
		curMaxCardNo = SqlGenerator.getSingleResultFromDB(oneProject,sql);
		if(!InputCheck.checkStringNotEmpty(curMaxCardNo)){
			return "ID000001";
		}			
		curMaxCardNo = curMaxCardNo.substring(2, curMaxCardNo.length());
		int InewCardNo = Integer.parseInt(curMaxCardNo)+1;
		newCardNo = String.format("ID%06d", InewCardNo);
		return newCardNo;		
	}
	//return new_card_no. if return is "" then fail to create new borrower
	public boolean createNewBorrower(
			String ssn,
			String fname,
			String lname,
			String address,
			String phone
			)
	{
		clearErrorInf();
		if(!InputCheck.checkStringNotEmpty(ssn)
				||!InputCheck.checkStringNotEmpty(fname)
				||!InputCheck.checkStringNotEmpty(lname)
				||!InputCheck.checkStringNotEmpty(address)
				)
		{
			errorInf += "ssn,fname,lname,address must be filled\n";
			return false;
		}
		
		if(!FormateRule.CheckSsn(ssn)){
			errorInf += "ssn formate should be like 111-11-1111\n";
			return false;
		}
		
		if(!InputCheck.checkExistSsn(ssn)){
			errorInf += InputCheck.getErrorInf();
			return false;
		}
		
		new_card_no = generateNewCardNo();
		
		String sql = SqlGenerator.CreateInstValueBorrowCmd(new_card_no,ssn,fname,lname,address,phone);
		DataBaseController db = new DataBaseController();
		db.execut(sql);
		return true;
	}
}
