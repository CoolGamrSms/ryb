/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.Graphics.Sprite;
import com.RYB.Utils.Vector2f;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Kyle
 */
public class Level {
    private  ArrayList<Integer> blocks = new ArrayList<Integer>();
    private int gridWidth, gridHeight, tileWidth;
    private Vector2f playerStart, levelGoal;
        
    public Level(int curLevel){
        playerStart = new Vector2f(0,0);
        levelGoal = new Vector2f(0,0);
        loadLevel(curLevel);
    }
    /*
     * Scans through the txt file that corresponds to the current level
     * reads line by line and loads various data based on file
     */
    private void loadLevel(int curLevel){
        
        if(curLevel == 0){
            //TODO: Set up main menu if on level 0
        }
        else{
            URL path = Level.class.getResource(new String("../Assets/Levels/level" + Integer.toString(curLevel) + ".txt"));
            
            try {
                
                FileInputStream is;
                is = new FileInputStream(path.getPath());
                InputStreamReader ir = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(ir);
                
                String curLine;
                //assuming we continue with flare map files...
                while(in.ready()){
                    
                    curLine = in.readLine();
                    
                    if(curLine.contains("[header]")){
                        curLine = in.readLine();
                        gridWidth = Integer.parseInt(curLine.substring(6));//(width=) => 6
                        curLine = in.readLine();
                        gridHeight = Integer.parseInt(curLine.substring(7));
                        
                        tileWidth = Integer.parseInt(in.readLine().substring(10)); //
                        in.readLine();
                    }
                    
                    if(curLine.contains("[tilesets]")){
                        //load tilesets in here
                    }
                    
                    //This creates an ArrayList of Dimensions (gridWidth x gridHeight) with values indicating a specified block type
                    if(curLine.contains("data=")){
                        for(int r = 0; r < gridHeight; r++){
                            curLine = in.readLine();
                            Scanner s = new Scanner(curLine);
                            s.useDelimiter(",");
                            
                            for(int c = 0; c < gridWidth; c++){
                                String k = s.next();
                                blocks.add(Integer.parseInt(k));
                            }
                        }
                    }
                    
                    
                    if(curLine.contains("# playerStart")){
                        in.readLine(); // type
                        curLine = in.readLine();
                        curLine = curLine.substring(9);
                        Scanner s = new Scanner(curLine);
                        s.useDelimiter(",");
                        String k = s.next();
                        playerStart.x = (float)(Double.parseDouble(k) * tileWidth);
                        k = s.next();
                        playerStart.y = (float)(Double.parseDouble(k) * tileWidth);
                    }
                    
                    if(curLine.contains("# levelGoal")){
                        in.readLine(); // type
                        curLine = in.readLine();
                        curLine = curLine.substring(9);
                        Scanner s = new Scanner(curLine);
                        s.useDelimiter(",");
                        String k = s.next();
                        levelGoal.x = (float)(Double.parseDouble(k) * tileWidth + tileWidth);
                        k = s.next();
                        levelGoal.y = (float)(Double.parseDouble(k) * tileWidth + tileWidth);
                    }
                    
                }
                
                
                in.close();
                is.close();
                ir.close();
                
            } catch (IOException ex) {
                Logger.getLogger(Sprite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<Integer> getBlocks() {
        return blocks;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }
    
     public int getTileWidth() {
        return tileWidth;
    }
     
    public Vector2f getPlayerStart() {
        return playerStart;
    }

    public Vector2f getLevelGoal() {
        return levelGoal;
    }
}
