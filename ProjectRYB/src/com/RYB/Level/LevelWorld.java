package com.RYB.Level;

import com.RYB.Display;
import com.RYB.GameWorld;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Objects.Entity;
import com.RYB.Utils.Vector2f;
import com.RYB.World;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Aaron
 */
public class LevelWorld implements World, MouseListener{
    private Color bgColor = Color.white;
    private LevelBuilder builder;
    
    private Display parent;
    private GameWorld tempWorld;
    
    private static final int blockSize = 32;    //based from current value in GreyBlock.java
    
    
    public LevelWorld(Display parent){
        this.parent = parent;
        parent.addMouseListener(this);
        tempWorld = new GameWorld();
        
        builder = new LevelBuilder(parent.getSize().width / blockSize, parent.getSize().width / blockSize, blockSize);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        Vector2f posClicked = builder.getCellVector(e.getX(), e.getY());
        if (posClicked.x < builder.getRows() && posClicked.y < builder.getColumns()){
            builder.addEntity( (int) posClicked.x, (int) posClicked.y, new ColorBlock(blockSize, blockSize, true, false, false, tempWorld));   
        }
    }
    
    @Override
    public void update() {  
    }

    @Override
    public void render(Graphics g) {
         g.setColor(bgColor);
         g.fillRect(0, 0, Display.width, Display.height);        
         
         //Grid Objects
         Entity[][] grid = builder.getEntities();
         
         for (int r = 0; r < builder.getRows(); r++){
             for (int c = 0; c < builder.getColumns(); c++){
                 if (grid[r][c] != null){
                     grid[r][c].render(g);
                 }
             }
         }         
    }

    @Override
    public void reset() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
