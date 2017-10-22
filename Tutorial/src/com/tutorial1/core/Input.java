package com.tutorial1.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	public static final int space = KeyEvent.VK_SPACE;
	public static final int left = KeyEvent.VK_LEFT;
	public static final int right = KeyEvent.VK_RIGHT;
	public static final int up = KeyEvent.VK_UP;
	public static final int down = KeyEvent.VK_DOWN;
	public static final int esc = KeyEvent.VK_ESCAPE;
	public static final int del = KeyEvent.VK_DELETE;
	
	
	
	/** A stand in for 'left' or 'w' */
	public static final int[] game_left = new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_A};
	/** A stand in for 'left' or 'w' */
	public static final int[] game_right = new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_D};
	/** A stand in for 'left' or 'w' */
	public static final int[] game_up = new int[]{KeyEvent.VK_UP, KeyEvent.VK_W};
	/** A stand in for 'left' or 'w' */
	public static final int[] game_down = new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_S};
	
	
	private boolean[] last = new boolean[256];
	private boolean[] now = new boolean[256];
	
	
	public static final int LMB = 1;
	public static final int MMB = 2;
	public static final int RMB = 3;
	
	private boolean[] lastMouse = new boolean[16];
	private boolean[] nowMouse = new boolean[16];
	private int[] mousePosition = new int[2];
	private int mouseWheelDelta = 0;
	private int mouseWheelMovement = 0;
	
	public void update(){
		
		
		last = new boolean[now.length];
		for(int i = 0; i < now.length; i++){
			last[i] = now[i];
		
		}
		
		mouseWheelMovement = 0;
		lastMouse = new boolean[nowMouse.length];
		for(int i = 0; i < nowMouse.length; i++){
			lastMouse[i] = nowMouse[i];
		
		}
		
	}

	// =========== Keyboard INPUT ===============================  //
	
	public void keyPressed(KeyEvent k) {
		now[k.getKeyCode()] = true;
		
	}

	public void keyReleased(KeyEvent k) {
		now[k.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent k) {}
	
	// ==== [ METHODS ] =====>
	
	public boolean getKeyDown(int keycode){
		
		return now[keycode];
	}
	public boolean getKeyDown(char key){
		
		return getKeyDown((int) Character.toUpperCase(key));
	}
	/**
	 * Returns true if any of the keys requested are down, else false
	 * NOTE: This is NOT the inverse of getKeyUp([]), as any key being down is considered true
	 * 
	 * @param keycodes, an array of VK_ keycodes.
	 * @return boolean if ANY keys are down
	 */
	public boolean getKeyDown(int[] keycodes){
		
		for(int keycode:keycodes){
			if(getKeyDown(keycode)){
				return true;
			}
		}
		return false;
	}
	
	public boolean getKeyUp(int keycode){
		
		return !now[keycode];
	}
	public boolean getKeyUp(char key){
		
		return getKeyUp((int) Character.toUpperCase(key));
	}
	/**
	 * Returns true if any of the keys requested are up, else false.
	 * NOTE: This is NOT the inverse of getKeyDown([]), as any key being up is considered true
	 * 
	 * @param keycodes, an array of VK_ keycodes.
	 * @return boolean if ANY keys are up
	 */
	public boolean getKeyUp(int[] keycodes){
		
		for(int keycode:keycodes){
			if(getKeyUp(keycode)){
				return true;
			}
		}
		return false;
	}
	
	public boolean getKeyPressed(int keycode){
		
		return now[keycode] && !last[keycode];
	}
	public boolean getKeyPressed(char key){
		
		return getKeyPressed((int) Character.toUpperCase(key));
	}
	/**
	 * Returns true if any of the keys requested have been pressed this update cycle, else false
	 * NOTE: This is NOT the inverse of getKeyReleased([]), as any key being pressed is considered true
	 * 
	 * @param keycodes, an array of VK_ keycodes.
	 * @return boolean if ANY keys are pressed
	 */
	public boolean getKeyPressed(int[] keycodes){
		
		for(int keycode:keycodes){
			if(getKeyPressed(keycode)){
				return true;
			}
		}
		return false;
	}
	
	
	public boolean getKeyReleased(int keycode){
		
		return !now[keycode] && last[keycode];
	}
	public boolean getKeyReleased(char key){
		
		return getKeyReleased((int) Character.toUpperCase(key));
	}
	/**
	 * Returns true if any of the keys requested have been released this update cycle, else false
	 * NOTE: This is NOT the inverse of getKeyPressed([]), as any key being released is considered true
	 * 
	 * @param keycodes, an array of VK_ keycodes.
	 * @return boolean if ANY keys are released
	 */
	public boolean getKeyReleased(int[] keycodes){
		
		for(int keycode:keycodes){
			if(getKeyReleased(keycode)){
				return true;
			}
		}
		return false;
	}
	
	
	// =========== MOUSE INPUT ===============================  //

	
	public int[] getMousePosition(){
		return mousePosition;
	}
	
	public boolean isButtonDown(int button){
		return nowMouse[button];
	}
	
	public boolean isButtonUp(int button){
		return !nowMouse[button];
	}
	
	public boolean isButtonPressed(int button){
		return nowMouse[button] && !lastMouse[button];
	}
	
	public boolean isButtonReleased(int button){
		return !nowMouse[button] && lastMouse[button];
	}
	
	public int getMouseWheelMovement(){
		return mouseWheelMovement;
	}
	public int getMouseWheelDelta(){
		return mouseWheelDelta;
	}
	
	public void mouseClicked(MouseEvent m) {}


	public void mouseEntered(MouseEvent m) {}


	public void mouseExited(MouseEvent m) {}


	public void mousePressed(MouseEvent m) {
		nowMouse[m.getButton()] = true;
	}


	public void mouseReleased(MouseEvent m) {
		nowMouse[m.getButton()] = false;
	}
	
	
	public void mouseWheelMoved(MouseWheelEvent m) {
		
		mouseWheelMovement +=  m.getWheelRotation();
		mouseWheelDelta += m.getWheelRotation();
	}
	
	
	public void mouseDragged(MouseEvent m) {
		
		mousePosition[0] = m.getX();
		mousePosition[1] = m.getY();
		
	}
	
	public void mouseMoved(MouseEvent m) {
		
		mousePosition[0] = m.getX();
		mousePosition[1] = m.getY();
	}
	
}
