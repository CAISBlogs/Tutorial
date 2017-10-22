package com.tutorial1.core;

public class Time {
	
	private static double delta = 0;
	private static double elapsed = 0;
	private static boolean pause = false;
	
	public static void setDelta(double delta){
		if(!pause){
			Time.delta = delta;
			Time.elapsed += delta;
		}
	}
	public static double getDelta(){
		return delta;
	}
	public static double getElapsed(){
		return elapsed;
	}
	public static void pause(){
		Time.pause = !Time.pause;
	}

}
