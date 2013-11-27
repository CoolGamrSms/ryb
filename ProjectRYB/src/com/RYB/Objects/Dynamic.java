package com.RYB.Objects;

import com.RYB.Utils.Vector2f;
import com.RYB.World;

public abstract class Dynamic extends Entity {
    public static Vector2f GRAVITY  = new Vector2f(0, 0.013f),
                           FRICTION = new Vector2f(0.06f, 0),
                           MOVEMENT = new Vector2f(1.0f,0),
                           JUMP     = new Vector2f(0,-2f);
    
    protected Vector2f  maxVelocity = new Vector2f(1.6f, 1.6f),
                        minVelocity = Vector2f.negative(maxVelocity);    
    
    protected Vector2f  velocity, acceleration;
    protected float     prevx, prevy;
    protected World     world;
    protected boolean   onGround;

    
    public Dynamic(float x, float y, int width, int height, World world){
        super(x, y, width, height);
        this.world = world;
        velocity = new Vector2f(0f,0f);
        acceleration = new Vector2f(0f,0f);
    }
    
    public Vector2f getVelocity(){
        return velocity;
    } 
    public Vector2f getAcceleration(){
        return acceleration;
    }
    public Vector2f getMaxConstraintVelocity(){
        return maxVelocity;
    }
    public void setAcceleration(Vector2f a){
        acceleration = a;
    }
    public void setConstraintVelocity(Vector2f v){
        maxVelocity = v;
        minVelocity = Vector2f.negative(v);
    }
    
    public boolean isOnGround(){
        return onGround;
    }

    public void handleKeyPress(){
        //Override this to deal with dynamics that respond to a key press
    }  
    public void handleConstraints(){
        velocity.x = Math.min(velocity.x, maxVelocity.x);
        velocity.y = Math.min(velocity.y, maxVelocity.y);
        
        velocity.x = Math.max(velocity.x, minVelocity.x);
        velocity.y = Math.max(velocity.y, minVelocity.y);
    }
    public void updateKinematics(){
        velocity.x = Math.signum(velocity.x)*(Math.abs(velocity.x)-FRICTION.x); //Apply friction
        if(Math.abs(velocity.x)*3<FRICTION.x) velocity.x = 0; //Round friction
        velocity.x += acceleration.x;
        velocity.y += acceleration.y;
        
        handleKeyPress();
        handleConstraints();
        
        prevx = x; //Store previous x and y coordinates
        prevy = y;
        
        y += velocity.y;
        handleYCollision();
        
        x += velocity.x;
        handleXCollision();
    }  
    protected void handleYCollision(){
        boolean bottomTouchesAnyBlock = false;
        for(int i = 0; i < world.getEntities().size(); i++){
            Entity e = (Entity) world.getEntities().get(i);
            if(e instanceof Static) { //Loops through all static entities in the world
                Static s = (Static)e;
                
                if(this.isOverlap(s) && s.getSolid()) { //Check if the block is solid and overlapping
                    if(!s.wasSolid()) System.out.println("Crushed"); //We need to decide what to do when something gets crushed
                    
                    y = this.overlapY(s); //Snap to edge of colliding block
                    prevy = y-velocity.y; //For player class to recognize jumping should be true
                    velocity.y = 0;
                    
                    if (this.isOverLapBelow(s)){
                        bottomTouchesAnyBlock = true;
                    }                    
                } 
            }
        }
        

        onGround = bottomTouchesAnyBlock;        
    }
    protected void handleXCollision(){

        for(int i = 0; i < world.getEntities().size(); i++){
            Entity e = (Entity) world.getEntities().get(i);
            if(e instanceof Static) { //Loop through all statics in the world
                Static s = (Static)e;
                if(this.isOverlap(s) && s.getSolid()) { //Check for collision with a solid
                    if(!s.wasSolid()) System.out.println("Crushed");
                    x = this.overlapX(s); //Snap to edge of colliding block
                    velocity.x = 0;
                }
            }
        }        
    }
    
    @Override 
    public void update(){
        super.update();
        updateKinematics();
    }
}
