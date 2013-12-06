package com.RYB.Objects;

import com.RYB.Display;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Utils.Vector2f;
import com.RYB.World;

public abstract class Dynamic extends Entity {
    public static Vector2f GRAVITY  = new Vector2f(0, 0.02f),
                           FRICTION = new Vector2f(0.04f, 0),
                           FRICTION_AIR = new Vector2f(0.04f, 0.0f),
                           MOVEMENT = new Vector2f(0.2f,0),
                           MOVEMENT_AIR = new Vector2f(0.06f,0.0f),
                           JUMP     = new Vector2f(0,-2.1f);
    
    protected Vector2f  maxVelocity = new Vector2f(1.8f, 4.5f),
                        minVelocity = Vector2f.negative(maxVelocity);    
    
    protected Vector2f  velocity, acceleration, friction;
    protected float     prevx, prevy;
    protected World     world;
    protected boolean   onGround;
    private boolean rCollide, yCollide, bCollide;
    
    public Dynamic(float x, float y, int width, int height, World world){
        super(x, y, width, height);
        this.bCollide = false;
        this.yCollide = false;
        this.rCollide = false;
        this.world = world;
        velocity = new Vector2f(0f,0f);
        acceleration = new Vector2f(0f,0f);
        friction = new Vector2f(0f,0f);
    }
    
    //Getters and Setters
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
    protected boolean isOffMap(){
        if (x < width/2){
            x=width/2;
        }
        if (x > Display.width-width/2){
            x=Display.width-width/2;
        }
        if (y > Display.height){
            return true;
        }   
        
        return false;
    }

    //Physics
    public void updateKinematics(){
        
        if(Math.abs(velocity.x)<friction.x) velocity.x = 0; //Round friction
        
        if(Math.abs(velocity.x)!=0)
            velocity.x = Math.signum(velocity.x)*(Math.abs(velocity.x)-friction.x); //Apply friction
        
        velocity.x += acceleration.x;
        velocity.y += acceleration.y;
        
        handleKeyPress();
        handleConstraints();
        
        prevx = x; //Store previous x and y coordinates
        prevy = y;
       
        rCollide=false;
        yCollide=false;
        bCollide=false;
        
        y += velocity.y;
        handleYCollision();
        
        x += velocity.x;
        handleXCollision();
        
        if (isOffMap()){
            offMap();
        }
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
    public boolean get_rCollide() {
        return rCollide;
    }
    public boolean get_yCollide() {
        return yCollide;
    }
    public boolean get_bCollide() {
        return bCollide;
    }
    protected void handleYCollision(){
        boolean bottomTouchesAnyBlock = false;
        for(int i = 0; i < world.getEntities().size(); i++){
            Entity e = (Entity) world.getEntities().get(i);
            if(e instanceof Static) { //Loops through all static entities in the world
                Static s = (Static)e;
                
                if(this.isOverlap(s)) { //Check if there is a collision
                    if(s.getSolid()) { //Check if the block is solid
                        y = this.overlapY(s); //Snap to edge of colliding block
                        prevy = y-velocity.y; //For player class to recognize jumping should be true
                        velocity.y = 0;
                    
                        if (this.isOverLapBelow(s)){
                            bottomTouchesAnyBlock = true;
                        }                    
                    }
                    else {
                        ColorBlock b = (ColorBlock)s;
                        if(b.getR()) rCollide = true;
                        if(b.getYw()) yCollide = true;
                        if(b.getB()) bCollide = true;
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
                    x = this.overlapX(s); //Snap to edge of colliding block
                    velocity.x = 0;
                }
            }
        }   
    }
    
    //Rendering
    
    @Override 
    public void update(){
        super.update();
        updateKinematics();
    }
    
    //Death Handling
    
    public void crushed(){
        world.resetPlayer();
    }
    public void offMap(){
        world.resetPlayer();
    }
}
