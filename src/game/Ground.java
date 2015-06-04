package game;

public class Ground extends GameObject {
	
	public Ground(double x, double y, int width, int height, double scale) {
		super(x, y, width, height, scale);
		System.out.println(y);
	}

}
