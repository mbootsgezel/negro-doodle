package game;

public class GameObject {
	
	protected double x;
	protected double y;
	
	protected int width;
	protected int height;
	
	protected double scale;
	
	protected double velX;
	protected double velY;
	
	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public GameObject(double x, double y, int width, int height, double scale) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.scale = scale;
	}
	
	public double getScale(){
		return scale;
	}
	
	public void setScale(double scale){
		this.scale = scale;
	}
	
	public int getActualWidth(){
		return width;
	}

	public int getWidth() {
		return (int) (width*scale);
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getActualHeight(){
		return height;
	}

	public int getHeight() {
		return (int) (height*scale);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

}
