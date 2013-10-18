
package com.RYB.Objects.Blocks;
import com.RYB.Graphics.Sprite;
import com.RYB.Objects.Entity;
import com.RYB.Utils.Vector2f;
import com.RYB.World;
import java.awt.Graphics;
/**
 *
 * @author Ben
 */



public class End extends Entity {
    
    World world;

    
    public End(float x, float y, World w)
    {
        super(x,y, 32, 32);
        loadEnd();
        world = w;
        
    }
    public End(Vector2f Position, World w){
        super(Position.x, Position.y, 32, 32);
        loadEnd();
        world = w;
       
    }
    
    
      private void loadEnd(){
        sprite = new Sprite("../Assets/end.png", (int) x, (int) y);   
    }
      
      public void update()
      {
          super.update();     
          
          if(isOverlap(world.getPlayer()))
          {
              world.nextLevel(); 
          }
         
      }   
     
}
