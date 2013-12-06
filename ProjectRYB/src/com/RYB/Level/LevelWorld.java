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
import com.RYB.Objects.Spike;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.SwingUtilities;

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
                
                //Right Click
                if (SwingUtilities.isRightMouseButton(e)){
                    LevelWorld.this.deletePressed(e.getX(), e.getY());
                }
                //Left Click
                else{
                    LevelWorld.this.mousePressed(clickX, clickY);                    
                }
            }
            public void mouseReleased(MouseEvent e) {
                //Check that this is not just a mouse press release instead of a drag
                if (clickX == e.getX() && clickY == e.getY()){ return; }
                    
                //Right Click
                if (SwingUtilities.isRightMouseButton(e)){
                    LevelWorld.this.deleteReleased(clickX, e.getX(), clickY, e.getY());
                }    
                //Left Click
                else{
                    LevelWorld.this.mouseReleased(clickX, e.getX(), clickY, e.getY());
                }
                
                clickX = -1;                
                clickY = -1;
            }
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        parent.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE){
                    deletePressed(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE){
                    deletePressed(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });
        tempWorld = new World();
        toolBar = new LevelToolBar();
        
        builder = new LevelBuilder(parent.getSize().height / blockSize, parent.getSize().width / blockSize, blockSize);
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
                x = c*blockSize;
                y = r*blockSize;
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

    public void loadLevel(int levelNum) {
        builder.clearAll();
        
        Level level = new Level(levelNum);
        
            Vector2f cellClicked;
            Vector2f entityCenter;        
            int row;
            int col;
            int xCenter;
            int yCenter;
            
        //Player Load
            entityCenter = level.getPlayerStart();
            cellClicked = builder.getCellVector((int)entityCenter.x, (int)entityCenter.y);
            row = (int) cellClicked.x;
            col = (int) cellClicked.y;
            xCenter = (int) entityCenter.x;
            yCenter = (int) entityCenter.y;
            builder.addPlayer( row, col, new Player( xCenter, yCenter , tempWorld));
           
        //End Load
            entityCenter = level.getLevelGoal();
            cellClicked = builder.getCellVector((int)entityCenter.x, (int)entityCenter.y);
            builder.addEnd( (int) (cellClicked.x), (int) (cellClicked.y), new End((int) (cellClicked.y * blockSize), (int) (cellClicked.x * blockSize), tempWorld));
            
        for(int y = 0; y < level.getGridHeight(); y++){
            for(int x = 0; x < level.getGridWidth(); x++){
                int px = x * level.getTileWidth() + level.getTileWidth()/2, py = y * level.getTileWidth() + level.getTileWidth()/2;
                
                entityCenter = new Vector2f(px, py);
                cellClicked = builder.getCellVector((int)entityCenter.x, (int)entityCenter.y);
            
                switch(level.getBlocks().get(x + y * level.getGridWidth())){
                    case (0):
                        break;
                    case (1): //gray block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new GreyBlock(px, py));
                        break;
                    case(2): //red block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, true, false, false, tempWorld));
                        break;
                    case(3): //yellow block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, false, true, false, tempWorld));
                        break;
                    case(4): //blue block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, false, false, true, tempWorld));
                        break;
                    case(5): //orange block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, true, true, false, tempWorld));
                        break;
                    case(6): //purple block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, true, false, true, tempWorld));
                        break;
                    case(7): //green block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, false, true, true, tempWorld));
                        break;
                    case(8): //black block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, true, true, true, tempWorld));
                        break;
                    case(9): //white block
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new ColorBlock(px, py, false, false, false, tempWorld));
                        break;
                    case(10): //spike
                        builder.addEntity((int) (cellClicked.x), (int) (cellClicked.y), new Spike(px, py, tempWorld));
                        break;
                   
                    default:
                        break;
                }
            }
        }
    }
    
    
    private void deletePressed(int mouseX, int mouseY){
        Vector2f cellClicked = builder.getCellVector(mouseX, mouseY);

        if (cellClicked.x < builder.getRows() && cellClicked.y < builder.getColumns() && cellClicked.x >= 0 && cellClicked.y >= 0){
            builder.removeEntity((int) (cellClicked.x), (int) (cellClicked.y));
        }
        
        //tempWorld.update();
    }
    private void deleteReleased(int clickX, int releaseX, int clickY, int releaseY){        
        Vector2f cellClicked = builder.getCellVector(clickX, clickY);
        Vector2f cellReleased = builder.getCellVector(releaseX, releaseY);
                
        float rowDifference = Math.signum(cellReleased.x - cellClicked.x);
        float colDifference = Math.signum(cellReleased.y - cellClicked.y);
        
        //Ensures that both for loops go through at least once
        if (rowDifference == 0){ rowDifference = 1; }
        if (colDifference == 0){ colDifference = 1; }
        

        for (float xCol = cellClicked.x; (rowDifference == 1.0f && xCol <= cellReleased.x) || (rowDifference == -1.0f && xCol >= cellReleased.x); xCol += rowDifference){
            for (float yRow = cellClicked.y; (colDifference == 1.0f &&  yRow <= cellReleased.y) || (colDifference == -1.0f && yRow >= cellReleased.y); yRow += colDifference){
                deletePressed((int) yRow * blockSize, (int) xCol * blockSize);                
            }
        }
    } 
    
    private void mousePressed(int mouseX, int mouseY){
            Vector2f cellClicked = builder.getCellVector(mouseX, mouseY);
            Vector2f entityCenter = builder.getPositionVector((int) cellClicked.x, (int) cellClicked.y);
            
            int row = (int) cellClicked.x;
            int col = (int) cellClicked.y;
            int xCenter = (int) entityCenter.x;
            int yCenter = (int) entityCenter.y;
            
            if (cellClicked.x < builder.getRows() && cellClicked.y < builder.getColumns() && cellClicked.x >= 0 && cellClicked.y >= 0){
                if (toolBar.getEntityToolSelected().equals("Block")) {
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
                                //White
                                break;
                        }
                        
                        if (gray){
                            builder.addEntity(row, col, new GreyBlock(blockSize, blockSize));
                        }
                        else {
                            builder.addEntity(row, col, new ColorBlock(blockSize, blockSize, r, y, b, tempWorld));                     
                        }
                }
                else if (toolBar.getEntityToolSelected().equals("Player")){ 
                        builder.addPlayer( row, col, new Player( xCenter, yCenter , tempWorld));
                }
                else if (toolBar.getEntityToolSelected().equals("End")){
                        builder.addEnd( (int) (cellClicked.x), (int) (cellClicked.y), new End((int) (cellClicked.y * blockSize), (int) (cellClicked.x * blockSize), tempWorld)); 
                }
                else if (toolBar.getEntityToolSelected().equals("Spike")){ 
                        builder.addEntity( row, col, new Spike( xCenter, yCenter , tempWorld));
                }
        } 
    }
    private void mouseReleased(int clickX, int releaseX, int clickY, int releaseY){
        //Exits if not block drags
        if (toolBar.getEntityToolSelected().equals("Player") || toolBar.getEntityToolSelected().equals("End")
           || toolBar.getEntityToolSelected().equals("Spike") ){
            return;
        }
 
        
        Vector2f cellClicked = builder.getCellVector(clickX, clickY);
        Vector2f cellReleased = builder.getCellVector(releaseX, releaseY);
                
        float rowDifference = Math.signum(cellReleased.x - cellClicked.x);
        float colDifference = Math.signum(cellReleased.y - cellClicked.y);
        
        //Ensures that both for loops go through at least once
        if (rowDifference == 0){ rowDifference = 1; }
        if (colDifference == 0){ colDifference = 1; }
        

        for (float xCol = cellClicked.x; (rowDifference == 1.0f && xCol <= cellReleased.x) || (rowDifference == -1.0f && xCol >= cellReleased.x); xCol += rowDifference){
            for (float yRow = cellClicked.y; (colDifference == 1.0f &&  yRow <= cellReleased.y) || (colDifference == -1.0f && yRow >= cellReleased.y); yRow += colDifference){
                mousePressed((int) yRow * blockSize, (int) xCol * blockSize);                
            }
        }
    }
}
