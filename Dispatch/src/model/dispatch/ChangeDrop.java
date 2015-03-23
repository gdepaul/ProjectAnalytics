package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.NullClubException;

public class ChangeDrop extends Dispatch<DispatchServer>  {

	private String supe; 
	
	public ChangeDrop(String source, String club) {
		super(source,club);
	}
	public ChangeDrop(String source, String club, String supe) {
		super(source,club);
		this.supe=supe;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.changeDrop(this.club);
	}
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "ChangeDrop:"+ format.format(date) + "\tField Supervisor Assigned: " + this.supe + "\tSource: " + this.getSource() + "\tClub: " + this.club;
	}
}
