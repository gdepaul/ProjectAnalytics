package model.dispatch;

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
		super(source);
		this.club = club;
		this.initialCash = drop;
		this.initialTickets = initialTickets;
		this.initialWristbands = initialWristbands;
		this.location = location;
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

}
