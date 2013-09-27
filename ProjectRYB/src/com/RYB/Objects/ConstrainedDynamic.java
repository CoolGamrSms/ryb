package com.RYB.Objects;

/**
 *
 * @author Aaron
 */
public abstract class ConstrainedDynamic extends Dynamic{
    
    public ConstrainedDynamic(float x, float y, int width, int height){
        super(x, y, width, height);
    }
    
    public void handleConstraints(float min_x, float max_x, float min_y, float max_y, float min_dx, float max_dx, float min_dy, float max_dy){
        //Position
            //X
            if (x + getWidth() > max_x){
                x = max_x - getWidth();
            }

            if (x < min_x){
                x = min_x;
            }
        
            //Y
            if (y + getHeight() > max_y){
                y = max_y - getHeight();
            }

            if (y < min_y){
                y = min_y;
            }
        
        //Velocity
            //X
            if (velocity.x > max_dx){
                velocity.x = max_dx;
            }
            
            if (velocity.x < min_dx){
                velocity.x = min_dx;
            }
            
            
            //Y
            if (velocity.y > max_dy){
                velocity.y = max_dy;
            }
            
            if (velocity.y < min_dy){
                velocity.y = min_dy;
            }        
    }
}
