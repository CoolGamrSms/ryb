
package com.RYB.Objects.Blocks;
import com.RYB.Graphics.Sprite;
import com.RYB.Objects.Player;
import com.RYB.Utils.Vector2f;
import com.RYB.World;
import java.awt.Graphics;
/**
 *
 * @author Ben
 */



public class End extends GreyBlock {
    
    Sprite sprite;
    World world;

    
    public End(float x, float y, World w)
    {
        super(x,y);
        loadEnd();
        world = w;
        
    }
    public End(Vector2f Position, World w){
        super(Position.x, Position.y);
        loadEnd();
        world = w;
       
    }
    
    
      private void loadEnd(){
        sprite = new Sprite("../Assets/end.png");   
    }
      
      public void update()
      {
          if(isOverlap(world.getPlayer()))
          {
              world.nextLevel(); 
              System.out.println("End Level");
          }
         
      }
      
     
}
