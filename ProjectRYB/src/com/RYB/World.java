package com.RYB;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.RYB.Graphics.Sprite;
import com.RYB.Objects.Entity;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Objects.Blocks.GreyBlock;
import com.RYB.Level.Level;
import com.RYB.Objects.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Kyle
 */
public class World {
    Color bgColor = Color.white;
    Sprite bgImage = new Sprite("../Assets/SkyBackground.jpg");
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    
    public int curLevel = 0;
    
    private Player player;
    
    public World(){
        loadLevel(1);
    }
    public void update(){
        
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).update();
        }        
    }
    
    private void nextLevel(){
        resetWorld();
        curLevel++;
        loadLevel(curLevel);
    }
    
    public void render(Graphics g){
        
         g.setColor(bgColor);
         g.fillRect(0, 0, Display.width, Display.height);
         
         bgImage.draw(g);
         
         for(int i = 0; i < entities.size(); i++){
             entities.get(i).render(g);
         }
    }
    public ArrayList getEntities() {
        return entities;
    }
    private void add(Entity e){
        entities.add(e);
    }
    
    private void resetWorld(){
        entities.clear();
    }
    private void loadLevel(int curLevel){
        Level level = new Level(curLevel);
        
        ArrayList<Integer> blocks = level.getBlocks();
        
        player = new Player(level.getPlayerStart(), this);
        add(player);
        
        for(int y = 0; y < level.getGridHeight(); y++){
            for(int x = 0; x < level.getGridWidth(); x++){
                int px = x * level.getTileWidth() + level.getTileWidth()/2, py = y * level.getTileWidth() + level.getTileWidth()/2;
                switch(blocks.get(x + y * level.getGridWidth())){
                    case (0):
                        break;
                    case (1): //gray block
                        this.add(new GreyBlock(px, py));
                        break;
                    case(2): //red block
                        this.add(new ColorBlock(px, py, true, false, false, this));
                        break;
                    case(3): //yellow block
                        this.add(new ColorBlock(px, py, false, true, false, this));
                        break;
                    case(4): //blue block
                        this.add(new ColorBlock(px, py, false, false, true, this));
                        break;
                    case(5): //orange block
                        this.add(new ColorBlock(px, py, true, true, false, this));
                        break;
                    case(6): //purple block
                        this.add(new ColorBlock(px, py, true, false, true, this));
                        break;
                    case(7): //green block
                        this.add(new ColorBlock(px, py, false, true, true, this));
                        break;
                    case(8): //black block
                        this.add(new ColorBlock(px, py, true, true, true, this));
                        break;
                    case(9): //white block
                        this.add(new ColorBlock(px, py, false, false, false, this));
                        break;
                    default:
                        break;
                }
            }
        }
        //Execute neighbor finding code for all color blocks
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            if(e instanceof ColorBlock) { //Loops through all static entities in the world
                ColorBlock s = (ColorBlock)e;
                s.findNeighbors();
            }
        }
    }
    
}
