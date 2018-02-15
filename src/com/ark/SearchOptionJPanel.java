package com.leo;

import java.awt.Choice;
import java.awt.GridLayout;
import java.awt.TextField;
import javax.swing.JPanel;

import com.leo.SearchOption; 

 public class SearchOptionJPanel extends JPanel{
	
	private static final long serialVersionUID = 844711297641665826L;
	Choice chSearchType = null;	
	Choice chOpCompare = null;
	TextField contentInput = null;
	Choice chOp = null;		
	public SearchOptionJPanel(int indexSearchType,
			String searchTypes[]){
		super(new GridLayout(1, 4, 10, 2));
		chSearchType =new Choice();
		for(int i = 0; i < searchTypes.length; i++){
			chSearchType.add(searchTypes[i]);
		}		
		chSearchType.select(indexSearchType);			
		add(chSearchType);
		
		chOpCompare =new Choice();			
		chOpCompare.add("like");
		chOpCompare.add("=");
		chOpCompare.select(0);			
		add(chOpCompare);			
		
		contentInput = new TextField(10);			
		add(contentInput);	
					
		chOp =new Choice();
		chOp.add("and");
		chOp.add("or");
		chOp.select(0);			
		add(chOp);
		setVisible(true);			
	}
	
	public void SetchOpHide(){
		chOp.setVisible(false);
	}
	
	public SearchOption generateOptions(){
		SearchOption option = new SearchOption(
				chSearchType.getSelectedItem(),
				chOpCompare.getSelectedItem(),
				contentInput.getText(),
				chOp.getSelectedItem());
		return option;
	}		
}