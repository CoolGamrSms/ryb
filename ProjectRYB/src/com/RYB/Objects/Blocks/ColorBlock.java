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
    public Color color;
    public boolean r, yc, b;
    private boolean solid;
    private boolean jPress,kPress,lPress = false;
    //public boolean[] ryb; //commented out so code would compile
    
    public ColorBlock(int x, int y, boolean r, boolean yc, boolean b){
        super(x,y);
        
        this.r = r;
        this.yc = yc;
        this.b = b;
       
  
        //ryb[0] = r;
        //ryb[1] = yc;
        //ryb[2] = b;
        updateColor();
      
    }
    
    public void updateColor()
    {
         solid  = true;
          if(r){
            color = Color.red;
            if(yc)
            {
                if(b)
                    color = Color.black;
                else
                    color = new Color(255,164,0);
            }
            else if(b)
                color = new Color(200,0,255);
        }
        
        else if(yc){
            color = Color.yellow;
            if(b){
                color = Color.green;
            }
        }
        
        else if(b){
            color = Color.blue;
          
        }
        else
        {
            color = Color.white;//or black, what did we decide again?
            solid = false;
        }
    }
    public boolean getSolid()
    {
        return solid;
    }   
    public void render(Graphics g){
        g.setColor(color);
        g.fillRect((int)x,(int)y, width, height);
    }
    public void update(){
   
      if(!Keyboard.KEY_J)
      { 
          jPress=true;
      }
      if(jPress && Keyboard.KEY_J)
      {
          jPress=false;
          if(r)
              r=false;
          else
              r=true;
      }
       if(!Keyboard.KEY_K)
      {
           kPress=true;
      }
      if(kPress && Keyboard.KEY_K)
      {
           kPress=false;
          if(yc)
              yc=false;
          else
              yc=true;
      }
        if(!Keyboard.KEY_L)
      {
           lPress=true;
      }
      if(lPress && Keyboard.KEY_L)
      {
           lPress=false;
          if(b)
              b=false;
          else
              b=true;
      }
        updateColor();    
    }
    
}
