package com.RYB.Level;

import com.RYB.Display;
import com.RYB.World;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Objects.Entity;
import com.RYB.Utils.Vector2f;
import com.RYB.DisplayWorld;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;

/**
 *
 * @author Aaron
 */
public class LevelWorld implements DisplayWorld{
    private Color bgColor = Color.white;
    private LevelBuilder builder;
    
    private World tempWorld;
    
    private static final int blockSize = 32;    //based from current value in GreyBlock.java
    
    
    public LevelWorld(Display parent){
        parent.addMouseListener(new MouseBuilder());
        tempWorld = new World();
        
        builder = new LevelBuilder(parent.getSize().width / blockSize, parent.getSize().width / blockSize, blockSize);
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
         
         drawGridLines(g);
    }

    private void drawGridLines(Graphics g){
        //Reference Lines are gray
        g.setColor(Color.BLACK);
        
        int x, y;
        
        for (int r = 0; r < builder.getRows(); r++){
            for (int c = 0; c < builder.getColumns(); c++){
                x = r*blockSize;
                y = c*blockSize;
                g.drawLine(x, y, x, y);                
            }
        }
    }
    @Override
    public void reset() {
    }

    //Handles mouse click
    private class MouseBuilder implements MouseListener{
        
        @Override
        public void mouseReleased(MouseEvent e) {
            Vector2f posClicked = builder.getCellVector(e.getX(), e.getY());
            if (posClicked.x < builder.getRows() && posClicked.y < builder.getColumns()){
                builder.addEntity( (int) posClicked.x, (int) posClicked.y, new ColorBlock(blockSize, blockSize, true, false, false, tempWorld));   
            }        
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
}
