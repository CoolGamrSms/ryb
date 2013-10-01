/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import java.awt.Rectangle;

/**
 *
 * @author Shane
 */
public abstract class Static extends Entity {
    protected boolean solid = false, psolid = false;
    public Static(float x, float y, int width, int height){
        super(x, y, width, height);
    }
    public boolean getSolid()
    {
        return solid;
    }
    public boolean wasSolid()
    {
        return psolid;
    }
}
