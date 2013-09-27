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
    private float max_spd = 2; //Max velocity of the ball
    private float acc = 0.04f; //Horizontal movement acceleration
    private float fric = 0.04f; //Deceleration when not moving
    
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
        velocity = new Vector2f(0.75f, 0f);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillArc((int)x, (int)y, d,d, 0, 360); 
    }

    @Override
    public void update() {
        
       velocity.y += 0.02; //psuedo gravity
       
        y+=velocity.y;
        x+=velocity.x;
        
        if(y + d > Display.height || y < 0){
            velocity.y *= -1;
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
       
       if(Keyboard.space){
               c = Color.yellow;
       }else{
           c = Color.green;
       }
        
    }
    
}
