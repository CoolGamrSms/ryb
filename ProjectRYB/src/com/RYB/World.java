package com.RYB;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.RYB.Objects.Entity;
import com.RYB.Objects.GreyBlock;
import com.RYB.Objects.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Kyle
 */
public class World {
    Color bgColor = Color.black;
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    
   
    
    public World(){
        Player player = new Player(50, 50);
        add(player);
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
