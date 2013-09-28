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
        boolean xOverlap = (x+width/2>other.x-other.width/2 && x-width/2<other.x+other.width/2);
        boolean yOverlap = (y+height/2>other.y-other.height/2 && y-height/2<other.y+other.height/2);
        return xOverlap && yOverlap;
    }
    public float overlapX(Entity other) {
        if(x<other.x) return ((other.x-other.width/2)-(width/2));
        else return (other.x+other.width/2)+(width/2);
    }
    public float overlapY(Entity other) {
        if(y<other.y) return ((other.y-other.height/2)-(height/2));
        else return ((other.y+other.height/2)+(height/2));
    }
    public abstract void render(Graphics g);
    public abstract void update();
}
