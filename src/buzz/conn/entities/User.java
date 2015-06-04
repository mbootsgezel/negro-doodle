package buzz.conn.entities;

import buzz.conn.model.Entity;

public class User extends Entity{
	
	private static final long serialVersionUID = -4132322928684069716L;

	private String username;
	private int userid;
	
	public User(String username) {
		this.username = username;
	}
	
	public User(){
		
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setUserID(int userid){
		this.userid = userid;
	}
	
	public String getUsername(){
		return username;
	}
	
	public int getUserID(){
		return userid;
	}
	
	public String toString(){
		return "Username = " + username + ", ID = " + userid;
	}
	

}
