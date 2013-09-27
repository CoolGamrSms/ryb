/*
 This class should be used to output data to the screen
 */
package com.RYB.Utils;

import com.RYB.Objects.Entity;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Kyle
 */
public class Console {
    
    private String curmsg;
    private ArrayList<String> log = new ArrayList<String>();
    
    private Color fontColor;
    
    private boolean enabled = false;
    
    private int x = 10, y = 15;
    private int maxLogSize = 10;
    
    public Console(){
        fontColor = Color.black;
    }
    
    public void writeln(String msg){
        curmsg = msg;
        log.add(msg);
        if(log.size() > maxLogSize){
            log.remove(0);
        }
    }
    
    public void writeEntity(Entity e){
        //TODO: allow devs to pass entity to console and have it automatically format to usefull data
    }
    
    public void clearLog(){
        log.clear();
    }
    
    public void enable(boolean e){
        enabled = e;
    }
    
    public void render(Graphics g){
        if(!enabled) return;
        g.setColor(fontColor);
        
        for(int i = 0; i < log.size(); i++){
            g.drawString(log.get(i), x, y + y*i);
        }
    }
}
