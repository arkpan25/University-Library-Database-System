package com.ark;

import java.util.Vector;

public class Fines extends ErrorInfo{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fines fine = new Fines();
		fine.updateFineTatlbe();
	}
    static double fineRate = 0.25;
    private Vector<Vector<String>> result = null;
    
    public  Vector<Vector<String>> getResutls(){
    	return result;
    }
    
    public void updateFineTatlbe(){
    	insertFineBookReturned();
    	updateFineBookReturned();
    	insertFineBookStillOut();
    	updateFineBookStillOut();    	
    }
    
	public  boolean displayAllFinesGroupByCardNo(boolean containPrevios, SearchOption options[]){		
		String whereStatement = SearchOption.generateWhereStatments(options);
		if(!SearchOption.checkContent(whereStatement))
			return false;	
		String sum = "sum_fine_amt";
		String cardNo = "cardNo";
		Vector<String> projects = new Vector<String>();
		projects.add(cardNo);
		projects.add(sum);
		String sql = SqlGenerator.CreateDisplayAllFinesGroupbyCardNo(containPrevios,cardNo,sum,whereStatement);
		DataBaseController db = new DataBaseController();		
		result = db.executQuery(sql, projects);	
		
		if(!InputCheck.VectorVectorStringNotEmpty(result)){
			return false;
		}		
		return true;
	}
    
	public  boolean updatePayFine(String cardNo){
		if(!InputCheck.checkAllowPayFine(cardNo)){
			errorInf = InputCheck.getErrorInf();
			return false;
		}
		String sql = SqlGenerator.updatePayFineCmd(cardNo);
		DataBaseController db = new DataBaseController();
    	db.execut(sql);
		return true;
	}
	
    private void insertFineBookReturned(){    	
    	String sql = SqlGenerator.CreateInsertFineBookReturnedCmd();
    	DataBaseController db = new DataBaseController();
    	db.execut(sql);
    }
    
    private void updateFineBookReturned(){    	
    	String sql = SqlGenerator.CreateUpdateFineBookReturnedCmd();
    	DataBaseController db = new DataBaseController();
    	db.execut(sql);
    }
    
    private void insertFineBookStillOut(){    	
    	String sql = SqlGenerator.CreateInsertFineBookStillOutCmd();
    	DataBaseController db = new DataBaseController();
    	db.execut(sql);
    }
    
    private void updateFineBookStillOut(){    	
    	String sql = SqlGenerator.CreateUpdateFineBookStillOutCmd();
    	DataBaseController db = new DataBaseController();
    	db.execut(sql);
    }
}
