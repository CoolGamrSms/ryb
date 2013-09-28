package com.RYB;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.RYB.Objects.Entity;
import com.RYB.Objects.Blocks.GreyBlock;
import com.RYB.Objects.Blocks.ColorBlock;
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
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    
   
    
    public World(){
        Player player = new Player(50, 50);
        add(player);
        ColorBlock block1 = new ColorBlock(200,400,true,false,false);
        add(block1);
        ColorBlock block1a = new ColorBlock(232,400,true,false,false);
        add(block1a);
        ColorBlock block2 = new ColorBlock(300,300,false,true,false);
        add(block2);
        ColorBlock block2a = new ColorBlock(332,300,false,true,false);
        add(block2a);
        ColorBlock block3 = new ColorBlock(400,200,false,false,true);
        add(block3);
        ColorBlock block3a = new ColorBlock(432,200,false,false,true);
        add(block3a);
        ColorBlock block4 = new ColorBlock(500,200,true,true,true);
        add(block4);
        ColorBlock block4a = new ColorBlock(532,200,true,true,true);
        add(block4a);
    }
    public void update(){
        
        for(int i = 0; i < entities.size(); i++){           //TODO: possibly combine these for loops to make it more efficient.
            entities.get(i).update();
        }
    }
    
    public void render(Graphics g){
        
         g.setColor(bgColor);
         g.fillRect(0, 0, Display.width, Display.height);
         
         for(int i = 0; i < entities.size(); i++){
             entities.get(i).render(g);
         }
    }
    
    private void add(Entity e){
        entities.add(e);
    }
}
