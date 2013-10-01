/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RYB.Graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Kyle
 * 
 */
public class Sprite {
    private BufferedImage bi;
    public int x = 0, y = 0;
    
    public Sprite(String path){
        try {       
            loadImage(path);
        } catch (IOException ex) {
            Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
     * Loads image into buffered image object
     */
    private void loadImage(String path) throws IOException{
        bi = ImageIO.read(Sprite.class.getResource(path));
    }
    
    /*
     * Draws the buffered image to the screen using graphics object
     */
    public void draw(Graphics g){
        g.drawImage(bi, x, y, null);
    }
}
