package game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import buzz.conn.entities.User;
import buzz.conn.model.CurrentDate;


public class Player extends GameObject{

	private CurrentDate d = new CurrentDate();

	private boolean movingRight = false;
	private boolean movingLeft = false;
	private boolean onGround = false;

	private int destinationX;

	private float gravity = 0.5f;  

	private long timer = System.currentTimeMillis();

	private BufferedImage walkRight, walkRight1, walkRight2, walkLeft, walkLeft1, walkLeft2, idle, jumpRight, jumpRight1, jumpRight2, jumpRight3, jumpRight4, jumpLeft, jumpLeft1, jumpLeft2, jumpLeft3, jumpLeft4;

	private int rightWalkCount = 0;
	private int leftWalkCount = 0;
	
	private int rightJumpCount = 0;
	private int leftJumpCount = 0;
	
	private int colorCount = 0;

	private Game game;

	private User user;

	public Player(double x, double y, int width, int height, double scale, Game game){
		super(x, y, width, height, scale);

		this.setVelY(2.0);

		this.game = game;

		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

		walkRight1 = ss.grabImage(1, 1, width, height);
		walkRight2 = ss.grabImage(2, 1, width, height);
		walkLeft1 = ss.grabImage(3, 1, width, height);
		walkLeft2 = ss.grabImage(4, 1, width, height);
		
		idle = ss.grabImage(5, 1, width, height);

		jumpRight1 = ss.grabImage(6, 1, width, height);
		jumpRight2 = ss.grabImage(7, 1, width, height);
		jumpRight3 = ss.grabImage(8, 1, width, height);
		jumpRight4 = ss.grabImage(9, 1, width, height);

		jumpLeft1 = ss.grabImage(10, 1, width, height);
		jumpLeft2 = ss.grabImage(11, 1, width, height);
		jumpLeft3 = ss.grabImage(12, 1, width, height);
		jumpLeft4 = ss.grabImage(13, 1, width, height);

		walkRight = walkRight1;
		walkLeft = walkLeft1;

		jumpLeft = jumpLeft1;
		jumpRight = jumpRight1;

	}

	public void tick(){

		if(movingRight && onGround){
			x+=2;
			if(System.currentTimeMillis() - timer > 200){
				timer+=200;
				switchWalkRight();
			}
		}
		if(movingLeft && onGround){
			x-=2;
			if(System.currentTimeMillis() - timer > 200){
				timer+=200;
				switchWalkLeft();
			}
		}
		
		if(movingRight && !onGround){
			x+=2;
			if(System.currentTimeMillis() - timer > 20){
				timer+=200;
				switchJumpRight();
			}
		}
		if(movingLeft && !onGround){
			x-=2;
			if(System.currentTimeMillis() - timer > 20){
				timer+=200;
				switchJumpLeft();
			}
		}

		if(movingLeft && (getX() <= destinationX) && destinationX != 0){
			movingLeft = false;
			destinationX = 0;
		}

		if(movingRight && (getX() >= destinationX) && destinationX != 0){
			movingRight = false;
			destinationX = 0;
		}

		x += velX * 0.75f;      		// Apply horizontal velocity to X position
		y += velY * 0.75f;      		// Apply vertical velocity to X position
		velY += gravity * 0.75f;  		// Apply gravity to vertical velocity

		if(getY() > (game.getWorld().getY() - game.getWorld().getHeight() - 32)){
			y = (game.getWorld().getY() - game.getWorld().getHeight() - 32);
			velY = 0.0;
			onGround = true;
		}

	}

	public void render(Graphics g){
		if(!onGround){
			if(movingLeft){
				g.drawImage(jumpLeft, (int) x, (int) y, (int)(width*scale), (int)(height*scale), null);
			} else if (movingRight){
				g.drawImage(jumpRight, (int) x, (int) y, (int)(width*scale), (int)(height*scale), null);
			} else {
				g.drawImage(idle, (int) x, (int) y, getWidth(), getHeight(), null);
			}
		} else if(movingRight && movingLeft){
			g.drawImage(idle, (int) x, (int) y, (int)(width*scale), (int)(height*scale), null);
		} else if(movingRight){
			g.drawImage(walkRight, (int) x, (int) y, (int)(width*scale), (int)(height*scale), null);
		} else if(movingLeft){
			g.drawImage(walkLeft, (int) x, (int) y, (int)(width*scale), (int)(height*scale), null);
		} else {
			g.drawImage(idle, (int) x, (int) y, getWidth(), getHeight(), null);
		}

		//collision testing rectangle
		//g.drawRect((int) x, (int) y, getWidth(), getHeight());

		//username
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawString(System.getProperty("user.name"), (int) x + 10, (int) y + 12);
	}
	
	private Color getColor(){
		switch(colorCount){
		case 0: colorCount++; return Color.RED;
		case 1: colorCount++; return Color.BLUE;
		case 2: colorCount++; return Color.YELLOW;
		case 3: colorCount++; return Color.GREEN;
		case 4: colorCount++; return Color.CYAN;
		case 5: colorCount++; return Color.MAGENTA;
		case 6: colorCount++; return Color.PINK;
		case 7: colorCount = 0; return Color.ORANGE;
		default: return null;
		}
	}

	public void switchWalkRight(){
		switch(rightWalkCount){
		case 0:
			walkRight = walkRight1;
			rightWalkCount++;
			break;
		case 1:
			walkRight = walkRight2;
			rightWalkCount--;
			break;
		}
	}

	public void switchJumpRight(){
		switch(rightJumpCount){
		case 0:
			jumpRight = jumpRight1;
			rightJumpCount++;
			break;
		case 1:
			jumpRight = jumpRight2;
			rightJumpCount++;
			break;
		case 2:
			jumpRight = jumpRight3;
			rightJumpCount++;
			break;
		case 3:
			jumpRight = jumpRight4;
			rightJumpCount = 0;
			break;
		}
	}

	public void moveTo(int x){
		this.destinationX = x;
		if(getX() > destinationX){
			movingLeft = true;
		}
		if(getX() < destinationX){
			movingRight = true;
		}
	}

	public void switchWalkLeft(){
		switch(leftWalkCount){
		case 0:
			walkLeft = walkLeft1;
			leftWalkCount++;
			break;
		case 1:
			walkLeft = walkLeft2;
			leftWalkCount--;
			break;
		}
	}
	
	public void switchJumpLeft(){
		switch(leftJumpCount){
		case 0:
			jumpLeft = jumpLeft1;
			leftJumpCount++;
			break;
		case 1:
			jumpLeft = jumpLeft2;
			leftJumpCount++;
			break;
		case 2:
			jumpLeft = jumpLeft3;
			leftJumpCount++;
			break;
		case 3:
			jumpLeft = jumpLeft4;
			leftJumpCount = 0;
			break;
		}
	}

	public void startJump(){
		if(onGround){
			velY = -8.0f;
			onGround = false;
		}
	}

	public void stopJump(){
		if(velY < -4.0){
			velY = -4.0;
		}
	}

	public void setMovingLeft(boolean b){
		this.resetTimer();
		this.movingLeft = b;
	}

	public void setMovingRight(boolean b){
		this.resetTimer();
		this.movingRight = b;
	}

	public boolean isMovingLeft(){
		return movingLeft;
	}

	public boolean isMovingRight(){
		return movingRight;
	}

	public void resetTimer(){
		timer = System.currentTimeMillis();
	}

	public int getPlayerID(){
		return user.getUserID();
	}

	private void display(String s){
		System.out.println(d.now() + " Game - " + s);
	}

	private void displayError(String s){
		System.err.println(d.now() + " Game - " + s);
	}
}
