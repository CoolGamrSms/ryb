package com.RYB;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.RYB.Level.LevelWorld;
import com.RYB.Utils.Console;
import com.RYB.Utils.Keyboard;
import com.RYB.Utils.Mouse;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
    private World world;
    
    private Thread gameThread;
    private boolean running = false;
    private int fps = 0;
    
    public static Keyboard input;
    public static Mouse mouse;
    public static Console console;
    
    public Display(String title){
        size = new Dimension(width, height);
        this.title = title;
        
        world = new GameWorld();
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
    public Dimension getSize(){
        return size;
    }
    private JMenuBar createWorldMenu(){
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem levelEditor = new JMenuItem("Level Editor");
        
        //Mnemonics
        file.setMnemonic(KeyEvent.VK_F);
        levelEditor.setMnemonic(KeyEvent.VK_L);
        
        //Adding Components
        file.add(levelEditor);
        menu.add(file);
        
        //Listeners
        MenuListener listener = new MenuListener();
        levelEditor.addActionListener(listener);
        
        return menu;
    }
    private JMenuBar createLevelEditorMenu(){
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem gameWorld = new JMenuItem("Game World");
        
        //Mnemonics
        file.setMnemonic(KeyEvent.VK_F);
        gameWorld.setMnemonic(KeyEvent.VK_G);
        
        //Adding Components
        file.add(gameWorld);
        menu.add(file);
        
        //Listeners
        MenuListener listener = new MenuListener();
        gameWorld.addActionListener(listener);
        
        return menu;        
    }
    private class MenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (world instanceof GameWorld){
                world = new LevelWorld(Display.this);
            } 
            else if (world instanceof LevelWorld){
                world = new GameWorld();
            }
        }
        
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
            delta+= (curTime - lastTime) / ns;
            lastTime = curTime;
			
            if(delta >= 1){
		update();
		delta--;
            }
            render();
            frames++;
			
            while(System.currentTimeMillis() - lastTimer > 1000){
		lastTimer+=1000;
		fps = frames;
		System.out.println(fps);
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
