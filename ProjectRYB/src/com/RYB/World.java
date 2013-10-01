package com.RYB;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.RYB.Graphics.Sprite;
import com.RYB.Objects.Entity;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Objects.Blocks.GreyBlock;
import com.RYB.Objects.Level;
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
    
    public World(){
        loadLevel(1);
    }
    public void update(){
        
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).update();
        }
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
    
    private void loadLevel(int curLevel){
        Level level = new Level(curLevel);
        
        ArrayList<Integer> blocks = level.getBlocks();
        
        add(new Player(level.getPlayerStart(), this));
        
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
                        this.add(new ColorBlock(px, py, true, false, false));
                        break;
                    case(3): //yellow block
                        this.add(new ColorBlock(px, py, false, true, false));
                        break;
                    case(4): //blue block
                        this.add(new ColorBlock(px, py, false, false, true));
                        break;
                    case(5): //orange block
                        this.add(new ColorBlock(px, py, true, true, false));
                        break;
                    case(6): //purple block
                        this.add(new ColorBlock(px, py, true, false, true));
                        break;
                    case(7): //green block
                        this.add(new ColorBlock(px, py, false, true, true));
                        break;
                    case(8): //black block
                        this.add(new ColorBlock(px, py, true, true, true));
                        break;
                    case(9): //white block
                        this.add(new ColorBlock(px, py, false, false, false));
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
}
