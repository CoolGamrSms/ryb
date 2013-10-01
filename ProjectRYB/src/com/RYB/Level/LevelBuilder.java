package com.RYB.Level;

import com.RYB.Objects.Entity;
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
    private Entity[][] grid;
    private int rows, columns;
    
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
    
    public void addEntity(int row, int col, Entity e){
        grid[row][col] = e;
    }
    public void removeEntity(int row, int col){
        grid[row][col] = null;
    }
    
    public Vector2f getCellVector(int xPressed, int yPressed){
        return new Vector2f( (int) xPressed / tileLength, (int) yPressed / tileLength );
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
                        if (grid[r][c] == null){
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
            
            commands.add("[Object Layer 1]");
            commands.add("# playerStart");
            commands.add("type=int");
            commands.add("location=2,3.96875,0,0");
            commands.add("");
            
            commands.add("[Object Layer 1]");
            commands.add("# levelGoal");
            commands.add("type=int");
            commands.add("location=14.7812,9.71875,0,0");
            
            //Writing
            for (String command: commands){
                out.write(command);
                out.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(LevelBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
