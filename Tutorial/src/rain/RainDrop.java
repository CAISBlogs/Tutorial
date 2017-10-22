package rain;

import java.awt.Graphics;

import main.Main;

public class RainDrop {
	
	static final float gravity = 0.5f;
	

	float x, y;
	float xVel = 0;
	float yVel = 0;
	float size;
	public boolean isDead = false;

	public RainDrop(float x, float y, float xVel, float yVel, float size) {
		super();
		this.x = x;
		this.y = y;
		this.xVel = xVel;
		this.yVel = yVel;
		this.size = size;
	}
	
	public void update() {
		this.x += xVel;
		this.y += yVel; 
		this.yVel += gravity;
		
		if(y > Main.height()) {
			isDead = true;
		}
		
	}
	
	public void render(Graphics screen) {
		screen.fillRect((int) (x-(size/2)), (int) (y-(size*3)), (int)size, (int) (size*3));
	}
	
}
