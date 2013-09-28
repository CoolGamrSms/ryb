/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects.Blocks;

import java.awt.Color;
import java.awt.Graphics;
import com.RYB.Utils.Keyboard;

/**
 *
 * @author Kyle
 */
public class ColorBlock extends GreyBlock{
    private Color color,tcolor;
    private boolean r=true, yc=true, b=true;
    private boolean rt,yt,bt;
    private boolean jPress,kPress,lPress = false;
    private Color orange = new Color(255,164,0);
    private Color purple = new Color(255,0,200);
    private Color white = new Color(255,255,255,0);
    private boolean[] ryb = new boolean[3];
    
    public ColorBlock(int x, int y, boolean r, boolean yc, boolean b){
        super(x,y);    
  
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
    
    public void updateColor()
    {
        solid=true;
        rt = r&&ryb[0];
        yt = yc&&ryb[1];
        bt = b&&ryb[2];
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
            color = Color.white;//or black, what did we decide again?
            solid = false;
        }
    }
     
    @Override
    public void render(Graphics g){
        g.setColor(tcolor);
        g.fillRect((int)x-width/2,(int)y-height/2, width, height);
        g.setColor(color);
        g.fillRect((int)x-width/2+2,(int)y-height/2+2, width-4, height-4);   
    }
    @Override
    public void update(){
   
      if(!Keyboard.KEY_J)
      { 
          jPress=true;
      }
      if(jPress && Keyboard.KEY_J)
      {
          jPress=false;
          r=!r;
      }
       if(!Keyboard.KEY_K)
      {
           kPress=true;
      }
      if(kPress && Keyboard.KEY_K)
      {
           kPress=false;
           yc=!yc;
      }
        if(!Keyboard.KEY_L)
      {
           lPress=true;
      }
      if(lPress && Keyboard.KEY_L)
      {
           lPress=false;
           b=!b;
      }
        updateColor();    
    }
    
}
