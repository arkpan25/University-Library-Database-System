package com.ark;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class JPanelComTools {
	public static void SetComJButton(JButton jb){
		jb.setBounds(20, 30, 100, 25);
        Border b = new LineBorder(Color.YELLOW, 3);
        jb.setBorder(b);
        jb.setBackground(Color.CYAN);
        jb.setOpaque(true);
	}
	
	public static void SetComJLabel(JLabel jl){		  
		jl.setBackground(Color.PINK);
		jl.setOpaque(true);
	}
}
