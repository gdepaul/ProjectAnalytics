package model.dispatchObject;

import java.io.Serializable;

import model.Club;

public abstract class DispatchObject<T> implements Serializable {

	/**
	 * 	This long was auto-generated
	 */
	private static final long serialVersionUID = 1674881261061250454L;
	
	protected String club;
	protected int cash;
	protected int tickets;
	
	private int change;
	public DispatchObject() { }
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
	
	public String getObjectType(){
		return "not implemented for this class";
		
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClub() {
		return club;
	}

	public int getCash() {
		return cash;
	}

	public int getTickets() {
		return tickets;
	}

	public int getChange() {
		return change;
	}
	
	
}
