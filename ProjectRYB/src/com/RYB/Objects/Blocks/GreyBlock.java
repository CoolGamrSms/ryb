/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects.Blocks;

import com.RYB.Objects.Static;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Shane
 */
public class GreyBlock extends Static {
    
    public GreyBlock(float x, float y){
        super(x, y, 32, 32);
        solid = true;
        psolid = true;
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect((int)x-16,(int)y-16,32,32);
    }
    
    public int getColor(){
        return 1;
    }
}
