package com.RYB.Objects;

public abstract class Dynamic extends Entity {
    private Vector2f velocity, acceleration;
    
    public Dynamic(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    
    public Vector2f getVelocity(){
        return velocity;
    } 
    public Vector2f getAcceleration(){
        return acceleration;
    }
}