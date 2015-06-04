package game;
import java.awt.image.BufferedImage;


public class SpriteSheet {
	
	private static final int SIZE = 32;
	
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height){
		BufferedImage img = image.getSubimage((col * 16) - 16, (row * 24) - 24, width, height);
		return img;
	}
	
	

}
