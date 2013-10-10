/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Graphics.Sprite;
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

    private boolean jumping = false;
    
    public Player(float x, float y, World world){
        super(x,y,32,48,world);
        loadPlayer();
    }
    
    public Player(Vector2f startPosition, World world){
        super(startPosition.x, startPosition.y, 32, 48, world);
        loadPlayer();
    }
    
    private void loadPlayer(){
        sprite = new Sprite("../Assets/player.png");   
    }
    
    @Override
    public void render(Graphics g) {
        //g.fillRect((int)x-width/2, (int)y-height/2, w, h); //Uncomment to show bounding box
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
           velocity.x = Math.min(velocity.x,1.6f);
       }
       if(Keyboard.left){
           velocity.x-=MOVEMENT.x;
           velocity.x = Math.max(velocity.x,-1.6f);
       }
       
       if((Keyboard.up || Keyboard.space) && !jumping && velocity.y==0){
               jumping = true;
               velocity.y = JUMP.y;
       }
      
    }
    
    public void reset(){
        velocity.x = 0;
        velocity.y = 0;
    }
    
}
