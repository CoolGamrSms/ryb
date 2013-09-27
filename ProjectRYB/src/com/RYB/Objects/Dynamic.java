package com.RYB.Objects;

import com.RYB.Utils.Vector2f;

public abstract class Dynamic extends Entity {
    public static final Vector2f GRAVITY = new Vector2f(0, 0.02f);
    
    protected Vector2f velocity, acceleration;
    
    public Dynamic(float x, float y, int width, int height){
        super(x, y, width, height);
    }
    
    public Vector2f getVelocity(){
        return velocity;
    } 
    public Vector2f getAcceleration(){
        return acceleration;
    }
    
    public void updateKinematics(){
        velocity.x += acceleration.x;
        velocity.y += acceleration.y;
        
        x += velocity.x;
        y += velocity.y;
    }  
}