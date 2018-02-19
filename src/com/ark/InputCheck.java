package com.ark;

import java.util.Vector;

public class InputCheck{
	
	static protected String errorInf = "";//record errorInf
	public static void main(String[] args) {
		System.out.println(checkExistIsbn("0195153448"));
		System.out.println(checkExistIsbn(null));
		System.out.println(checkExistBranchId("8"));
		System.out.println(checkExistBranchId("4"));
		System.out.println(checkExistCardNo("111"));
		System.out.println(checkExistCardNo("ID000001"));
	}
	
	static public String getErrorInf(){
		return errorInf;
	}
	
	static public void clearErrorInf(){
		errorInf = "";
	}
	 
	

	public static boolean checkStringNotEmpty(String item){
		if(item==null)
			return false;
		if(item.isEmpty())
			return false;
		if(item == "")
			return false;
		return true;
	}
	
	public static boolean checkIntegerMoreThan0(String item){
		if(item==null)
			return false;
		if(item.isEmpty())
			return false;
		if(Integer.parseInt(item)< 1){
			return false;
		}
		return true;
	}
	
	 public static boolean VectorVectorStringNotEmpty(Vector<Vector<String>> result){
		 if(result == null) return false;
		 if(result.isEmpty()) return false;
		 if(result.get(0) == null) return false;
		 if(result.get(0).isEmpty()) return false;
		 if(result.get(0).get(0) == "") return false;		 
		 return true;
	 }
	    	
	
	public static boolean checkExistIsbn(String isbn){
		
		if(!checkStringNotEmpty(isbn)){
			errorInf = "isbn could not be empty\n";
			return false;
		}			
		
		String project = "__count";
		String sql = SqlGenerator.CreateCheckExistIsbnCmd(project,isbn);
		String count = SqlGenerator.getSingleResultFromDB(project,sql);
		
		if(!checkIntegerMoreThan0(count)){
			errorInf = "isbn is not existed\n";
			return false;
		}		
		return true;		
	}
	
	public static boolean checkExistBranchId(String branchId){
		
		if(!checkStringNotEmpty(branchId)){
			errorInf = "branchId could not be empty\n";
			return false;
		}			
		
		String project = "__count";
		String sql = SqlGenerator.CreateCheckExistBranchIdCmd(project,branchId);
		String count = SqlGenerator.getSingleResultFromDB(project,sql);
		if(!InputCheck.checkStringNotEmpty(count)){
			return true;
		}
		
		if(!checkIntegerMoreThan0(count)){
			errorInf = "branchId is not existed\n";
			return false;
		}		
		return true;		
	}

	public static boolean checkExistCardNo(String cardNo){
	
		if(!checkStringNotEmpty(cardNo)){
			errorInf = "cardNo could not be empty\n";
			return false;
		}			
		
		String project = "__count";
		String sql = SqlGenerator.CreateCheckExistCardNoCmd(project,cardNo);
		String count = SqlGenerator.getSingleResultFromDB(project,sql);
		
		if(!checkIntegerMoreThan0(count)){
			errorInf = "cardNo is not existed\n";
			return false;
		}		
		return true;		
	}

	public static boolean checkExistSsn(String ssn){
	
		if(!checkStringNotEmpty(ssn)){
			errorInf = "cardNo could not be empty\n";
			return false;
		}			
		
		String project = "__count";
		String sql = SqlGenerator.CreateCheckExistSsnCmd(project,ssn);
		String count = SqlGenerator.getSingleResultFromDB(project,sql);
		
		if(checkIntegerMoreThan0(count)){
			errorInf = "ssn have already existed, change another ssn\n";
			return false;
		}		
		return true;		
	}

	public static boolean checkAllowPayFine(String cardNo){
		String project = "__count";
		String sql = SqlGenerator.CreatecheckAllowPayFineCmd(project, cardNo);
		String count = SqlGenerator.getSingleResultFromDB(project,sql);
		
		if(checkIntegerMoreThan0(count)){
			errorInf = "do not allow payment of a fine for books that are not yet returned\n";
			return false;
		}		
		return true;	
	}
}
