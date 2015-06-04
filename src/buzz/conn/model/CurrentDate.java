package buzz.conn.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
	
	public String now(){
		Date now = new Date();
		return sdf.format(new Date());
	}

}
