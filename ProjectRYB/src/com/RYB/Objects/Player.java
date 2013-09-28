/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Display;
import com.RYB.Utils.Keyboard;
import com.RYB.Utils.Vector2f;
import com.RYB.World;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Shane
 */
public class Player extends Dynamic{

    private Color c;
    private float max_spd = 1.6f; //Max velocity of the ball
    private float acc = 0.06f; //Horizontal movement acceleration
    private float fric = 0.06f; //Deceleration when not moving
    private boolean jumping = false;
    
    public Player(float x, float y, World world){
        super(x,y,48,64,world);
        prevx = x;
        prevy = y;
        c = Color.black;
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(c);
        g.fillRect((int)x-w/2, (int)y-h/2, w, h); 
    }

    @Override
    public void update() {
        
        updateKinematics();
        if(velocity.y==0) jumping = false;
        //Collision code coming as soon as I figure out how to list all statics
        
        //Key movements change velocity
       if(Keyboard.right){
           velocity.x+=MOVEMENT.x;
           velocity.x = Math.min(velocity.x,2.4f);
       }
       if(Keyboard.left){
           velocity.x-=MOVEMENT.x;
           velocity.x = Math.max(velocity.x,-2.4f);
       }
       
       if(Keyboard.space && !jumping){
               jumping = true;
               velocity.y = JUMP.y;
       }
        
    }
    
}
