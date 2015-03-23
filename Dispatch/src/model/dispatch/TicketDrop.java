package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.*;

public class TicketDrop extends Dispatch<DispatchServer>  {

	private String type; 
	
	private int num_fulls;
	private int num_halves;
	
	public TicketDrop(String source, String club, int num_fulls, int num_halves) {
		super(source, club);
		this.num_fulls=num_fulls;
		this.num_halves=num_halves;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException, IllegalTicketOperation {
		executeOn.ticketDrop(this.club, num_fulls, num_halves);
	}
	
	public String getName() { return this.club; }
	public String getType() { return type; }
	public int getFullSheets() { return num_fulls; }
	public int getHalfSheets() { return num_halves; }
	
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "TicketDrop:"+ format.format(date) +"\tSource: " + this.getSource() + "\tClub: " + this.club + "\tFull Sheets: " + this.num_fulls + "\tHalf Sheets: " + this.num_halves;
	}
}
