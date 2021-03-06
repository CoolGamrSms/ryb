package com.RYB.Utils;



public class Vector2f {
    public float x, y;
    
    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Vector2f(Vector2f tmp){
        this.x = tmp.x;
        this.y = tmp.y;
    }
    
    public double getMagnitude(){
        return Math.sqrt(x*x + y*y);
    }
    
    public static Vector2f add(Vector2f v1, Vector2f v2){
        return new Vector2f(v1.x+v2.x,v1.y+v2.y);
    }
    public static Vector2f multiply(Vector2f v1, float multiplier){
        return new Vector2f(v1.x*multiplier,v1.y*multiplier);
    }
    public static Vector2f negative(Vector2f v){
        return new Vector2f( -v.x, -v.y );
    }
    
    
    @Override
    public String toString(){
        return "x:" + this.x + ", y:" + this.y;        
    }
}
