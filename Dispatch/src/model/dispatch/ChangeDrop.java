package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.NullClubException;

public class ChangeDrop extends Dispatch<DispatchServer>  {

	private String supe; 
	private int amount;
	public ChangeDrop(String source, String club, int amount) {
		super(source,club);
		this.amount = amount;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.changeDrop(this.club, amount);
	}
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "ChangeDrop:     "+ format.format(date) + "     Source: " + this.getSource() + "     Club: " + this.club + "     # of Change Drops: " + this.amount;
		//return "ChangeDrop:\t"+ format.format(date) + "\tSource: " + this.getSource() + "\tClub: " + this.club + "\t # of Change Drops: " + this.amount;
	}
	public String toStringList() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "ChangeDrop:     "+ format.format(date) + "     Source: " + this.getSource() + "     Club: " + this.club + "     # of Change Drops: " + this.amount;
	}
}
