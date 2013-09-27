package com.RYB.Objects;

import com.RYB.Utils.Vector2;

public abstract class Dynamic extends Entity {
    protected Vector2 velocity, acceleration;
    
    public Dynamic(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    
    public Vector2 getVelocity(){
        return velocity;
    } 
    public Vector2 getAcceleration(){
        return acceleration;
    }
}