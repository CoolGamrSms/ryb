/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.World;
import java.util.ArrayList;

/**
 *
 * @author Kyle
 */
public class MovingPlatform extends Dynamic{
    
    public ArrayList<ColorBlock> blockArray = new ArrayList<ColorBlock>();
    private int size = 1;
    
    private float x, y;
    private float startX, startY, endX, endY;
    private World world;
    private float width, height;
    
    private boolean[] color;
    
    private boolean movingLeft = true;
    
    public MovingPlatform(float startX, float startY, float endX, float endY, int size, boolean[] color, World world){
        super(startX, startY, 32 * size, 32, world);
        this.size = size;
        this.x = startX;
        this.y = startY;
        this.world = world;
        this.color = color;
        
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        
        this.width = size * 32;
        this.height = 32;
        
        init();
    }
    
    public void init(){
        for(int i = 0; i < size; i++){
            blockArray.add(new ColorBlock((int)(x + 32*i),(int) y,color[0], color[1], color[2], world));
        }
        for(int i = 0; i < size; i++){
            world.add(blockArray.get(i));
        }
    }
    
    public void update(){
        super.update();
        if(movingLeft && x <= startX){
            movingLeft = false;
        } else if(!movingLeft && x >= endX){
            
            movingLeft = true;
        }
        
        if(movingLeft){
            x -= 1;
             for(int i = 0; i < size; i++)
                blockArray.get(i).x -= 1;
        }else{
            x += 1;
             for(int i = 0; i < size; i++)
                blockArray.get(i).x += 1;
        }
    }
    
}
