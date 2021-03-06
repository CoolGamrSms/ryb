package com.RYB;

import com.RYB.Graphics.Sprite;
import com.RYB.Objects.Entity;
import com.RYB.Objects.Blocks.End;
import com.RYB.Objects.Blocks.ColorBlock;
import com.RYB.Objects.Blocks.GreyBlock;
import com.RYB.Level.Level;
import com.RYB.Objects.Player;
import com.RYB.Objects.Spike;
import com.RYB.Utils.Keyboard;
import com.RYB.Utils.Vector2f;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Kyle
 */
public class World implements DisplayWorld{
    Color bgColor = Color.white;
    Sprite bgImage = new Sprite("../Assets/Start.jpg");
   // Sprite bgOverlay = new Sprite("../Assets/clearOverlay.png");
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    
    private int curLevel = 0;
    private int maxLevel = 12;
    
    private int score = 0;
    private double[] scoreGoal= new double[3];
    
    private Player player;
    private Vector2f playerStart;
    private End end;
    
    private boolean r=true,y=true,b=true, colorChanged = false; //Global RYB values
    private boolean jPress,kPress,lPress,enterPress = false;
    
    public World(){
        loadLevel(curLevel);
    }
    public void update(){
      if(!Keyboard.KEY_J)
      { 
          jPress=true;
      }
      if(jPress && Keyboard.KEY_J)
      {
          if(!player.get_rCollide()) {
           jPress=false;
           r=!r;
           colorChanged = true;
           score++;
          }
      }
       if(!Keyboard.KEY_K)
      {
           kPress=true;
      }
      if(kPress && Keyboard.KEY_K)
      {
          if(!player.get_yCollide()) {
           kPress=false;
           y=!y;
           colorChanged = true;
           score++;
          }
      }
        if(!Keyboard.KEY_L)
      {
           lPress=true;
      }
      if(lPress && Keyboard.KEY_L)
      {
          if(!player.get_bCollide()) {
           lPress=false;
           b=!b;
           colorChanged = true;
           score++;
          }
      }
      if(Keyboard.KEY_R){
          resetPlayer();
          r = true;
          y = true;
          b = true;
          score = 0;
        
      }
        for(int i = 0; i < entities.size(); i++){
            entities.get(i).update();
        }     
      
            
      
       if(enterPress && Keyboard.enter)
      {
           enterPress=false;
           nextLevel();
      }
        if(!Keyboard.enter)
      {
           enterPress=true;
      }
      
      if(colorChanged)
          updateBlockColors();
      
      colorChanged = false;
    }
   
    public Player getPlayer()
    {
        return player;
    }
    public void nextLevel(){
        reset();
        
        if (curLevel!=0)
        {
            System.out.println("You switched "+ score +" times.");
            if(score <= scoreGoal[0])
                System.out.println("You got Gold!");
            else if(score <= scoreGoal[1])
                System.out.println("You got Silver!");
            else if(score <= scoreGoal[2])
                System.out.println("You got Bronze!");
            else
                System.out.println("Try again!");
            score = 0;
        }
        curLevel++;
        if(curLevel>maxLevel)
        {
            curLevel = maxLevel;
        }
        
        loadLevel(curLevel);
    }
    public void setLevel(int i){
        reset();
        
        curLevel = i;
        
        //Bounds Check
        if (curLevel > maxLevel){
            curLevel = maxLevel;
        }
        
        loadLevel(curLevel);
    }
    
    public void render(Graphics g){
        
         g.setColor(bgColor);
         g.fillRect(0, 0, Display.width, Display.height);
         
         bgImage.draw(g);
         //bgOverlay.draw(g);
         
         for(int i = 0; i < entities.size(); i++){
             if (entities.get(i) != null)
                entities.get(i).render(g);
         }
    }
    public ArrayList getEntities() {
        return entities;
    }
    public boolean getR() {
        return r;
    }
    public boolean getY() {
        return y;
    }
    public boolean getB() {
        return b;
    }
    public void add(Entity e){
        entities.add(e);
    }
    
