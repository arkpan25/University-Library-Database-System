package com.leo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BookCheckOutJPanel extends JPanel{
	private static final long serialVersionUID = 6125199576022913186L;
	JLableAndText jIsbn = null;
	JLableAndText jBranchId = null;
	JLableAndText jCardNo = null;
	JLabel jlResult = null;
	
	public BookCheckOutJPanel(){
		super(new BorderLayout());
		JPanel jp = new JPanel(new GridLayout(11,1));
		jIsbn = new JLableAndText("ISBN:");		
		jBranchId = new JLableAndText("Branch ID:");
		jCardNo = new JLableAndText("Card No:");
		
		JButton jbSubmit = new JButton("Check Out");		    
		jlResult = new JLabel("",JLabel.CENTER);		
		JPanel jp_1 = new JPanel(new GridLayout(1,2));
		int jlsNum = 9;
		JLabel jls[] = new JLabel[jlsNum];
		for(int i = 0; i < jlsNum; i++){
			jls[i] = new JLabel("");
		}
		
        jbSubmit.addActionListener(new BtnCheckOutListener());
        JPanelComTools.SetComJButton(jbSubmit);
        JPanelComTools.SetComJLabel(jlResult);
				
		
		jp.add(jls[0]);
		jp.add(jls[1]);
		jp.add(jIsbn);		
		jp.add(jls[2]);
		jp.add(jBranchId);
		jp.add(jls[3]);
		jp.add(jCardNo);
		jp.add(jls[4]);			
		jp.add(jlResult);
		jp.add(jls[5]);
		jp_1.add(jbSubmit);
		
		jp_1.add(jls[6]);
		jp_1.add(jls[7]);
		jp_1.add(jls[8]);

				
		jp.add(jp_1);
		add(jp,BorderLayout.NORTH);
	}
	private class BtnCheckOutListener implements ActionListener{
	
		public void actionPerformed(ActionEvent e) {
			String isbn = jIsbn.getContent();
			String branchId = jBranchId.getContent();
			String cardNo = jCardNo.getContent();			
			
			if(isbn.isEmpty()||branchId.isEmpty()||cardNo.isEmpty()){
				String text = "ISBN, Branch ID and Card No must not be empty!";
				jlResult.setText("    Fail to check out!");
				JOptionPane.showMessageDialog(null, text);
				return;
			}
			boolean result = LibraryController.getInstnce().checkOutBook(isbn,branchId,cardNo);
			if(result == false){
				String errorInf = LibraryController.getInstnce().getErrorInf();
				jlResult.setText(errorInf);
				return;
			}
			jlResult.setText("    Success to CheckOut ISBN: " + isbn +
					" Branch ID: "+branchId+
					" for Card No: "+cardNo);			
		}
	}
}
