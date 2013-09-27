package com.RYB;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 *
 * @author Kyle
 */
public class Display extends Canvas implements Runnable{

    public static int width = 800;
    public static int height = width * 9 / 16;
    private Dimension size;
    
    private String title;
    
    private JFrame frame;
    
    private Thread gameThread;
    private boolean running = false;
    
    private World world;
    
    private int fps = 0;
    
    public Display(String title){
        size = new Dimension(width, height);
        this.title = title;
        
        world = new World();
        
        frame = new JFrame(title);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public Dimension getSize(){
        return size;
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
	double ns = 1000000000.0 / 60.0;
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
        
        g.setColor(Color.white);
	g.drawString(Integer.toString(fps) + " fps", 10, 25);
                
        g.dispose();
        bs.show();
        
    }
    
    private void update(){
        world.update();
    }
    
    
    public static void main(String[] args){
        Display display = new Display("RYB Version 0.1");
        display.start();
    }
}
