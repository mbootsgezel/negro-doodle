package buzz.conn.server;

import game.Window;
import buzz.conn.client.Client;

public class Starter {

	public static void main(String[] args){
		
		new Thread(Server.getInstance()).start();
		new Thread(Client.getInstance()).start();
		
		new Window(Client.getInstance());
		
	}
	
}
