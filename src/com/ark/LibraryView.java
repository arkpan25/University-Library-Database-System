package com.ark;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

public class LibraryView extends JPanel {
	private static final long serialVersionUID = -1145608518628873027L;

	public LibraryView() {
        super(new GridLayout(1, 1));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        int numPanels = 5;
        JComponent panels[] = new JComponent[numPanels];
        //ImageIcon icon = createImageIcon("/images/middle.png");
        
        panels[0] = new BookSearchJPanel();        
        tabbedPane.addTab("   Book  Search   ", null, panels[0],
                "Book Search");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        panels[1] = new BookCheckOutJPanel();
        panels[1].setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("        Check Out    ", null, panels[1],
                "Check Out");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);   
        
        panels[2] = new BookCheckInJPanel();
        tabbedPane.addTab("   Check In     ", null, panels[2],
                "Check In");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        panels[3] = new BorrowerManagementJPanel();
        tabbedPane.addTab("Borrower Management", null, panels[3],
                "Borrower Management");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        
        panels[4] = new FinesJPanel();
        tabbedPane.addTab("        Fines       ", null, panels[4],
                "Fines");
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
        
        //Add the tabbed pane to this panel.
        add(tabbedPane);
        for (int i = 0; i < numPanels; i++) {
            Color color = Color.getHSBColor((float) i / numPanels, 1, 1);            
            tabbedPane.setBackgroundAt(i, color);
        }
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }   
  
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = LibraryView.class.getResource(path);
        //System.out.println(LibraryView.class.getResource("/"));
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("LibraryManagerSystem");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        frame.add(new LibraryView(), BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 800);
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
            }
        });
    }
}