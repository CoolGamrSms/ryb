package com.RYB.Utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	public boolean keys[] = new boolean[120];
	public static boolean right, left, up, down, space, KEY_J, KEY_K, KEY_L, KEY_P,KEY_R,enter;
	
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
                space = keys[KeyEvent.VK_SPACE];
                KEY_J = keys[KeyEvent.VK_J];
                KEY_K = keys[KeyEvent.VK_K];
                KEY_L = keys[KeyEvent.VK_L];
                KEY_P = keys[KeyEvent.VK_P];
                KEY_R = keys[KeyEvent.VK_R];
                enter = keys[KeyEvent.VK_ENTER];
                
	}
	
	public void keyPressed(KeyEvent e) {	
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

}
