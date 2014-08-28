/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author vinit
 */
public class JImagePanel extends JPanel {
    private BufferedImage image = null;
    public JImagePanel() {
    
    }

    @Override
    public void paintComponent( Graphics g ) {
        if( image != null ){
            g.drawImage(image,0,0,null);
        }
    }
    
    public void setImage( BufferedImage image ){
        this.image = image;
        this.invalidate();
        this.repaint();
    }
    
}
