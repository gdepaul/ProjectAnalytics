package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.NullClubException;

public class InitialCashDrop extends Dispatch<DispatchServer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445909438060758561L;
	private String club;
	private float initialCash;
	private int initialTickets;
	private int initialWristbands;
	private String location;
	
	public InitialCashDrop(String source, String club, float drop, int initialTickets, int initialWristbands, String location) {
		super(source,club);
		this.club = club;
		this.initialCash = drop;
		this.initialTickets = initialTickets;
		this.initialWristbands = initialWristbands;
		this.location = location;
	}
	public InitialCashDrop(String source, String club, float drop, int initialTickets, int initialWristbands) {
		super(source,club);
		this.club = club;
		this.initialCash = drop;
		this.initialTickets = initialTickets;
		this.initialWristbands = initialWristbands;
	}
	

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.initialCashDrop(club, initialCash, initialTickets, initialWristbands, location);
		//executeOn.addTransaction(this);
	}
	public void undoDispatch() {
		this.initialCash *= -1;
		this.initialTickets *= -1;
		this.initialWristbands *= -1;
	}
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "Intial Cash Drop:     "+ format.format(date) +"     Source: " + this.getSource() + "     Club: " + this.club + "     Initial Cash: " + this.initialCash + "     Initial Tickets: " + this.initialTickets + "     Initial Wristbands:" + this.initialWristbands;
	}
}
