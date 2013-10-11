package com.RYB.Level;

import com.RYB.Display;
import com.RYB.World;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Objects.Entity;
import com.RYB.Utils.Vector2f;
import com.RYB.DisplayWorld;
import com.RYB.Objects.Blocks.End;
import com.RYB.Objects.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 *
 * @author Aaron
 */
public class LevelWorld implements DisplayWorld{
    private Color bgColor = Color.white;
    private LevelBuilder builder;
    
    private World tempWorld;
    private LevelToolBar toolBar;
    
    private static final int blockSize = 32;    //based from current value in GreyBlock.java
    
    
    public LevelWorld(Display parent){
        parent.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                LevelWorld.this.mousePressed(e.getX(), e.getY());
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        tempWorld = new World();
        toolBar = new LevelToolBar();
        
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
                g.drawLine(x, y, x + blockSize, y);  
                g.drawLine(x, y, x, y + blockSize);  
            }
        }
    }
    @Override
    public void reset() {
    }

    @Override
    public void saveLevel(File file) {
        builder.output(file);
    }

    private void mousePressed(int mouseX, int mouseY){
            Vector2f posClicked = builder.getCellVector(mouseX, mouseY);
            
            if (posClicked.x < builder.getRows() && posClicked.y < builder.getColumns()){
                if (toolBar.getEntityToolSelected().equals("Block")){
                    builder.addEntity( (int) (posClicked.x), (int) (posClicked.y), new ColorBlock(blockSize, blockSize, true, false, false, tempWorld));                     
                }
                else if (toolBar.getEntityToolSelected().equals("Player")){
                    builder.addPlayer( (int) (posClicked.x), (int) (posClicked.y), new Player( (int) (posClicked.x), (int) (posClicked.y), tempWorld));                       
                }
                else if (toolBar.getEntityToolSelected().equals("End")){
                    builder.addEnd( (int) (posClicked.x), (int) (posClicked.y), new End((int) (posClicked.x), (int) (posClicked.y), tempWorld));                       
                }

            }      
            
    }
}
