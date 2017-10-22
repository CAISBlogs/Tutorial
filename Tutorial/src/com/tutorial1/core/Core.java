package com.tutorial1.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import main.Main;

/**
 * Home is the container class for the whole program. The main method calls one instance of home and home sorts the rest. It contains the window class responsible for display
 * and runs the primary thread of the entire program
 * @author matt
 *
 */
public class Core extends Canvas implements Runnable{
	
	//Set true to have resiseable screen and extra features; false for release.
	public boolean dev = true;
	public boolean devDisplay = false;
	
	private static final long serialVersionUID = 5995997190925094718L;
	
	
	private Thread tMain;
	private boolean running = false;
	private JFrame window;
	private Main display;
	private Input input;
	
	private float FPS = 60f;
	private float tickRate = 60f;
	private boolean showFPS = false;
	private float actualFPS = FPS;
	
	private Color backgroundColor = Color.BLACK;
	private Image backgroundImage;
	
	/**
	 * Home method called at start
	 */
	public Core(){
		window = new JFrame("Tutorials");
		window.setMinimumSize(new Dimension(1024, 1024));
		window.setPreferredSize(new Dimension(1024,1024));
		window.getContentPane().setBackground(Color.black);
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
		window.setUndecorated(false);
		window.setResizable(true);
		window.setLocationRelativeTo(null);
		
		window.add(this);

		window.pack();
		window.setVisible(true);
		
		input = new Input();
		this.addKeyListener(input);
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		this.addMouseWheelListener(input);
		
		display = new Main();
		Main.window = this;
		
		display.start();
		start();
	}
	
	/**
	 * start and stop from runnable, do not ever need to be touched used to instaciate run method
	 */
	public synchronized void start(){
		
		tMain = new Thread(this);
		tMain.start();
		running = true;
		
	}	
	public synchronized void stop(){
		
		try{
			tMain.join();
			running = false;
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Sets the new maximum FPS for the updater, does not guarantee the rate specified. All values of zero or below are ignored.
	 * 
	 * @param newFPS
	 */
	public void setFPS(float newFPS){
		if(newFPS > 0){
			this.FPS = newFPS;
		}
	}
	
	/**
	 * Sets the new maximum tickrate for the updater, does not guarantee the rate specified. All values of zero or below are ignored.
	 * 
	 * @param newTickRate
	 */
	public void setTickRate(float newTickRate){
		if(newTickRate > 0){
			this.tickRate = newTickRate;
		}
	}
	
	/**
	 * run method, while thread is running will call tick and ~60 times per second call the render method
	 */
	public void run() {
		
		this.requestFocus();
		double lastTick = System.currentTimeMillis();
		double lastTickTime = System.currentTimeMillis();
		
		double lastRender = System.currentTimeMillis();
		double last = System.currentTimeMillis();
		int frames = 0;
		
		while(running){
			long now = System.currentTimeMillis();
			if(now > 1000f/this.tickRate + lastTick){

				Time.setDelta(System.currentTimeMillis() - lastTickTime);
				lastTickTime = System.currentTimeMillis();

				tick();
				lastTick = (long) (lastTick + (1000f/this.tickRate));
			}

			
			if(now > 1000f/this.FPS + lastRender){
				render();
				lastRender = (long) (lastRender + (1000f/this.FPS));
				frames++;
			}
			
			if(now >= last + 1000){
				last = now;
				if(showFPS){
					System.out.println(frames);
				}
				actualFPS = frames;
				frames = 0;
			}
			
		}
		stop();
	}
	
	public void showFPS(){
		showFPS = !showFPS;
	}
	
	public float getFPS(){
		return this.actualFPS;
	}
	
	/**
	 * Tick, use for anything that needs to be incrememted once per cycle
	 */
	private void tick(){
		
		
		
		display.update(input);
		
		input.update();
	}
		
	public void setBackground(Image img) {
		this.backgroundImage = img;
		
	}
	
	
	public void setBackground(Color c) {
		super.setBackground(c);
		if(c != null){
			this.backgroundColor = c;
		} else {
			this.backgroundColor = Color.black;
		}
	}
	
	/**
	 * Render is used to draw and is permanently linked to the window method, but can invoke the render state of other classes when called. Will loop ~60 per second through run method
	 */
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(backgroundColor);
		g.fillRect(0, 0, window.getWidth(), window.getHeight());
		if(backgroundImage != null){
			g.drawImage(backgroundImage, 0, 0, null);
		}
		
		g.setColor(Color.white);
		//ALL GRAPHICS METHODS MUST OCCUR IN THIS SPACE [

		display.render(g);
		
		// ] TO HERE
		
		g.dispose();
		bs.show();
		
	}
	
	/**
	 * Will request an empty graphics object used for fontmetrics only
	 * @return Graphics can be used to call graphics specific funtions but will not draw as part of the render method
	 */
	
	
	
	
	public static void main(String args[]){
		new Core();
	}
	




}
