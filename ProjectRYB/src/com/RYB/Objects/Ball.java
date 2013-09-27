/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Display;
import com.RYB.Utils.Keyboard;
import com.RYB.Utils.Vector2f;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Kyle
 */
public class Ball extends Dynamic{

    private Color c;
    private int d = 40;
    
    public Ball(float x, float y, int r){
        super(x,y,r,r);
        d = r;
        c = Color.white;
        velocity = new Vector2f(0.5f, 0.75f);
    }
    
    public Ball(float x, float y, int r, Color c){
         super(x,y,r,r);
        d = r;
        this.c = c;
        velocity = new Vector2f(0.75f, 0.75f);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillArc((int)x, (int)y, d,d, 0, 360); 
    }

    @Override
    public void update() {
        
       velocity.y += 0.01; //psuedo gravity
       
        y+=velocity.y;
     
        if(y + d > Display.height || y < 0){
            velocity.y *= -1;
        }
        
        //Key movements
       if(Keyboard.right){
           x+=velocity.x;
       }
       if(Keyboard.left){
           x-=velocity.x;
       }
       
       if(Keyboard.space){
           c = Color.yellow;
       }else{
           c = Color.green;
       }
        
    }
    
}
