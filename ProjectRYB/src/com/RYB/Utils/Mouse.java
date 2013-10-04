/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Utils;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Kyle
 */
public class Mouse implements MouseInputListener
{
    private boolean enabled = false;
    public int x, y;
    public boolean down = false;
    
    public void mouseDragged(MouseEvent e){
        x = e.getX();
        y = e.getY();
        down = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
    
    public void enable(boolean b){
        enabled = b;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
