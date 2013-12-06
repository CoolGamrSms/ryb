package com.RYB;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.RYB.Level.LevelBuilder;
import com.RYB.Level.LevelWorld;
import com.RYB.Objects.Dynamic;
import com.RYB.Objects.Player;
import com.RYB.Utils.Console;
import com.RYB.Utils.IOUtils;
import com.RYB.Utils.Keyboard;
import com.RYB.Utils.Mouse;
import com.RYB.Utils.Vector2f;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Kyle
 */
public class Display extends Canvas implements Runnable{

    public static int width = 800;
    public static int height = 32 * 19;
    private Dimension size;
    
    private String title;
    private JFrame frame;
    private DisplayWorld world;
    
    private Thread gameThread;
    private boolean running = false;
    private int fps = 0;
    
    public static Keyboard input;
    public static Mouse mouse;
    public static Console console;
    
    public Display(String title){
        size = new Dimension(width, height);
        this.title = title;
        
        world = new World();
        input = new Keyboard();
        addKeyListener(input);
        
        mouse = new Mouse();
        addMouseMotionListener(mouse);
        
        console = new Console();
        console.enable(true);
        
        frame = new JFrame(this.title);
        frame.setResizable(false);
        frame.add(this);
        frame.setJMenuBar(createWorldMenu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    //Frame Functions
    public Dimension getSize(){
        return size;
    }
    private JMenuBar createWorldMenu(){
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem levelEditor = new JMenuItem("Level Editor");
        JMenuItem debugMode = new JMenuItem("Debug Mode");
        
        //Mnemonics
        file.setMnemonic(KeyEvent.VK_F);
        levelEditor.setMnemonic(KeyEvent.VK_L);
        debugMode.setMnemonic(KeyEvent.VK_D);
        
        //Adding Components
        file.add(levelEditor);
        file.add(debugMode);
        menu.add(file);
        
        //Listeners
        levelEditor.addActionListener(new ActionListener(){ //ActionListener for GameWorld Option
            @Override
            public void actionPerformed(ActionEvent e){
                world.reset();
                world = new LevelWorld(Display.this);
                frame.setJMenuBar(createLevelEditorMenu());
                frame.pack();
            }
        });
        debugMode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //Frame and Panel Setup
                final JFrame frame = new JFrame("Options");
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 2));
                
                    //Declare
                    JLabel lblLevel,
                           lblMovement,
                           lblGravity;
                    
                    final JTextField level,
                                     movement,
                                     gravity;
                    
                    JButton exit;
                    
                    //Initialize
                    lblLevel = new JLabel("Level: ");
                    level = new JTextField("1");
                    
                    lblMovement = new JLabel("Movement: (Fast / Normal / Slow) ");
                    movement = new JTextField("Normal");
                    
                    lblGravity = new JLabel("Gravity: (High / Normal / Low");
                    gravity = new JTextField("Normal");
                    
                    exit = new JButton("Close");
                    
                //Add
                panel.add(lblLevel);
                panel.add(level);
                panel.add(lblMovement);
                panel.add(movement);
                panel.add(lblGravity);
                panel.add(gravity);
                panel.add(exit);
                
                final ActionListener closeAction = new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                       World tmpWorld = (World) world;
                       Player player = tmpWorld.getPlayer();
                       
                       
                       //Level
                       tmpWorld.setLevel(Integer.parseInt(level.getText().trim()));
                       
                       //Character speed
                       switch(movement.getText().toLowerCase().trim()){
                           case "fast":
                               player.setConstraintVelocity(new Vector2f(15.0f, player.getMaxConstraintVelocity().y));
                               Dynamic.MOVEMENT = new Vector2f(3.0f,0);                               
                               break;
                           case "normal":
                               tmpWorld.getPlayer().setConstraintVelocity(new Vector2f(1.6f, player.getMaxConstraintVelocity().y));                               
                               Dynamic.MOVEMENT = new Vector2f(1.0f,0);                               
                               break;
                           case "slow":
                               tmpWorld.getPlayer().setConstraintVelocity(new Vector2f(0.8f, player.getMaxConstraintVelocity().y));                               
                               Dynamic.MOVEMENT = new Vector2f(0.05f,0);                               
                               break;
                           default:
                               break;
                       }
                       
                       //Gravity
                       switch(gravity.getText().toLowerCase().trim()){
                           case "high":
                               player.setConstraintVelocity(new Vector2f(player.getMaxConstraintVelocity().x, 10.0f));
                               player.setAcceleration(new Vector2f(0, 3.0f));                               
                               break;
                           case "normal":
                               player.setConstraintVelocity(new Vector2f(player.getMaxConstraintVelocity().x, 1.6f));
                               //player.setAcceleration(new Vector2f(0, 0.013f));                               
                               break;
                           case "low":
                               player.setConstraintVelocity(new Vector2f(player.getMaxConstraintVelocity().x, 3.0f));
                               player.setAcceleration(new Vector2f(0, 0.0001f));  
                               break;
                           default:
                               break;
                       }                       
                   }
                    
                };
                
                //Frame Closing Listener
                exit.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            closeAction.actionPerformed(null);
                            frame.dispose();
                        }
                    });
                frame.getRootPane().setDefaultButton(exit);                
                frame.addWindowListener(new WindowAdapter(){
                   @Override
                   public void windowClosing(WindowEvent windowEvent){
                   }
                });
                
                //Frame Finalize
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
                frame.setResizable(false);
                frame.setAlwaysOnTop(true);
            }
        });
        
        return menu;
    }
    private JMenuBar createLevelEditorMenu(){
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem gameWorld = new JMenuItem("Game World");
        JMenuItem save = new JMenuItem("Save...");
        JMenuItem testLevel = new JMenuItem("Test Level");
        JMenu addItem = new JMenu("Add...");
        JMenuItem playerStart = new JMenuItem("Player Start");
        JMenuItem levelGoal = new JMenuItem("Level Goal");
        
        //Mnemonics
        file.setMnemonic(KeyEvent.VK_F);
        gameWorld.setMnemonic(KeyEvent.VK_G);
        
        //Adding Components
        file.add(gameWorld);
        file.addSeparator();
        file.add(save);
        file.add(testLevel);
        file.add(addItem);
        addItem.add(playerStart);
        addItem.add(levelGoal);
        menu.add(file);
        
        //Listeners
        gameWorld.addActionListener(new ActionListener(){ //ActionListener for GameWorld Option
            @Override
            public void actionPerformed(ActionEvent e){
                world.reset();
                world = new World();
                frame.setJMenuBar(createWorldMenu());
                frame.pack();
            }
        });
        
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = IOUtils.browseToSaveMap();
                if (f != null){
                    world.saveLevel(f);
                }
            }
            
        });
        
        testLevel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //Test Level...
            }
            
        });
        
        playerStart.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //Allow user to enter a player start location
            }
            
        });
        
        levelGoal.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //Allow user to enter a level goal location
            }
            
        });
        
        return menu;        
    }
    
    private synchronized void start(){
        if(running)
            return;
        running = true;
        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }
    private synchronized void stop(){
        if(!running) return;
		running = false;
        try{
        	gameThread.join();
	}catch(InterruptedException e){
		e.printStackTrace();
	}
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long lastTimer = System.currentTimeMillis();
	double ns = 1000000000.0 / 240.0;
	double delta = 0;
	int frames = 0;
		
	while(running){
            long curTime = System.nanoTime();
            delta = (curTime - lastTime) / ns;            

			
            if(delta >= 1){
                lastTime = curTime;
		update();
                frames++;
                render();
            }
			
            while(System.currentTimeMillis() - lastTimer > 1000){
		lastTimer+=1000;
		fps = frames;
		//System.out.println(fps);
		frames = 0;
            }
        }
    }
    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
       
        world.render(g);
        
         console.render(g);
        
        g.setColor(Color.white);
        frame.setTitle("RYB Version 0.1 " + Integer.toString(fps) + " fps");
                
        g.dispose();
        bs.show();
        
    }
    private void update(){
        world.update();
        input.update();
    }
    
    
    public static void main(String[] args){
        Display display = new Display("RYB Version 0.1");
        display.start();
    }
}
