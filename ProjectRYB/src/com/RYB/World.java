package com.RYB;

import java.awt.Graphics;

/**
 *
 * @author Aaron
 */
public interface World {
    void update();
    void render(Graphics g);
    
    void reset();
    //void load(); ??
    //void save(); ??
}
