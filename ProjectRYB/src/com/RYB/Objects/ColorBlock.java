/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Kyle
 */
public class ColorBlock extends GreyBlock{
    public Color color;
    public boolean r, yc, b;
    
    public boolean[] ryb;
    
    public ColorBlock(int x, int y, boolean r, boolean yc, boolean b){
        super(x,y);
        
        this.r = r;
        this.yc = yc;
        this.b = b;
        
        ryb[0] = r;
        ryb[1] = yc;
        ryb[2] = b;
        
        if(r){
            color = Color.red;
            if(yc){
                color = Color.orange;
            }
        }
        
        else if(yc){
            color = Color.yellow;
            if(b){
                color = Color.green;
            }
        }
        
        else if(b){
            color = Color.blue;
            if(r){
                color = new Color(255,0,255);
            }
        }
    }
    
    public void render(Graphics g){
        g.setColor(color);
        g.fillRect((int)x,(int)y, width, height);
    }
}
