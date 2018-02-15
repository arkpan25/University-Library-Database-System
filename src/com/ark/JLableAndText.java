package com.leo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JLableAndText extends JPanel{
	private static final long serialVersionUID = -386271802157406427L;
	JLabel jl = null;
	TextField tf = null;
	public JLableAndText(String name){
		super(new GridLayout(1, 2, 10, 2));
		jl = new JLabel(name,JLabel.CENTER);
		tf = new TextField(10);
		jl.setBackground(Color.PINK);
		jl.setOpaque(true);
		add(jl);
		add(tf);
	}
	public String getContent(){
		return tf.getText();
	}	
}
