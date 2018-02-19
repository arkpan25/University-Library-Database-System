package com.ark;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import com.ark.SearchOption;

public class BookSearchJPanel extends JPanel{
	private static final long serialVersionUID = 8515983161365026697L;
	SearchOptionJPanel soj[] = null;
	JTable jtBookInfo = null;
	int maxtableRow = 200000;
	Vector<Vector<String>> searchResults = null;
	JLabel jlSearchResult = null; //display result
	private static String searchTypes[] = {"bd.isbn","bd.title","bd.author_name"};
	//private static String project[] = {"isbn","title","author_name"};
	private static String project[] = {"bd.isbn",//0
									"bd.title", //1
									"bd.author_name",//2
									"bd.branch_id",//3
									"bd.branch_name",//4
									"bd.no_of_copies",//5 
									"checkout", 	//6	
									"available"//7
									};
		
	private class BtnSearchListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			clearjtBookInfo();
			SearchOption options[] = new SearchOption[3];
			options[0] = soj[0].generateOptions();
			options[1] = soj[1].generateOptions();
			options[2] = soj[2].generateOptions();
			if((searchResults=LibraryController.getInstnce().searchBook(options))
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
	}
	private void clearjtBookInfo(){
		DefaultTableModel model = (DefaultTableModel) jtBookInfo.getModel();				
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}
	
	private String[] fillBookAvaliabe(String obj[]){		
		if(obj[6]==null){
			obj[6] = "0";
		}
		if(obj[7] == null){
			obj[7] = obj[5];
		}
		return obj;				
	}
	
	
	public void updatejtBookInfo(){
		if(searchResults == null) return;
		if(searchResults.isEmpty()) return;		
		DefaultTableModel model = (DefaultTableModel) jtBookInfo.getModel();
		
		int displayRows = searchResults.size()< maxtableRow? searchResults.size(): maxtableRow;
		for(int i=0; i< displayRows;i++){
			Vector<String> line = searchResults.get(i);
			String obj[] = new String[line.size()];
			for(int j = 0; j < line.size(); j++){
				obj[j] = line.get(j);
			}	
			obj = fillBookAvaliabe(obj);
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

	public BookSearchJPanel(){
		//super(new FlowLayout());
		super(new BorderLayout());
		//super(new GridLayout(2, 1) );
		//super(new GridBagLayout());
		//GridBagLayout layout = new GridBagLayout();
		//this.setLayout(layout);
		JScrollPane js = creatTable();
        JPanel jp = createSearchOption();
        add(js, BorderLayout.CENTER);
        add(jp, BorderLayout.NORTH);    
	}
	
	private JScrollPane creatTable(){
		soj = new SearchOptionJPanel[3];      
        jtBookInfo = new JTable(new DefaultTableModel(project, maxtableRow));        
        JScrollPane sp = new JScrollPane(jtBookInfo); 
        add(sp); 
        return sp;
	}
	
	private JPanel createSearchOption(){
        JPanel jp = new JPanel(new GridLayout(5, 1, 15, 10));
        soj[0] = new SearchOptionJPanel(0,searchTypes);
        soj[1] = new SearchOptionJPanel(1,searchTypes);
        soj[2] = new SearchOptionJPanel(2,searchTypes);
        jp.add(soj[0]);
        jp.add(soj[1]);
        jp.add(soj[2]); 
        soj[2].SetchOpHide();
        JPanel jp1 = new JPanel(new GridLayout(1,3));
        JLabel jl[] = new JLabel[4];
        for(int i = 0; i < 4; i++){
        	jl[i]= new JLabel("", JLabel.CENTER);
        }
        
        jlSearchResult= new JLabel("", JLabel.CENTER);   
        JPanelComTools.SetComJLabel(jlSearchResult);
        
        JButton jbSearch = new JButton("search");
        JPanelComTools.SetComJButton(jbSearch);  
        jbSearch.addActionListener(new BtnSearchListener());
        jp.add(jlSearchResult); 
        jp1.add(jbSearch);          
        jp1.add(jl[0]);        
        jp1.add(jl[1]);
        jp1.add(jl[2]);
        jp.add(jp1);
        add(jp); 
        return jp;
	}
}
