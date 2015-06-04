package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import buzz.conn.client.Client;
import buzz.conn.entities.User;
import buzz.conn.model.CurrentDate;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -4909548892769602446L;
	
	private CurrentDate d = new CurrentDate();
	
	private Client client;
	private User user;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public final String TITLE = "2D Test Game";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	private BufferedImage dirt = null;
	
	private Player p, p1, p2, p3, p4, p5, p6, p7;
	
	private Ground ground1;
	private Ground ground2;
	
	private int FPS = 0;
	
	private int colorCount = 0;
	
	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		
		spriteSheet = loader.loadImage("/spritesheet.png");
		background = loader.loadImage("/background2.png");
		dirt = loader.loadImage("/ground.png");
		
		addKeyListener(new KeyInput(this));
		
		p = new Player(0, getHeight()-200, 16, 24, 4.0, this);
		p1 = new Player(100, getHeight()-200, 16, 24, 4.0, this);
		p2 = new Player(200, getHeight()-200, 16, 24, 4.0, this);
		p3 = new Player(300, getHeight()-200, 16, 24, 4.0, this);
		p4 = new Player(400, getHeight()-200, 16, 24, 4.0, this);
		p5 = new Player(500, getHeight()-200, 16, 24, 4.0, this);
		p6 = new Player(600, getHeight()-200, 16, 24, 4.0, this);
		p7 = new Player(700, getHeight()-200, 16, 24, 4.0, this);
		
		players.add(p);
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		players.add(p5);
		players.add(p6);
		players.add(p7);
		
		ground1 = new Ground(0, getHeight()- 64, 128, 128, 0.5);
		//ground2 = new Ground(0, getHeight() - 128, 128, 128, 0.5);
		
	}
	
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println(updates + " Ticks, FPS " + frames);
				this.FPS = frames;
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick(){
		for(int playerCount = 0; playerCount < players.size(); playerCount++){
			players.get(playerCount).tick();
		}
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//////////////////////////////////
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		
		if(players.get(0).isMovingLeft() || players.get(0).isMovingRight()){
			g.setColor(getColor());
			g.drawRect(0, 0, getWidth(), getHeight());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(null);
		}
		
		
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		
		g.drawImage(dirt, 0, (int)ground1.getY(), getWidth(), 64, null);
		//g.drawImage(dirt, 0, (int)ground2.getY(), 254, 64, null);
		
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString(Integer.toString(FPS), 10, 31);
		
		for(int playerCount = 0; playerCount < players.size(); playerCount++){
			players.get(playerCount).render(g);
		}
		
		//////////////////////////////////
		g.dispose();
		bs.show();
		
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
	
	public synchronized void start(){
		if(running){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running){
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public Game(Client client) {
		this.client = client;
		this.user = client.getUser();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D){
			for(int i = 0; i < players.size(); i++){
				players.get(i).setMovingRight(true);
			}
		}
		if(key == KeyEvent.VK_A){
			for(int i = 0; i < players.size(); i++){
				players.get(i).setMovingLeft(true);
			}
		}
		if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W){
			for(int i = 0; i < players.size(); i++){
				players.get(i).startJump();
			}
		}
		if(key == KeyEvent.VK_H){
			p.moveTo(10);
		}
		if(key == KeyEvent.VK_K){
			p.moveTo(600);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_D){
			for(int i = 0; i < players.size(); i++){
				players.get(i).setMovingRight(false);
			}
		}
		if(key == KeyEvent.VK_A){
			for(int i = 0; i < players.size(); i++){
				players.get(i).setMovingLeft(false);
			}
		}
		if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W){
			for(int i = 0; i < players.size(); i++){
				players.get(i).stopJump();
			}
		}
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	public void paint(Graphics g){
		
	}
	
	public Ground getWorld(){
		return ground1;
	}
	
	private void display(String s){
		System.out.println(d.now() + " Game - " + s);
	}

	private void displayError(String s){
		System.err.println(d.now() + " Game - " + s);
	}

}
