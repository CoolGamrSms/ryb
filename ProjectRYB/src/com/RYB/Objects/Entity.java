package com.RYB.Objects;

import java.awt.Graphics;

public abstract class Entity {
    public float x, y;
    protected int height, width;
    
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    
    public boolean isOverlap(Entity other){
        boolean xOverlap = (other.x > this.x && other.x < this.x + this.width) || (other.x + other.width > this.x && other.x + other.width > this.x + this.width);
        boolean yOverlap = (other.x > this.x && other.x < this.x + this.width) || (other.x + other.width > this.x && other.x + other.width > this.x + this.width);   
        
        return xOverlap && yOverlap;
    }
    
    public abstract void render(Graphics g);
    public abstract void update();
}
