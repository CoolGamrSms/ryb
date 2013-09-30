/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Graphics.Sprite;
import com.RYB.Utils.Keyboard;
import com.RYB.World;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Shane
 */
public class Player extends Dynamic{

    private float max_spd = 1.6f; //Max velocity of the ball
    private float acc = 0.06f; //Horizontal movement acceleration
    private float fric = 0.06f; //Deceleration when not moving
    private boolean jumping = false;
    
    public Player(float x, float y, World world){
        super(x,y,32,48,world);
        
        
        //set sprite = to a new Sprite object with a path like this
        //should work with any image file, supports Alpha
        //every entity has a sprite object all you have to do is set it and call super.render(g) so it is drawn to the screen.
        //TODO: Animation and sprite sheets
        // -Kyle
        sprite = new Sprite("../Assets/player.png");
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void update() {
        super.update(); //super.update allows us to do the same thing to all derived classes e.g. update graphics location or apply gravity to all
                           //dynamic objects
        
        if(velocity.y==0 && prevy<y) jumping = false;
       
        
        //Key movements change velocity
       if(Keyboard.right){
           velocity.x+=MOVEMENT.x;
           velocity.x = Math.min(velocity.x,2f);
       }
       if(Keyboard.left){
           velocity.x-=MOVEMENT.x;
           velocity.x = Math.max(velocity.x,-2f);
       }
       
       if(Keyboard.space && !jumping && velocity.y==0){
               jumping = true;
               velocity.y = JUMP.y;
       }
        
    }
    
}
