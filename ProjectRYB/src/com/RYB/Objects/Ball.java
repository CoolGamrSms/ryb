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
    private float max_spd = 1.6f; //Max velocity of the ball
    private float acc = 0.06f; //Horizontal movement acceleration
    private float fric = 0.06f; //Deceleration when not moving
    private boolean jumping = false;
    
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
        velocity = new Vector2f(0f, 0f);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillArc((int)x, (int)y, d,d, 0, 360); 
    }

    @Override
    public void update() {
        
       
       
        y+=velocity.y;
        x+=velocity.x;
        
        if(y + d >= Display.height){
            jumping = false;
            velocity.y = 0f;
        }
        else {
            velocity.y += 0.02f; //psuedo gravity
        }
        
        //Key movements change velocity
       if(Keyboard.right){
           velocity.x=Math.min(velocity.x+2*max_spd+acc,3*max_spd)-2*max_spd;
       }
       if(Keyboard.left){
           velocity.x=Math.max(velocity.x+2*max_spd-acc,max_spd)-2*max_spd;
       }
       if(!Keyboard.left && !Keyboard.right){
           velocity.x=Math.signum(velocity.x)*Math.max(0f,Math.abs(velocity.x)-fric);
       }
       
       if(Keyboard.space && !jumping){
               jumping = true;
               velocity.y=-2f;
       }
        
    }
    
}