    public void reset(){
        r=true;
        y=true;
        b=true;
        entities.clear();
    }
    private void loadLevel(int curLevel){
        
        changeBackground(curLevel);
        
        Level level = new Level(curLevel);
        
        ArrayList<Integer> blocks = level.getBlocks();
        
        playerStart = level.getPlayerStart();
        
        player = new Player(level.getPlayerStart(), this);
        add(player);
        end = new End(level.getLevelGoal(),this);
        add(end);
        
       /* boolean[] ryb = new boolean[]{true, false, true};
        add(new MovingPlatform(200, 200, 400, 200, 2,ryb, this));*/
        
        for(int y = 0; y < level.getGridHeight(); y++){
            for(int x = 0; x < level.getGridWidth(); x++){
                int px = x * level.getTileWidth() + level.getTileWidth()/2, py = y * level.getTileWidth() + level.getTileWidth()/2;
                switch(blocks.get(x + y * level.getGridWidth())){
                    case (0):
                        break;
                    case (1): //gray block
                        this.add(new GreyBlock(px, py));
                        break;
                    case(2): //red block
                        this.add(new ColorBlock(px, py, true, false, false, this));
                        break;
                    case(3): //yellow block
                        this.add(new ColorBlock(px, py, false, true, false, this));
                        break;
                    case(4): //blue block
                        this.add(new ColorBlock(px, py, false, false, true, this));
                        break;
                    case(5): //orange block
                        this.add(new ColorBlock(px, py, true, true, false, this));
                        break;
                    case(6): //purple block
                        this.add(new ColorBlock(px, py, true, false, true, this));
                        break;
                    case(7): //green block
                        this.add(new ColorBlock(px, py, false, true, true, this));
                        break;
                    case(8): //black block
                        this.add(new ColorBlock(px, py, true, true, true, this));
                        break;
                    case(9): //white block
                        this.add(new ColorBlock(px, py, false, false, false, this));
                        break;
                    case(10): //spike
                        this.add(new Spike(px, py, this));
                        break;
                   
                    default:
                        break;
                }
            }
        }
        //Execute neighbor finding code for all color blocks
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            if(e instanceof ColorBlock) { //Loops through all color blocks in the world
                ColorBlock s = (ColorBlock)e;
                s.findNeighbors();
            }
        }
        scoreGoal = level.getGoal();
    }
    
    
    private void changeBackground(int curLevel)
    {
        if(curLevel == 0)
        {
            bgImage = new Sprite("../Assets/Start.jpg");
            //bgOverlay = new Sprite("../Assets/clearOverlay.png");
        }
        else
        {
            bgImage = new Sprite("../Assets/SkyBackground.jpg");
            /*if(curLevel == 1)
                bgOverlay = new Sprite("../Assets/wasdOverlay.png");
            else if(curLevel == 2)
                bgOverlay = new Sprite("../Assets/rOverlay.png");
            else if(curLevel == 3)
                bgOverlay = new Sprite("../Assets/jOverlay.png");
            else if(curLevel == 4)
                bgOverlay = new Sprite("../Assets/jkOverlay.png");
            else if(curLevel == 5)
                bgOverlay = new Sprite("../Assets/klOverlay.png");
            else if(curLevel == 6)
                bgOverlay = new Sprite("../Assets/jklOverlay.png");
            else
                bgOverlay = new Sprite("../Assets/clearOverlay.png");*/
        }
    }
    
    public void resetPlayer(){
      
        player.x = playerStart.x;
        player.y = playerStart.y;
        r=true;
        y=true;
        b=true;
        updateBlockColors();
        player.reset();
        
    }
    @Override
    public void saveLevel(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void updateBlockColors(){
        //Updates the block colors only when a button is pressed
        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i) instanceof ColorBlock){
                ((ColorBlock)entities.get(i)).updateColor();
            }
        }
    }
        
}
