package com.RYB.Level;

import com.RYB.Objects.Entity;

/**
 *
 * @author Aaron
 */
public class LevelBuilder {
    private int tileLength;
    private Entity[][] grid;
    
    public LevelBuilder(int rows, int columns, int tileLength){
        this.tileLength = tileLength;

        //Initializes to all null (empty);
        this.grid = new Entity[rows][columns];
    }
    
    public int getRows(){
        return grid.length;
    }
    public int getColumns(){
        return grid[0].length;
    }
    public int getTileLength(){
        return tileLength;
    }
    
    public void addEntity(int row, int col, Entity e){
        grid[row][col] = e;
    }
    public void removeEntity(int row, int col){
        grid[row][col] = null;
    }
    
    public void output(){
        //Write to file to be able to be read
    }
}
