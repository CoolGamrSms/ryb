/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects.Blocks;

import com.RYB.Objects.Entity;
import java.awt.Color;
import java.awt.Graphics;
import com.RYB.Utils.Keyboard;
import com.RYB.World;

/**
 *
 * @author Kyle
 */
public class ColorBlock extends GreyBlock{
    private Color color,tcolor;
    private boolean rt,yt,bt;
    private boolean n_up=false, n_down=false, n_left=false, n_right=false; //Neighbor variables
    private Color orange = new Color(255,164,0);
    private Color purple = new Color(255,0,200);
    private Color white = new Color(255,255,255,100);
    private boolean[] ryb = new boolean[3];
    protected World world;
    
    public ColorBlock(int x, int y, boolean r, boolean yc, boolean b, World world){
        super(x,y);    
        this.world = world;
        this.ryb[0] = r;
        this.ryb[1] = yc;
        this.ryb[2] = b;
        updateColor();
        rt = ryb[0];
        yt = ryb[1];
        bt = ryb[2];
        if(rt) {
            tcolor = Color.red;
            if(yt) {
                if(bt) tcolor = Color.black;
                else tcolor = orange;
            }
            else if(bt) tcolor = purple;
        }
        else if(yt) {
            if(bt) tcolor = Color.green;
            else tcolor = Color.yellow;
        }
        else if(bt) tcolor = Color.blue;
        else tcolor = white;//or black, what did we decide again?
      
    }
    public void findNeighbors() { //Detect neighbor blocks to adjust outlines
        for(int i = 0; i < world.getEntities().size(); i++){
            Entity e = (Entity) world.getEntities().get(i);
            if(e instanceof ColorBlock && e!=this) { //Loops through all ColorBlocks in the world
                ColorBlock s = (ColorBlock)e;
                if(s.compareColor(rt,yt,bt)) { //Check to see if they are the same color
                    if(s.y==y+height && s.x==x) n_down = true;
                    if(s.y==y-height && s.x==x) n_up = true;
                    if(s.x==x+width && s.y==y) n_right = true;
                    if(s.x==x-width && s.y==y) n_left = true;
                }
            }
        }
    }
    public void updateColor()
    {
        solid=true;
        rt = world.getR()&&ryb[0];
        yt = world.getY()&&ryb[1];
        bt = world.getB()&&ryb[2];
        if(rt) {
            color = Color.red;
            if(yt) {
                if(bt) color = Color.black;
                else color = orange;
            }
            else if(bt) color = purple;
        }
        else if(yt) {
            if(bt) color = Color.green;
            else color = Color.yellow;
        }
        else if(bt) color = Color.blue;
        else
        {
            color = white;//or black, what did we decide again?
            solid = false;
        }
    }
    public boolean compareColor(boolean r, boolean y, boolean b) {
        return (!(r^rt) && !(y^yt) && !(b^bt));
    }
    @Override
    public void render(Graphics g){
        if(color!=white) {
            g.setColor(color);
            g.fillRect((int)x-width/2,(int)y-height/2, width, height); 
        }
        g.setColor(tcolor);
        //THICK LINES
        if(!n_up) g.fillRect((int)x-width/2, (int)y-height/2, width, 2);
        if(!n_down) g.fillRect((int)x-width/2, (int)y+height/2-2, width, 2);
        if(!n_left) g.fillRect((int)x-width/2, (int)y-height/2, 2, height);
        if(!n_right) g.fillRect((int)x+width/2-2, (int)y-height/2, 2, height);
        //THIN LINES
        /*if(!n_up) g.drawLine((int)x-width/2, (int)y-height/2, (int)x+width/2-1, (int)y-height/2);
        if(!n_down) g.drawLine((int)x-width/2, (int)y+height/2-1, (int)x+width/2-1, (int)y+height/2-1);
        if(!n_left) g.drawLine((int)x-width/2, (int)y-height/2, (int)x-width/2, (int)y+height/2-1);
        if(!n_right) g.drawLine((int)x+width/2-1, (int)y-height/2, (int)x+width/2-1, (int)y+height/2-1);*/
          
    }
    @Override
    public void update(){
      psolid = solid;  
    }
    
    public int getColor(){
        if (rt && yt && bt){
            return 8;
        }
        else if (rt && yt){
            return 5;
        }
        else if (rt && bt){
            return 6;
        }
        else if (yt && bt){
            return 7;
        }
        else if (rt){
            return 2;
        }
        else if (yt){
            return 3;
        }
        else if (bt){
            return 4;
        }
        else{
            return 9;
        }
    }
}
