package game;
import javax.swing.JFrame;

import buzz.conn.client.Client;


public class Window extends JFrame {
	
	private Game game;
	
	public Window(Client client) {
		this.game = new Game(client);
		
		this.setTitle(game.TITLE);
		this.add(game);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		game.start();
	}

}
