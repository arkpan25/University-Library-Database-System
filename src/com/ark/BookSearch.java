package com.ark;

import java.util.Vector;
import com.ark.SearchOption;
public class BookSearch {
	private Vector<String> project = null;
	public BookSearch(){		
		project = new Vector<String>();
		project.add("bd.isbn");
		project.add("bd.title");
		project.add("bd.author_name");
		project.add("bd.branch_id");
		project.add("bd.branch_name");
		project.add("bd.no_of_copies");
		project.add("checkout");
		project.add("available");
	}
	
	public static void main(String[] args) {
		BookSearch bs = new BookSearch();
		Vector<Vector<String>> lines = new Vector<Vector<String>>(); 
		SearchOption options[] = new SearchOption[3];
		options[0] = new SearchOption("isbn", "=", "0393045218", "and");
		options[1] = new SearchOption("isbn", "=", "0425176428", "or");
		options[2] = new SearchOption("isbn", "=", "0679425608", "and");
		lines = bs.searchBook(options);
		
		for(Vector<String> line : lines){			
			for(int i = 0; i < line.size(); i++){
				System.out.print(line.get(i)+ "\t");				
			}
			System.out.println("");
		}		
	}

	// assumption input is lower-case
	// could return null 
	public Vector<Vector<String>> searchBook(SearchOption options[]){		
		String whereStatement = SearchOption.generateWhereStatments(options);
		if(!SearchOption.checkContent(whereStatement))
			return null;		
		String sql = SqlGenerator.searchBookCmd(project,whereStatement);
		DataBaseController db = new DataBaseController();		
		return db.executQuery(sql, project);		 
	}

}
