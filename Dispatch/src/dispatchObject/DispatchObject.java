package dispatchObject;

import java.io.Serializable;

import model.Club;

public abstract class DispatchObject<T> implements Serializable {

	/**
	 * 	This long was auto-generated
	 */
	private static final long serialVersionUID = 1674881261061250454L;
	
	private String club;
	private int cash;
	private int tickets;
	
	private int change;
		public DispatchObject(String club, int cash, int tickets, int change) {
		super();
		this.club = club;
		this.cash = cash;
		this.tickets = tickets;
		this.change = change;
	}
	
	public void updateClub(String clubName){
		
	}
	
	public Club returnClubStatus(String clubName){
		
		return null;
	}
	
	public void addClub(Club newClub){
		
	}
	
	
}
