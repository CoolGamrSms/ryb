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
    private static int x, y;
    private static boolean down = false;
    private static boolean clicked = false;
    
    public static Vector2f getCoords(){
        return new Vector2f((float) x, (float) y);
    }
    
    public static boolean clicked(){ //a click is a press and release
        return down;
    }
    
    public void mouseDragged(MouseEvent e){
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
    
    
    public void enable(boolean b){
        enabled = b;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        
        if(clicked)
            clicked = false;
        else
            clicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        down = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        down = false;
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
