/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author vinit
 */
public class Main {
    
    public static void main(String args[])throws Exception{
                java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new ContactEditorUI().setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    
    
}
