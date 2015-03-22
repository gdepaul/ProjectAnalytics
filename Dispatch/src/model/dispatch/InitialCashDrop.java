package model.dispatch;

import server.DispatchServer;
import exceptions.NullClubException;

public class InitialCashDrop extends Dispatch<DispatchServer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445909438060758561L;
	private String club;
	private float drop;
	
	public InitialCashDrop(String source, String club, float drop) {
		super(source);
		this.club = club;
		this.drop = drop;
	}
	

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.initialCashDrop(club, drop);
	}

}
