package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyboardListener implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Key typed: "+e.getKeyCode());
		if(e.getKeyCode() == 39){ //højre
		}else if(e.getKeyCode() == 37){ //venstre
		}else if(e.getKeyCode() == 38){ //op
		}else if(e.getKeyCode() == 40){ //ned
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Key pressed: "+e.getKeyCode());
		if(e.getKeyCode() == 39){ //højre
		}else if(e.getKeyCode() == 37){ //venstre
		}else if(e.getKeyCode() == 38){ //op
		}else if(e.getKeyCode() == 40){ //ned
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		System.out.println("Key released: "+e.getKeyCode());
		if(e.getKeyCode() == 39){ //højre
		}else if(e.getKeyCode() == 37){ //venstre
		}else if(e.getKeyCode() == 38){ //op
		}else if(e.getKeyCode() == 40){ //ned
		}
	}
}