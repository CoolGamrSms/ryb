package com.RYB;

import java.awt.Graphics;

/**
 *
 * @author Aaron
 */
public interface DisplayWorld {
    void update();
    void render(Graphics g);
    
    void reset();
    //void load(); ??
    //void save(); ??

}
