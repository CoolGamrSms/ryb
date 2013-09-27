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
 * @author Shane
 */
public class Player extends Dynamic{

    private Color c;
    private int w = 48;
    private int h = 64;
    private float prevx, prevy;
    private float max_spd = 1.6f; //Max velocity of the ball
    private float acc = 0.06f; //Horizontal movement acceleration
    private float fric = 0.06f; //Deceleration when not moving
    private boolean jumping = false;
    
    public Player(float x, float y){
        super(x,y,48,64);
        prevx = x;
        prevy = y;
        c = Color.white;
        velocity = new Vector2f(0f, 0f);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillRect((int)x-w/2, (int)y-h/2, w, h); 
    }

    @Override
    public void update() {
        
       
        prevx = x;
        prevy = y;
        y+=velocity.y;
        x+=velocity.x;
        //Collision code coming as soon as I figure out how to list all statics
        
        if(y + h/2 >= Display.height){
            jumping = false;
            velocity.y = 0f;
            y = Display.height-h/2;
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
       if(!(Keyboard.left ^ Keyboard.right)){ //If both or neither are pressed (XNOR)
           velocity.x=Math.signum(velocity.x)*Math.max(0f,Math.abs(velocity.x)-fric);
       }
       
       if(Keyboard.space && !jumping){
               jumping = true;
               velocity.y=-2f;
       }
        
    }
    
}
