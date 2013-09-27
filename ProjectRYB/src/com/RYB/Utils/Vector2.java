package com.RYB.Utils;



public class Vector2 {
    public int x, y;
    
    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public double getMagnitude(){
        return Math.sqrt(x*x + y*y);
    }
}
