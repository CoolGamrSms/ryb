/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Display;
import com.RYB.Utils.Vector2;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Kyle
 */
public class Ball extends Dynamic{

    private Color c;
    private int d = 40;
    
    public Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        
        c = Color.white;
        velocity = new Vector2(3, 1);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillArc(x, y, d,d, 0, 360); 
    }

    @Override
    public void update() {
        x+=velocity.x;
        y+=velocity.y;
        
        if(x + d > Display.width || x < 0){
            velocity.x *= -1;
        }
        if(y + d > Display.height || y < 0){
            velocity.y *= -1;
        }
    }
    
}
