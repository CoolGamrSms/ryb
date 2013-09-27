/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Utils;

import com.RYB.Display;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Kyle
 */
public class Mouse implements MouseMotionListener
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
    
}
