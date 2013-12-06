/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Objects;

import com.RYB.World;

/**
 *
 * @author Ben
 */
public class Spike extends Enemy
{
    public Spike(float x, float y, World world)
    {
        super(x,y,32,32,world,"spike");
    }
    
    public int getType()
    {
        return 10;
    }
}
