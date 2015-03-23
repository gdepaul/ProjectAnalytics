package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.*;

public class TicketDrop extends Dispatch<DispatchServer>  {

	private String type; 
	private String supe;
	private int amount;
	
	public TicketDrop(String source, String club, String type, int amount) {
		super(source, club);
		this.type=type;
		this.amount = amount;
	}
	public TicketDrop(String source, String club, String type, int amount, String supe) {
		super(source, club);
		this.type=type;
		this.amount = amount;
		this.supe = supe;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException, IllegalTicketOperation {
		executeOn.ticketDrop(this.club,type,amount);
	}
	
	public String getName() { return this.club; }
	public String getType() { return type; }
	public int getAmount() { return amount; }
	
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "TicketDrop:"+ format.format(date) +"\tSource: " + this.getSource() + "\tField Supervisor Assigned: " + this.supe + "\tClub: " + this.club + "\tType of Drop: " + this.type + "\tAmount: " + this.amount;
	}
}
