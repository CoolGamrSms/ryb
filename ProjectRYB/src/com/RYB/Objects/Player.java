/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Graphics.Sprite;
import static com.RYB.Objects.Dynamic.GRAVITY;
import static com.RYB.Objects.Dynamic.MOVEMENT;
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
        this(new Vector2f(x, y), world);
    }
    
    public Player(Vector2f startPosition, World world){
        super(startPosition.x, startPosition.y, 30, 48, world);
        loadPlayer();
        setAcceleration(GRAVITY);
    }
    
    private void loadPlayer(){
        sprite = new Sprite("../Assets/player.png", (int) x , (int) y);   
    }
    
    @Override
    public void render(Graphics g) {
        //g.fillRect((int)x-width/2, (int)y-height/2, w, h); //Uncomment to show bounding box
        super.render(g);
    }

    @Override
    public void handleKeyPress(){
       //Right moves right
       if(Keyboard.right){
           velocity.x+=MOVEMENT.x;
       }
       
       //Left moves left
       if(Keyboard.left){
           velocity.x-=MOVEMENT.x;
       }     
    }
    
    @Override
    public void handleConstraints(){
        //Must limit x movement while in air
        if (!isOnGround()){
            Vector2f tmpMax = new Vector2f(maxVelocity);
            Vector2f tmpMin = new Vector2f(minVelocity);
            
            maxVelocity = new Vector2f(0.8f, tmpMax.y);
            minVelocity = new Vector2f(-0.8f, tmpMin.y);

            super.handleConstraints();

            maxVelocity = tmpMax;
            minVelocity = tmpMin;
        }
        //Normal limits on velocity
        else{
            super.handleConstraints();
        }
    }
    
    @Override
    public void update() {
       super.update();

       if(velocity.y==0 && prevy<y){
           jumping = false;
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
