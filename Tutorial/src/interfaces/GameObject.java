package interfaces;

import java.awt.Graphics;

import com.tutorial1.core.Input;

public interface GameObject {

	public void start();
	public void update(Input input);
	public void render(Graphics screen);
}
