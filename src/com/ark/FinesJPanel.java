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
import java.awt.Checkbox;

public class FinesJPanel extends JPanel{

	private static final long serialVersionUID = -2575376393622965396L;
	String curSelectCardNo = "";
	SearchOptionJPanel soj[] = null;
	JTable jtFinesInfo = null;
	Checkbox chkPreviousContain = null;
	int maxtableRow = 200000;
	Vector<Vector<String>> searchResults = null;
	JLabel jlSearchResult = null; //display result
	private static String searchTypes[] = {"card_no"};
	private static String project[] = {"card_no","sum_fine_amt"};
	
	private class BtnSearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			displyFinesInfo();	
		}
	}
	
	private class BtnUpdateFineListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			LibraryController.getInstnce().updateFines();
			String text = "update Fine successfully";			
			jlSearchResult.setText(text);
		}
	}
	
	private class BtnPayFineListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {		
		
		int row =  jtFinesInfo.getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) jtFinesInfo.getModel();

		curSelectCardNo = (String) model.getValueAt(row, 0);		
		boolean bResult = LibraryController.getInstnce().updatePayFine(curSelectCardNo);	
		String text = "pay successsfully";
		if(bResult==false) text = LibraryController.getInstnce().getErrorInf();					
		jlSearchResult.setText(text);	
		if(bResult ==false) return;
		displyFinesInfo();		
		return;		
		}
	}
	
	private void displyFinesInfo(){
		clearjtBookInfo();
		SearchOption options[] = new SearchOption[searchTypes.length];
		for(int i = 0; i < searchTypes.length; i++){
			options[i] = soj[i].generateOptions();
		}		
		boolean bPreviousContain = chkPreviousContain.getState();
		boolean bResult = LibraryController.getInstnce().displayAllFinesGroupByCardNo(bPreviousContain, options);
		if(!bResult) {
			String text = "search result is 0";
			//JOptionPane.showMessageDialog(null, text);
			jlSearchResult.setText(text);
			return;
		}
		searchResults = LibraryController.getInstnce().getResutls();
		updatejtBookInfo();	
	}
	
	private void clearjtBookInfo(){
		DefaultTableModel model = (DefaultTableModel) jtFinesInfo.getModel();				
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}
	
	public void updatejtBookInfo(){			
		DefaultTableModel model = (DefaultTableModel) jtFinesInfo.getModel();		
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

	public FinesJPanel(){		
		super(new BorderLayout());		
		JScrollPane js = creatTable();
        JPanel jp = createSearchOption();        
        add(js, BorderLayout.CENTER);
        add(jp, BorderLayout.NORTH);    
	}
	
	private JScrollPane creatTable(){
		
		soj = new SearchOptionJPanel[searchTypes.length];      
        jtFinesInfo = new JTable(new DefaultTableModel(project, maxtableRow));        
        JScrollPane sp = new JScrollPane(jtFinesInfo); 
        add(sp); 
        return sp;
	}
	
	private JPanel createSearchOption(){
        JPanel jp = new JPanel(new GridLayout(3, 1, 15, 10));
        for(int i = 0; i < searchTypes.length; i++){
        	soj[i] = new SearchOptionJPanel(i,searchTypes);
        	jp.add(soj[i]);
        }         
        soj[searchTypes.length-1].SetchOpHide();
        JPanel jp1 = new JPanel(new GridLayout(1,3));
             
        jlSearchResult= new JLabel("", JLabel.CENTER);   
        JPanelComTools.SetComJLabel(jlSearchResult);
        
        JButton jbSearch = new JButton("search");
        JPanelComTools.SetComJButton(jbSearch);  
        jbSearch.addActionListener(new BtnSearchListener());
        
        JButton jbPayFine = new JButton("Pay Fine");
        JPanelComTools.SetComJButton(jbPayFine);  
        jbPayFine.addActionListener(new BtnPayFineListener());
        JButton jbUpdateFine = new JButton("Update Fine");
        JPanelComTools.SetComJButton(jbUpdateFine);
        jbUpdateFine.addActionListener(new BtnUpdateFineListener());
        chkPreviousContain = new Checkbox("Previous Fines Contain", false);
        
        jp.add(jlSearchResult);
        
        jp1.add(jbSearch);                
        jp1.add(jbPayFine);
        
        jp1.add(jbUpdateFine); 
        jp1.add(chkPreviousContain); 
        jp.add(jp1);
        add(jp); 
        return jp;
	}
}
