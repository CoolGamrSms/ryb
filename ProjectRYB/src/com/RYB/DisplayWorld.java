package com.RYB;

import java.awt.Graphics;
import java.io.File;

/**
 *
 * @author Aaron
 */
public interface DisplayWorld {
    void update();
    void render(Graphics g);
    
    void reset();
    public abstract void saveLevel(File file);
    //void load(); ??
    //void save(); ??

}
