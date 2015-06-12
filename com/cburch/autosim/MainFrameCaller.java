/*
 * Copyright (c) 2006, Carl Burch.
 * 
 * This file is part of the Automaton Simulator source code. The latest
 * version is available at http://www.cburch.com/proj/autosim/.
 *
 * Automaton Simulator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * Automaton Simulator is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automaton Simulator; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301  USA
 */
 
package com.cburch.autosim;

import java.io.File;
import java.io.FileReader;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import java.io.IOException;
import GUI.ContactEditorUI;

import javax.swing.JOptionPane;

public class MainFrameCaller {
    static final String VERSION_NAME = "1.2";
    static final String FILE_VERSION_NAME = "v1.0";
    private ContactEditorUI cui;
    private Automaton automaton = null;
    public Automaton getAutomaton() {
		return automaton;
	}


	public void setAutomaton(Automaton automaton) {
		this.automaton = automaton;
	}

	public static final int CANCEL_OPTION = 1;
   
    public int returnValue; 
    /**
     * Return value if approve (yes, ok) is chosen.
     */
    public static final int APPROVE_OPTION = 0;

    /**
     * Return value if an error occured.
     */
    public static final int ERROR_OPTION = -1;
    
    static MainFrame win;
    static JFrame parent;
    
    public MainFrameCaller(Component parent){ 
    	this.parent = (JFrame) parent;
    	automaton = new Automaton();
    }

    
    public MainFrame createMainFrame() throws HeadlessException{
    	
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MDP Editor"); 
        return new MainFrame(automaton, parent);
        
    }
    
    
    public int showMainFrame(Component parent)throws HeadlessException {
    	
        if (win != null) {
            // Prevent to show second instance of dialog if the previous one still exists
            return MainFrameCaller.ERROR_OPTION;
        }

        win = createMainFrame();
        Listener l = new Listener();
        win.addWindowListener(l);
        /*win.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	doQuit();
                returnValue = CANCEL_OPTION;
            }
        }); */
        returnValue = ERROR_OPTION;
        win.setVisible(true);
        //firePropertyChange("JFileChooserDialogIsClosingProperty", dialog, null);
        // Remove all components from dialog. The MetalFileChooserUI.installUI() method (and other LAFs)
        // registers AWT listener for dialogs and produces memory leaks. It happens when
        // installUI invoked after the showDialog method.
        //win = null;
        System.out.println("Now returning " + returnValue);
        return returnValue;
        
     }
    
    
    public void doQuit(){
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
            	if(win.getEnteredValidMDP())
                	returnValue = APPROVE_OPTION;
                else
                	returnValue = CANCEL_OPTION;
            	win.getContentPane().removeAll();
                win.dispose();
                win = null;
            }
            else{
            	win.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            }
            System.out.println("Moving out of Do Quit. Value is now" + returnValue);
        }
    
    private class Listener implements WindowListener, ComponentListener {
        public void windowActivated(WindowEvent e) { }
        public void windowClosed(WindowEvent e) {
        //	doQuit();
            //System.exit(0);
        } 
        public void windowClosing(WindowEvent e) {
            doQuit();
        }
        public void windowDeactivated(WindowEvent e) { }
        public void windowDeiconified(WindowEvent e) { }
        public void windowIconified(WindowEvent e) { }
        public void windowOpened(WindowEvent e) { }

        public void componentHidden(ComponentEvent e) { }
        public void componentMoved(ComponentEvent e) { }
        public void componentResized(ComponentEvent e) {
        	win.getCurrentCanvas().computeSize();
        }
        public void componentShown(ComponentEvent e) {
        	win.getCurrentCanvas().computeSize();
        }
    }
    
    public static MainFrame getMainFrame(){
    	 return win;
     } 
     
}
