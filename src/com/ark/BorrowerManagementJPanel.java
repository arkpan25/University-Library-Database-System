package com.leo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BorrowerManagementJPanel extends JPanel{
	
	JLableAndText jSsn  = null;
	JLableAndText jFName  = null;
	JLableAndText jLName  = null;
	JLableAndText jAddress  = null;
	JLableAndText jPhone = null;
	JLabel jlResult = null;
	
	private static final long serialVersionUID = -1377560648772828596L;
	BorrowerManagementJPanel(){		
		super(new BorderLayout());
		JPanel jp = new JPanel(new GridLayout(15,1));
		jSsn = new JLableAndText("Ssn:");
		jFName = new JLableAndText("First Name:");		
		jLName = new JLableAndText("Last Name:");
		jAddress = new JLableAndText("Address:");
		jPhone = new JLableAndText("Phone:");
		JButton jbSubmit = new JButton("Submit");		    
		jlResult = new JLabel("",JLabel.CENTER);		
		JPanel jp_1 = new JPanel(new GridLayout(1,2));
		int jlsNum = 11;
		JLabel jls[] = new JLabel[jlsNum];
		for(int i = 0; i < jlsNum; i++){
			jls[i] = new JLabel("");
		}
		
        jbSubmit.addActionListener(new BtnSubmitListener());
        JPanelComTools.SetComJButton(jbSubmit);
        JPanelComTools.SetComJLabel(jlResult);
				
        jp.add(jls[10]);
		jp.add(jls[0]);
		jp.add(jSsn);
		jp.add(jls[1]);
		jp.add(jFName);		
		jp.add(jls[2]);
		jp.add(jLName);
		jp.add(jls[3]);
		jp.add(jAddress);
		jp.add(jls[4]);
		jp.add(jPhone);
		jp.add(jls[5]);		
		jp.add(jlResult);
		jp.add(jls[6]);
		jp_1.add(jbSubmit);		
		jp_1.add(jls[7]);
		jp_1.add(jls[8]);
		jp_1.add(jls[9]);
		jp.add(jp_1);
		add(jp,BorderLayout.NORTH);
		//add(jPhone,BorderLayout.NORTH);
	}
	
	private class BtnSubmitListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String ssn = jSsn.getContent();
			String fname = jFName.getContent();
			String lname = jLName.getContent();
			String address = jAddress.getContent();
			String phone = jPhone.getContent();
			if(ssn.isEmpty()||fname.isEmpty()||lname.isEmpty()||address.isEmpty()){
				String text = "Ssn, FirstName, LastName and Address must not be null!";
				jlResult.setText("    Fail to creat New Borrower!");
				JOptionPane.showMessageDialog(null, text);
				return;
			}
			//String newCardNo = 
			if(LibraryController.getInstnce().createNewBorrower(ssn, fname,lname,address,phone)){
				String newCardNo = LibraryController.getInstnce().getNewBorrowerCardNo();
				jlResult.setText("      Success to creat New Borrower! newCardNo is :" + newCardNo);
			}else{
				jlResult.setText(LibraryController.getInstnce().getErrorInf());
			}
			
		}
	}
	
	
}
