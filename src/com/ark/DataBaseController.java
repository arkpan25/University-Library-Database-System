/**
 *  NAME: DBTest.java
 *  AUTHOR: Chris Irwin Davis
 *          chrisirwindavis@utdallas.edu
 *          The Univeristy of Text at Dallas
 *  DATE: 8 JAN 2016
 *
 *  DESCRIPTION: This Java stub code is an example of how to connect to,
 *               query, and manipulate a MySQL database. This example is
 *               designed to work with the COMPANY database from the textbook
 *               "Fundamentals of Databse Design, 7/E" by Elmasri and Navathe.
 */

package com.ark;
import java.sql.*;
import java.util.Vector;

public class DataBaseController {
	Connection conn = null;
	public DataBaseController(){
		connectDatabase();
		//execut("use BookLibraryTest;");
		execut("use BookLibrary;");
	}	
    
     private void connectDatabase(){
     	try{
    	// Create a connection to the local MySQL server, with the "company" database selected.
		//        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
		// Create a connection to the local MySQL server, with the NO database selected.
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
    	}catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
    }
     public void execut(String command){
    	try{
		// Create a SQL statement object and execute the query.
		Statement stmt = conn.createStatement();
		// Set the current database, if not already set in the getConnection
		// Execute a SQL statement
		stmt.execute(command);
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
    }
    
    public Vector<Vector<String>> executQuery(String command, Vector<String> project){
    	
    	Vector<Vector<String>> lines = new Vector<Vector<String>>();    	
    	Statement stmt;
    	ResultSet rs;
    	try{
			// Create a SQL statement object and execute the query.
    		stmt = conn.createStatement();
			// Set the current database, if not already set in the getConnection
			// Execute a SQL statement
    		rs = stmt.executeQuery(command);
			while(rs.next()){
				Vector<String> line = new Vector<String>();
				for(int i = 0; i < project.size(); i++){
					String elem = rs.getString(project.get(i));
					line.add(elem);					
				}
				lines.add(line);
			}	
			rs.close();			
		}
		catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}finally{	
			  close();
		}    	
    	return lines;
    }
    
    public void close(){
    	try{
    		if(conn != null)
    			conn.close();
    	}
    	catch(SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		} 
    }    
 
    
    public static void main(String[] args) {
		// Initialize variables for fields by data type
    	DataBaseController dbtest = new DataBaseController();
    	dbtest.close();    	
	}
}