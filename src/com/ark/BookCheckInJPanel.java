package com.leo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BookCheckInJPanel extends JPanel{
	private static final long serialVersionUID = -744501943718830740L;

	SearchOptionJPanel soj[] = null;
	JTable jtBookLoansInfo = null;
	int maxtableRow = 200000;
	Vector<Vector<String>> searchResults = null;
	JLabel jlSearchResult = null; //display result
	private static String searchTypes[] = {"card_no","fname","lname","isbn"};
	private static String project[] = {"loan_id","card_no","fname","lname","isbn","date_out","due_date","date_in"};	
	private class BtnSearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			displyBookInfo();	
		}
	}
	
	private class BtnCheckInListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {			
		String loanId = "";
		int row =  jtBookLoansInfo.getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) jtBookLoansInfo.getModel();

		loanId = (String) model.getValueAt(row, 0);		
		LibraryController.getInstnce().updateBookLoans(loanId);				
		String text = "update successsfully";			
		jlSearchResult.setText(text);
		
		displyBookInfo();		
		return;		
		}
	}
	
	private void displyBookInfo(){
		clearjtBookInfo();
		SearchOption options[] = new SearchOption[searchTypes.length];
		for(int i = 0; i < searchTypes.length; i++){
			options[i] = soj[i].generateOptions();
		}		
		
		if((searchResults=LibraryController.getInstnce().searchBookLoans(options))
				==null){
			String text = "search result is 0";
			//JOptionPane.showMessageDialog(null, text);
			jlSearchResult.setText(text);
			return;
		}
		if(searchResults.isEmpty()){
			String text = "search result is 0";
			//JOptionPane.showMessageDialog(null, text);
			jlSearchResult.setText(text);
			return;
		}
		updatejtBookInfo();	
	}
	
	private void clearjtBookInfo(){
		DefaultTableModel model = (DefaultTableModel) jtBookLoansInfo.getModel();				
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}
	
	public void updatejtBookInfo(){
		if(searchResults == null) return;
		if(searchResults.isEmpty()) return;		
		DefaultTableModel model = (DefaultTableModel) jtBookLoansInfo.getModel();
		
		int displayRows = searchResults.size()< maxtableRow? searchResults.size(): maxtableRow;
		for(int i=0; i< displayRows;i++){
			Vector<String> line = searchResults.get(i);
			Object obj[] = new Object[line.size()];
			for(int j = 0; j < line.size(); j++){
				obj[j] = line.get(j);
			}			
			model.addRow(obj);
		}
		
		if(searchResults.size() > maxtableRow){
			String text = "there are " + searchResults.size()+ " results!\n" + maxtableRow+ " results could be displayed!";
			//JOptionPane.showMessageDialog(null, text);
			jlSearchResult.setText(text);			
		}else{
			String text = "there are " + searchResults.size()+ " results!\n";
			//JOptionPane.showMessageDialog(null, text);
			jlSearchResult.setText(text);			
		}
	}		

	public BookCheckInJPanel(){		
		super(new BorderLayout());		
		JScrollPane js = creatTable();
        JPanel jp = createSearchOption();
        add(js, BorderLayout.CENTER);
        add(jp, BorderLayout.NORTH);    
	}
	
	private JScrollPane creatTable(){
		
		soj = new SearchOptionJPanel[searchTypes.length];      
        jtBookLoansInfo = new JTable(new DefaultTableModel(project, maxtableRow));        
        JScrollPane sp = new JScrollPane(jtBookLoansInfo); 
        add(sp); 
        return sp;
	}
	
	private JPanel createSearchOption(){
        JPanel jp = new JPanel(new GridLayout(6, 1, 15, 10));
        for(int i = 0; i < searchTypes.length; i++){
        	soj[i] = new SearchOptionJPanel(i,searchTypes);
        	jp.add(soj[i]);
        }         
        soj[searchTypes.length-1].SetchOpHide();
        JPanel jp1 = new JPanel(new GridLayout(1,3));
        JLabel jl[] = new JLabel[4];
        for(int i = 0; i < 4; i++){
        	jl[i] = new JLabel("", JLabel.CENTER);
        }        
        jlSearchResult= new JLabel("", JLabel.CENTER);   
        JPanelComTools.SetComJLabel(jlSearchResult);
        
        JButton jbSearch = new JButton("search");
        JPanelComTools.SetComJButton(jbSearch);  
        jbSearch.addActionListener(new BtnSearchListener());
        
        JButton jbCheckIn = new JButton("checkIn");
        JPanelComTools.SetComJButton(jbCheckIn);  
        jbCheckIn.addActionListener(new BtnCheckInListener());
        
        jp.add(jlSearchResult);
        
        jp1.add(jbSearch);                
        jp1.add(jbCheckIn);        
        jp1.add(jl[0]); 
        jp1.add(jl[1]); 
        jp.add(jp1);
        add(jp); 
        return jp;
	}
}
