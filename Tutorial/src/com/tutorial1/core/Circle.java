package com.tutorial1.core;

import java.awt.Graphics;

public class Circle {
	public Circle(int newx, int newy, int newr) {
		x = newx;
		y = newy;
		r = newr;
	}
	
	private int x;
	private int y;
	private int r;
	
	public void render(Graphics screen) {
		
		int x = this.x;
		int y = this.y;
		int r = this.r;
		
		screen.fillOval(x - r , y - r, r * 2, r * 2);
	}
	
	public int getx() {
		return this.x;
	}
	public int gety() {
		return this.y;
	}
	public int getr() {
		return this.r;
	}
	public void setx(int x) {
		this.x = x;
	}
	public void setr(int r) {
		this.r = r;
	}
	public void sety(int y) {
		this.y = y;
	}
	
	
}
