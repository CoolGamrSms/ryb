package com.RYB.Objects;

import com.RYB.Graphics.Sprite;
import static com.RYB.Objects.Dynamic.GRAVITY;
import static com.RYB.Objects.Dynamic.MOVEMENT;
import com.RYB.Utils.Keyboard;
import com.RYB.Utils.Vector2f;
import com.RYB.World;
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
        friction = FRICTION;
    }
    
    private void loadPlayer(){
        sprite = new Sprite("../Assets/player.png", (int) x , (int) y);   
    }
    
    @Override
    public void render(Graphics g) {
        //test
        super.render(g);
    }

    @Override
    public void handleKeyPress(){
       //Right moves right
       if(Keyboard.right){
           if (isOnGround()){
               acceleration.x = MOVEMENT.x;
           }
           else{
               acceleration.x = MOVEMENT_AIR.x;
               friction.x = FRICTION_AIR.x;
           }
       }
       
       //Left moves left
       else if(Keyboard.left){
           if (isOnGround()){
               
               acceleration.x = -MOVEMENT.x;
           }
           else{
               acceleration.x = -MOVEMENT_AIR.x;
               friction.x = FRICTION_AIR.x;
           }       
       }     
       else{
           acceleration.x = 0;
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
        acceleration = GRAVITY;
    }
}
