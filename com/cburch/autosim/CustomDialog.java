package com.cburch.autosim;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;

import java.beans.*; //property change stuff
import java.util.Stack;
import java.awt.*;
import java.awt.event.*;

/* 1.4 example used by DialogDemo.java. */
class CustomDialog extends JDialog
                   implements ActionListener,
                              PropertyChangeListener {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String typedValue = null;
	private String msgText = null;
	
    private JTextField textValue;
    
    private JOptionPane optionPane;

    private String btnString1 = "Enter";
    private String btnString2 = "Cancel";

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     */
    public String[] getValidatedText() {
    	String arr[] = new String[1];
    	arr[0] = typedValue;
        return arr;
    }

    /** Creates the reusable dialog. 
     * @param transition */
    public CustomDialog(JDialog aFrame, Transition transition) {
        super(aFrame, true);
        
        setTitle("Transition details:");

        textValue = new JTextField(10);
        //if(transition.getAlphabet() != null)
        textValue.setText(Double.toString(transition.getProbability()));
        //Create an array of the text and components to be displayed.
        msgText = "Probability:";
        Object[] array = {msgText, textValue, transition.createDeleteItem()};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1, btnString2};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[0]);

        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textValue.requestFocusInWindow();
            }
        });

        //Register an event handler that puts the text into the option pane.
        textValue.addActionListener(this);

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }

    public CustomDialog(JDialog aFrame, StateActionLink saLink) {
        super(aFrame, true);
        
        setTitle("Action details:");

        textValue = new JTextField(10);
        //if(transition.getAlphabet() != null)
        textValue.setText(Double.toString(saLink.getReward()));
        //Create an array of the text and components to be displayed.
        msgText = "Reward:";
        Object[] array = {msgText, textValue, saLink.createDeleteItem()};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {btnString1, btnString2};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[0]);

        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                textValue.requestFocusInWindow();
            }
        });

        //Register an event handler that puts the text into the option pane.
        textValue.addActionListener(this);

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }

    
    
    /** This method handles events for the text field. */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }

    /** This method reacts to state changes in the option pane. */
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
         && (e.getSource() == optionPane)
         && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
             JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) {
                    typedValue = textValue.getText();
                    //boolean isValid = isValidGuard(typedGuard);
                    
                if (typedValue.length() > 0) {
                	boolean isValid;
                	if(msgText.equals("Reward:")){
                	     isValid = isValidReward(typedValue);
                	     if(isValid){
                             //we're done; clear and dismiss the dialog
                                 clearAndHide();
                     	    }
                     	    else{
                                JOptionPane.showMessageDialog(
                                 		CustomDialog.this,
                                                 "Sorry, \"" + "please enter a valid " + "\" "
                                                 + "floating point value.\n",
                                                 "Try again",
                                                 JOptionPane.ERROR_MESSAGE);
                     	    }
                	}
                	else{
                		isValid = isValidProbability(typedValue);
                		if(isValid){
                        //we're done; clear and dismiss the dialog
                            clearAndHide();
                	    }
                	    else{
                           JOptionPane.showMessageDialog(
                            		CustomDialog.this,
                                            "Sorry, \"" + "probability can be " + "\" "
                                            + "a value between 0 and 1.\n",
                                            "Try again",
                                            JOptionPane.ERROR_MESSAGE);
                	    }
                	}
                } else {
                    //text was invalid
                	if(typedValue.length() <= 0){		
                		JOptionPane.showMessageDialog(CustomDialog.this, 
                				"Sorry, please insert some value for "+ msgText + " and\n"+
                                "try again");
                                       	
                		}
                }
            } else { //user closed dialog or clicked cancel
                typedValue = null;
                clearAndHide();
            }
        }
    }
    
    private boolean isValidProbability(String someProbability){
    	System.out.println("Inside is valid probability");
    	double probability = Double.parseDouble(someProbability);
    	double eps = 0.0000000000000001;
    	if(probability >= eps && probability <=  (1.000 + eps))
    		return true;
    	return false;
    }
    
    private boolean isValidReward(String someReward){
    	System.out.println("Inside is valid reward");
    	try{
    	    double reward = Double.parseDouble(someReward);
        	return true;
    	}
    	catch(Exception ex){
    	/*	JOptionPane.showMessageDialog(
                    CustomDialog.this,
                    "Sorry, \"" + "please enter a value " + "\" "
                    + "between 0 and 1.\n",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE); */
    	}
    	return false;
    }

    /*private boolean isValidGuard(String guardString) {
		
		guardString = guardString.replaceAll(" ", "");
		
		if(guardString.equals("")){
			return true;
		}
		
		if(guardString.matches("[a-z]+<\\d+")){								// x < c --->  x-0 < 0
			return true;
		}
		
		else if(guardString.matches("[a-z]+>\\d+")){								// x > c --->  0-x < -c
			return true;
		}
		
		else if(guardString.matches("[a-z]+=\\d+")){								// x = c ---> x-0 <= c and 0-x <= c
			return true;
		}
			
		else if(guardString.matches("[a-z]+<=\\d+")){								// x <= c ---> x-0 <= c
			return true;
		}
		
		else if(guardString.matches("[a-z]+>=\\d+")){								// x >= c ---> 0-x <= -c
			return true;
		}
		// new..............
		else if(guardString.matches("[a-z]+-[a-z]+<\\d+")){								// x-y < c
			return true;
		}
		
		else if(guardString.matches("[a-z]+-[a-z]+<=\\d+")){								// x-y <= c
			return true;
		}

		else if(guardString.matches("[a-z]+-[a-z]+>\\d+")){								// x-y > c ---> y-x < -c
			return true;
		}
		
		else if(guardString.matches("[a-z]+-[a-z]+>=\\d+")){								// x-y >= c ---> y-x <= -c
			return true;
		}
			
		else if(guardString.matches("\\(.+\\)AND\\(.+\\)")){
			Stack<Character> stack = new Stack<Character>();
			for(int i = 0; i < guardString.length(); i++){
				if((stack.size() == 0) && (i != 0)){
					if(guardString.substring(i).startsWith("AND")){
						String guardPart1 = guardString.substring(1, i-1);
						String guardPart2 = guardString.substring(i+4, guardString.length()-1);
						boolean isValid1 = isValidGuard(guardPart1);
						boolean isValid2 = isValidGuard(guardPart2);
						if(isValid1 == isValid2 && isValid1 == true)
							return true;
						else
							return false;
					}
				}
				if(guardString.substring(i).startsWith("(")){
					stack.push('(');
				}
				if(guardString.substring(i).startsWith(")")){
					stack.pop();
				}
			}
			if(stack.size() != 0){
				return false;
			}
		}
		else{
			return false;
		}
		return true;
	}  */

	/** This method clears the dialog and hides it. */
    public void clearAndHide() {
        textValue.setText(null);
        setVisible(false);
    }
}

