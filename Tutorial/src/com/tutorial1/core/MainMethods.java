package com.tutorial1.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class MainMethods {

	public static Core window;
	public static final String bg_rain = "res/rainyday.jpg";
	public static final String bg_night = "res/nightSkyB+W.jpg";
	public static final String bg_alley = "res/alley.jpg";
	
	/**
	 * A set of resizing hints, used when calling the setBackground function on an image.
	 * 
	 * @author matt
	 *
	 */
	public static enum Hints{
		/** Use image as is, with no resizing. If the image is smaller than the screen the default background color will fill the remaining space */
		none,
		/** Fill the screen. If needed the image may be stretched to achieve this */
		stretch,
		/** Fill the screen. This is achieved by scaling and cropping so the most of the image is seen */
		center,
		/** Tiles the image over the screen, by default 10 columns are created - rows are determined by aspect ratio. The 'setTileVariables(rows r, columns c)' method may be used to change this */
		tile;
		
		static final int defaultCols = 10;
		int tileRows = -1;
		int tileColumns = -1;
		boolean smartTile = true;
		
		/**
		 * Sets the variables for the 'tile' hints. If a value is set to zero or below it will be allocated proceduarally (if both are then the column value is set at 10)
		 * 
		 * @param rows the rows (Horizontal tiling) in each tiling
		 * @param columns the columns (Vertical tiling) in each tiling
		 */
		Hints setTileVariables(int rows, int columns){
			this.tileRows = rows;
			this.tileColumns = columns;
			
			if(rows < 1 || columns < 1){
				this.smartTile = true;
			} else {
				this.smartTile = false;
			}
			return this;
		}

		public int getTileColumns(Image baseImage) {
			if(!smartTile || this.tileColumns > 0){
				return this.tileColumns;
			} else {
				if(this.tileRows > 0){
					return Math.max(1, tileRows * Math.round(baseImage.getWidth(null)/(float)baseImage.getHeight(null)));
				} else {
					return defaultCols;
				}
			}
		}
		
		public int getTileRows(Image baseImage) {
			if(!smartTile || this.tileRows > 0){
				return this.tileRows;
			} else {
				int cols = getTileColumns(baseImage);
				return Math.max(1, cols * Math.round(baseImage.getHeight(null)/(float)baseImage.getWidth(null)));
				
			}
		}
		
	}
	
	/**
	 * Sets the background image to the specified image using the given hint to detetmine resizing.
	 * 
	 * @param img the BufferedImage to set as the background for the window, null will remove any image
	 * @param hint the Hints enum to decide how to resize the image, null for no processing
	 */
	public void setBackground(BufferedImage img, Hints hint){
		
		if(hint == null){
			hint = Hints.none;
		}
		if(img != null){
			switch(hint){
			
			case center:
				if(img.getWidth() > img.getHeight()){
					img = img.getSubimage((int) ((img.getWidth() - img.getHeight()) /2f), 0, img.getHeight(), img.getHeight());
				} else {
					img = img.getSubimage(0, (int) ((img.getHeight() - img.getWidth()) /2f), img.getWidth(), img.getWidth());
				}
			case stretch:
				Image scaled = img.getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_SMOOTH);
				img = bufferImage(scaled);
				break;
			
			case tile:
				BufferedImage newImage = new BufferedImage(window.getWidth(),window.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = newImage.getGraphics();
				int cols = hint.getTileColumns(img);
				int rows = hint.getTileRows(img);
				BufferedImage subImage = bufferImage(img.getScaledInstance((int) (window.getWidth()/(float)cols)+1, (int) (window.getHeight()/(float)rows), Image.SCALE_SMOOTH));
				
				for(int i = 0; i < cols; i++){
					for(int j = 0; j < rows; j++){
						g.drawImage(subImage, (int)(i*(window.getWidth()/(float)cols)), (int) (j*(window.getHeight()/(float)rows)), null);
					}
				}
				img = newImage;
				g.dispose();
				break;
				
				
			case none: default:
				break;
			}
		}
		
		window.setBackground(img);
	}
	
	public void clearImage(){
		setBackground((BufferedImage)null, null);
	}
	
	/**
	 * Safe way to cast an Image to a BufferedImage
	 * 
	 * @param i input Image type image
	 * @return a BufferedImage
	 */
	private BufferedImage bufferImage(Image i){
		BufferedImage castImage = new BufferedImage(i.getWidth(null),i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics castGraphics = castImage.getGraphics();
		castGraphics.drawImage(i, 0, 0, null);
		castGraphics.dispose();
		return castImage;
	}
	
	
	/**
	 * Sets the background image to the specified image using the given hint to detetmine resizing.
	 * 
	 * @param img the String location of the background Image requested for the window, if null or the file is not available this function will do nothing;
	 * @param hint the Hints enum to decide how to resize the image, null for no processing
	 */
	public void setBackground(String s, Hints hint){
		BufferedImage img;
		try {
			img = ImageIO.read(new File(s));
		} catch (IOException e) {
			return;
		}
		setBackground(img, hint);
	}
	
	/**
	 * Sets the default background color for the window, this has a lower priority than any image. Null will set the color to Color.black.
	 * @param c a Color
	 */
	public void setBackground(Color c){
		window.setBackground(c);
	}
	
	/**
	 * Current screen width
	 * @return the width of the screen
	 */
	public static int width(){
		return window.getWidth();
	}

	/**
	 * Current screen height
	 * @return the height of the screen
	 */
	public static int height(){
		return window.getHeight();
	}
	
}
