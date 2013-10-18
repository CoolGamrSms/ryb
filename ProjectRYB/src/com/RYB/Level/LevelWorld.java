package com.RYB.Level;

import com.RYB.Display;
import com.RYB.World;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Objects.Entity;
import com.RYB.Utils.Vector2f;
import com.RYB.DisplayWorld;
import com.RYB.Objects.Blocks.End;
import com.RYB.Objects.Blocks.GreyBlock;
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
            private int clickX, clickY;
            
            public void mouseClicked(MouseEvent e) {                
            }
            public void mousePressed(MouseEvent e) {
                clickX = e.getX();
                clickY = e.getY();
                LevelWorld.this.mousePressed(clickX, clickY);
            }
            public void mouseReleased(MouseEvent e) {
                //Check that this is not just a mouse press release instead of a drag
                if (clickX == e.getX() && clickY == e.getY()){ return; }
                
                LevelWorld.this.mouseReleased(clickX, e.getX(), clickY, e.getY());
                clickX = -1;
                clickY = -1;
            }
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
            Vector2f cellClicked = builder.getCellVector(mouseX, mouseY);
            
            if (cellClicked.x < builder.getRows() && cellClicked.y < builder.getColumns()){
                if (toolBar.getEntityToolSelected().equals("Block")){
                    boolean r = false, b = false, y = false, gray = false;
                    
                    switch (toolBar.getBlockColorSelected()){
                        case "Gray":
                            gray = true;
                            break;
                        case "Red":
                            r = true;
                            break;
                        case "Blue":
                            b = true;
                            break;
                        case "Yellow":
                            y = true;
                            break;
                        case "Orange":
                            r = true; y = true;
                            break;
                        case "Green":
                            b = true; y = true;
                            break;
                        case "Purple":
                            r = true; b = true;
                            break;
                        case "Black":
                            r = true; b = true; y = true;
                            break;
                        default:
                            break;
                    }
                    
                    if (gray){
                        builder.addEntity( (int) (cellClicked.x), (int) (cellClicked.y), new GreyBlock(blockSize, blockSize));
                    }
                    else {
                        builder.addEntity( (int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(blockSize, blockSize, r, y, b, tempWorld));                     
                    }
                }
                else if (toolBar.getEntityToolSelected().equals("Player")){
                    System.out.println(cellClicked.x * blockSize);
                    builder.addPlayer( (int) (cellClicked.x), (int) (cellClicked.y), new Player( (int) (cellClicked.x * blockSize), (int) (cellClicked.y * blockSize), tempWorld));                       
                }
                else if (toolBar.getEntityToolSelected().equals("End")){
                    builder.addEnd( (int) (cellClicked.x), (int) (cellClicked.y), new End((int) (cellClicked.x * blockSize), (int) (cellClicked.y * blockSize), tempWorld));                       
                }

            }      
            
    }
    private void mouseReleased(int clickX, int releaseX, int clickY, int releaseY){
        //Exits if not block drags
        if (toolBar.getEntityToolSelected().equals("Player") || toolBar.getEntityToolSelected().equals("End")){
            return;
        }
 
        
        Vector2f cellClicked = builder.getCellVector(clickX, clickY);
        Vector2f cellReleased = builder.getCellVector(releaseX, releaseY);
        
        float xD = Math.signum(cellReleased.x - cellClicked.x);
        float yD = Math.signum(cellReleased.y - cellClicked.y);
        
        if (xD == 0){ xD = 1; }
        if (yD == 0){ yD = 1; }
        for (float xCol = cellClicked.x; (xD == 1.0f && xCol <= cellReleased.x) || (xD == -1.0f && xCol >= cellReleased.x); xCol += xD){
            for (float yRow = cellClicked.y; (yD == 1.0f &&  yRow <= cellReleased.y) || (yD == -1.0f && yRow >= cellReleased.y); yRow += yD){
                mousePressed((int) xCol * blockSize, (int) yRow * blockSize);                
            }
        }
    }
}
