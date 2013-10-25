package com.RYB.Level;

import com.RYB.Objects.Blocks.End;
import com.RYB.Objects.Blocks.GreyBlock;
import com.RYB.Objects.Entity;
import com.RYB.Objects.Player;
import com.RYB.Utils.Vector2f;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron
 */
public class LevelBuilder {
    private int tileLength;
    private int rows, columns;
    
    private Entity[][] grid;
    private Player player;      private int playerRow = -1, playerCol = -1;
    private End end;            private int endRow = -1,    endCol = -1;
    
    
    public LevelBuilder(int rows, int columns, int tileLength){
        this.tileLength = tileLength;

        //Initializes to all null (empty);
        this.rows = rows;
        this.columns = columns;
        this.grid = new Entity[rows][columns];
    }
    
    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    public int getTileLength(){
        return tileLength;
    }
    
    public int getGridWidth(){
        return getColumns() * getTileLength();
    }
    public int getGridHeight(){
        return getRows() * getTileLength();
    }
    public Entity[][] getEntities(){
        return grid;
    }
    
    public void addPlayer(int row, int col, Player p){
        //Removes old
        if (player != null){
            removeEntity(playerRow, playerCol);
        }
        
        player = p;
        playerRow = row;
        playerCol = col;
        addEntity(row, col, p);
    }
    public void addEnd(int row, int col, End e){
        //Removes old
        if (end != null){
            removeEntity(endRow, endCol);
        }
        
        //Adds new
        end = e;
        endRow = row;
        endCol = col;
        addEntity(row, col, e);
    }
    public void addEntity(int row, int col, Entity e){
        removeEntity(row, col);
        
        Vector2f position = getPositionVector(row, col);
        e.x = position.x;
        e.y = position.y;
        e.update();
        
        grid[row][col] = e;
    }
    
    public void removeEntity(int row, int col){
        //Bounds check
        if (row >= rows || col >= columns){    return; }
        
        if (grid[row][col] instanceof Player){
            player = null;
            playerRow = -1;
            playerCol = -1;
        }
        else if(grid[row][col] instanceof End){
            end = null;
            endRow = -1;
            endCol = -1;
        }
        
        grid[row][col] = null;
    }
       
    /**
     * Gets the cell (row, col) based on the point location x, y.
     * @param xPressed x pixel relative to LevelWorld
     * @param yPressed y pixel relative to LevelWorld
     * @return The cell vector (row, col)
     */
    public Vector2f getCellVector(int xPressed, int yPressed){
        return new Vector2f( (int) ((yPressed) / tileLength), (int) ((xPressed) / tileLength) );
    }
    public Vector2f getPositionVector(int r, int c){
        return new Vector2f( c * tileLength + tileLength/2, r * tileLength + tileLength/2 );
    }
    
    public void output(File level){
        try {     
            FileWriter writer = new FileWriter(level);
            BufferedWriter out = new BufferedWriter(writer);
            
            ArrayList<String> commands = new ArrayList<String>();
            
            
            //Formmating for saved file
            commands.add("[header]");
            commands.add("width=" + rows);
            commands.add("height=" + columns);
            commands.add("tilewidth=" + tileLength);
            commands.add("tileheight" + tileLength);
            commands.add("");
            
            commands.add("[tilesets]");
            commands.add("tileset=tilesheet.png," + tileLength + "," + tileLength + ",0,0");
            commands.add("");
            
            commands.add("[layer]");
            commands.add("type=Tile Layer 1");
            commands.add("data=");
                for (int r = 0; r < rows; r++){
                    String aRow = "";
                    for (int c = 0; c < columns; c++){
                        ///If empty
                        if (grid[r][c] == null){
                            aRow += "0,";
                        }
                        else if (grid[r][c] instanceof GreyBlock){
                            aRow += ((GreyBlock) grid[r][c]).getColor() + ",";
                        }
                        else{
                            aRow += "0,";
                        }
                    }
                    
                    commands.add(aRow);
                }
                String lastCommand = commands.get(commands.size() - 1);
                commands.set(commands.size() - 1, lastCommand.substring(0, lastCommand.length() - 1));  //Trims last comma
            commands.add("");
            
            
            //Player
            commands.add("[Object Layer 1]");
            commands.add("# playerStart");
            commands.add("type=int");
            if (player == null){
                commands.add("location=2,3.96875,0,0");
            }
            else{
                commands.add("location=" + playerCol + "," + playerRow + ",0,0");
            }
            commands.add("");
            
            //Level Goal
            commands.add("[Object Layer 1]");
            commands.add("# levelGoal");
            commands.add("type=int");
            if (end == null){
                commands.add("location=14.7812,9.71875,0,0");
            }
            else{
                commands.add("location=" + endCol + "," + endRow + ",0,0");
            }            
            
            //Writing
            for (String command: commands){
                out.write(command);
                out.newLine();
            }
            
            out.close();
            writer.close();
            
        } catch (IOException ex) {
            Logger.getLogger(LevelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
