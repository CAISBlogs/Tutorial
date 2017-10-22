package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.tutorial1.core.Input;
import com.tutorial1.core.MainMethods;

import interfaces.GameObject;
import rain.RainDrop;

public class Main extends MainMethods implements GameObject{

	
	// Put variables here: //
	Random r = new Random();
	public static ArrayList<RainDrop> rain = new ArrayList<RainDrop>();
	public static ArrayList<RainDrop> deadDrops = new ArrayList<RainDrop>();
	float rps = 300;
	double lastDrop;
	// =================== //
	
	/** This method is called once at the beginning of the program
	 */
	public void start(){
		setBackground(bg_rain, Hints.center);
		lastDrop = System.currentTimeMillis();
	}
	
	/** This method is called each update cycle
	 * @param input, the input handler for the program
	 */
	public void update(Input input){
		
		if (System.currentTimeMillis() > lastDrop + (1000f / rps)) {
			int drops = (int) ((System.currentTimeMillis() - lastDrop) / (1000f/rps));
			for(int i = 0; i < drops ; i++){
				RainDrop rainDrop = new RainDrop(r.nextInt(window.getWidth()), 0, 0, 0, 2); 
				rain.add(rainDrop); 
			}
			lastDrop += drops * (1000f / rps);
			
		}	
		System.out.println(rain.size());
		
		for(RainDrop drop: rain) {
			drop.update();
			if(drop.isDead){
				deadDrops.add(drop);
			}
		}
		for(RainDrop drop: deadDrops) {
			rain.remove(drop);
		}
		deadDrops = new ArrayList<RainDrop>();
	}
	
	/** This method is called ~60 times per second, after each call the screen is wiped
	 * 
	 * @param screen a graphics object that allows direct drawing
	 */
	public void render(Graphics screen){

		screen.setColor(Color.blue);
		for(RainDrop drop: rain) {
			drop.render(screen);
		}
	}

}
