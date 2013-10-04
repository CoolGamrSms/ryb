
package com.RYB.Objects.Blocks;
import com.RYB.Graphics.Sprite;
import com.RYB.Objects.Entity;
import com.RYB.Utils.Vector2f;
import com.RYB.GameWorld;
import java.awt.Graphics;
/**
 *
 * @author Ben
 */



public class End extends Entity {
    
    GameWorld world;

    
    public End(float x, float y, GameWorld w)
    {
        super(x,y, 32, 32);
        loadEnd();
        world = w;
        
    }
    public End(Vector2f Position, GameWorld w){
        super(Position.x, Position.y, 32, 32);
        loadEnd();
        world = w;
       
    }
    
    
      private void loadEnd(){
        sprite = new Sprite("../Assets/end.png");   
    }
      
      public void update()
      {
          super.update();     //Ben - the way our game is set up we need to call super.update() if you want the sprite location to update itself.
                              // The collision you had set up worked fine the block just wasn't rendering.
          
          if(isOverlap(world.getPlayer()))
          {
              world.nextLevel(); 
              System.out.println("End Level");
          }
         
      }   
     
}
