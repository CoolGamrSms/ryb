/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Kyle
 */
public class World {
    Color bgColor = Color.black;
  //  private ArrayList<Entity> entities = new ArrayList<Entity>();
    
    
    public void update(){
        
    }
    
    public void render(Graphics g){
         g.setColor(bgColor);
         g.fillRect(0, 0, Display.width, Display.height);
    }
}
