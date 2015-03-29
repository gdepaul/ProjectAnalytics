package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.*;

public class TicketDrop extends Dispatch<DispatchServer>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2538709215261306074L;

	private String type; 
	
	private int num_fulls;
	private int num_halves;
	private int num_singles;
	private int num_wristbands;
	
	public TicketDrop(String source, String club, int num_fulls, int num_halves, int num_singles, int num_wristbands) {
		super(source, club);
		this.num_fulls=num_fulls;
		this.num_halves=num_halves;
		this.num_singles=num_singles;
		this.num_wristbands=num_wristbands;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException, IllegalTicketOperation {
		executeOn.ticketDrop(this.club, num_fulls, num_halves, num_singles, num_wristbands);
	}
	
	public String getName() { return this.club; }
	public String getType() { return type; }
	public int getFullSheets() { return num_fulls; }
	public int getHalfSheets() { return num_halves; }
	public int getSingles() {return num_singles;}
	
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "TicketDrop:     "+ format.format(date) +
				"     Source: " + this.getSource() + 
				"     Club: " + this.club + 
				"     Full Sheets: " + this.num_fulls + 
				"     Half Sheets: " + this.num_halves +
				"     Single tickets: " + this.num_singles;
	}
}
