package com.RYB.Utils;



public class Vector2f {
    public float x, y;
    
    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    public double getMagnitude(){
        return Math.sqrt(x*x + y*y);
    }
}
