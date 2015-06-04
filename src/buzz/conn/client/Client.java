package buzz.conn.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import buzz.conn.entities.User;
import buzz.conn.model.CurrentDate;
import buzz.conn.model.Entity;

public class Client implements Runnable{
	
	private static Client instance;
	
	private String host;
	private int port;

	private Socket client;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	private boolean running;
	private boolean newClient;
	
	private CurrentDate d = new CurrentDate();
	
	private User user;
	
	private Client() {
		this.host = "localhost";
		this.port = 1500;
		this.user = new User();
		
		user.setUsername(System.getProperty("user.name"));
	}
	
	@Override
	public void run() {
		try {
			client = new Socket(host, port);
			toServer = new ObjectOutputStream(client.getOutputStream());
			fromServer = new ObjectInputStream(client.getInputStream());
			running = true;
			display("Succesfully connected to: " + host);
		} catch (Exception e){
			displayError("Can't connect to server at " + host);
		}
		
		try {
			sendUser();
			
			while(running){
				Entity ent = (Entity) fromServer.readObject();
				
				switch(ent.getType()){
				case Entity.USER:
					this.user = (User) ent.getObject();
					display(this.user.toString());
					break;
				}
			}
		} catch (Exception e){
			displayError("Connection to server has been lost.");
		}
	}
	
	public void sendUser(){
		//display("Sending user to server");
		Entity user = new Entity(Entity.USER, this.user);
		try { 
			toServer.writeObject(user);
		} catch (Exception e){
			displayError("Can't send user to server. Exception caught.");
		}
	}
	
	public User getUser(){
		return user;
	}
	
	private void display(String s){
		System.out.println(d.now() + " Client - " + s);
	}
	
	private void displayError(String s){
		System.err.println(d.now() + " Client - " + s);
	}
	
	public static Client getInstance(){
		if(instance == null){
			instance = new Client();
		}
		return instance;
	}
	
}
