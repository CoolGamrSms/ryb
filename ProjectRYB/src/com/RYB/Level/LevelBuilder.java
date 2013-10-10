package com.RYB.Level;

import com.RYB.Objects.Blocks.End;
import com.RYB.Objects.Entity;
import com.RYB.Objects.Player;
import com.RYB.Utils.Vector2f;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private Player player;
    private End end;
    
    
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
        if (player != null) return;
        
        player = p;
        addEntity(row, col, p);
    }
    public void addEnd(int row, int col, End e){
        if (end != null) return;
        
        end = e;
        addEntity(row, col, e);
    }
    public void addEntity(int row, int col, Entity e){
        grid[row][col] = e;
        
        Vector2f position = getPositionVector(row, col);
        e.x = position.x;
        e.y = position.y;
    }
    
    public void removeEntity(int row, int col){
        if (grid[row][col] instanceof Player){
            player = null;
        }
        else if(grid[row][col] instanceof End){
            end = null;
        }
        
        grid[row][col] = null;
    }
       
    public Vector2f getCellVector(int xPressed, int yPressed){
        return new Vector2f( (int) ((xPressed) / tileLength), (int) ((yPressed) / tileLength) );
    }
    public Vector2f getPositionVector(int r, int c){
        return new Vector2f( r * tileLength + tileLength/2, c * tileLength + tileLength/2 );
    }
    
    public void output(File level){
        try {     
            FileWriter writer = new FileWriter(level);
            BufferedWriter out = new BufferedWriter(writer);
            
            ArrayList<String> commands = new ArrayList<String>();
            
            
            //Formmating for saved file
            commands.add("[header]");
            commands.add("width=" + getGridWidth());
            commands.add("height=" + getGridHeight());
            commands.add("tilewidth=" + tileLength);
            commands.add("tileheight" + tileLength);
            commands.add("");
            
            commands.add("[tilesets]");
            commands.add("tileset=tilesheet.png," + tileLength + "," + tileLength + ",0,0");
            commands.add("");
            
            commands.add("[layer]");
            commands.add("type=Tile Layer 1");
            commands.add("data=");
                for (int r = 0; r < getRows(); r++){
                    String aRow = "";
                    for (int c = 0; c < getColumns(); c++){
                        //Not empty and not a player or end
                        if (grid[r][c] == null && !(grid[r][c] instanceof Player || grid[r][c] instanceof End)){
                            aRow += "0,";
                        }
                        else{
                            //TODO: ENTITY.getColor()
                            aRow += "1,";
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
                commands.add("location=" + player.x + "," + player.y + "0,0");
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
                commands.add("location=" + end.x + "," + end.y + "0,0");
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
