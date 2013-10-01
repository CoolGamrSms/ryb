package com.RYB.Objects;

import com.RYB.Display;
import com.RYB.Utils.Vector2f;
import com.RYB.World;
import java.util.ArrayList;

public abstract class Dynamic extends Entity {
    public static final Vector2f GRAVITY  = new Vector2f(0, 0.02f),
                                 FRICTION = new Vector2f(0.06f, 0),
                                 MOVEMENT = new Vector2f(0.12f,0),
                                 JUMP     = new Vector2f(0,-2.2f);
    
    protected Vector2f velocity, acceleration;
    protected float prevx, prevy;
    protected int w,h;
    protected World world;
    public Dynamic(float x, float y, int width, int height, World world){
        super(x, y, width, height);
        w = width;
        h = height;
        this.world = world;
        velocity = new Vector2f(0f,0f);
        acceleration = new Vector2f(0f,0f);
        applyForce(GRAVITY,1);
    }
    
    public Vector2f getVelocity(){
        return velocity;
    } 
    public Vector2f getAcceleration(){
        return acceleration;
    }
    
    public void updateKinematics(){
        velocity.x = Math.signum(velocity.x)*(Math.abs(velocity.x)-FRICTION.x); //Apply friction
        if(Math.abs(velocity.x)<FRICTION.x) velocity.x = 0; //Round friction
        velocity.x += acceleration.x;
        velocity.y += acceleration.y;
        
        prevx = x; //Store previous x and y coordinates
        prevy = y;
        
        
        //Update y coordinate and check vertical collisions
        y += velocity.y;
        for(int i = 0; i < world.getEntities().size(); i++){
            Entity e = (Entity) world.getEntities().get(i);
            if(e instanceof Static) { //Loops through all static entities in the world
                Static s = (Static)e;
                if(this.isOverlap(s) && s.getSolid()) { //Check if the block is solid and overlapping
                    y = this.overlapY(s); //Snap to edge of colliding block
                    prevy = y-velocity.y; //For player class to recognize jumping should be true
                    velocity.y = 0;
                }
            }
        }
        //Update x coordinate and check horizontal collisions
        x += velocity.x;
        for(int i = 0; i < world.getEntities().size(); i++){
            Entity e = (Entity) world.getEntities().get(i);
            if(e instanceof Static) { //Loop through all statics in the world
                Static s = (Static)e;
                if(this.isOverlap(s) && s.getSolid()) { //Check for collision with a solid
                    x = this.overlapX(s); //Snap to edge of colliding block
                    velocity.x = 0;
                }
            }
        }
        
        if(y+h/2>Display.height) { //Possibly temporary bottom of screen detect
            y=Display.height-h/2;
            prevy = y-velocity.y;
            velocity.y = 0;
        }
    }  
    public void applyForce(Vector2f force, float multiplier){
        acceleration = Vector2f.add(acceleration, Vector2f.multiply(force,multiplier));
    }
    
    @Override 
    public void update(){
        super.update();
        updateKinematics();
    }
}
