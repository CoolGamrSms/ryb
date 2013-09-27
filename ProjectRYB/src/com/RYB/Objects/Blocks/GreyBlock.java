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
        super(x, y, 64, 64);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect((int)x,(int)y,64,64);
    }

    @Override
    public void update() {

    }
    
}
