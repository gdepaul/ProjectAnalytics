package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.dispatchObject.DispatchObject;


public class Club implements Serializable {
	
	private static final long serialVersionUID = -1802530257610968136L;
	String clubName;
	float money;
	int tickets;
	
	public Club(String clubName){
		this.clubName = clubName;
		this.money = 0;
		this.tickets = 0;
	}
	
	public Club(String clubName, int money, int tickets){
		this.clubName = clubName;
		this.money = money;
		this.tickets = tickets;
	}
	
	List<DispatchObject> transactions = new ArrayList<DispatchObject>();
	
	public String totalTransactions(){
		return "TO BE IMPLEMENTED";
	}
	
	public void addMoney(int more_money){
		money += more_money;
	}
	
	public void subtractMoney(int less_money){
		money -= less_money;
	}
	
	public void addTickets(int more_tickets){
		tickets += more_tickets;
	}
	
	public void subtractTickets(int less_tickets){
		tickets -=less_tickets;
	}
	
	public String getClubName() {
		return clubName;
	}

	public float getMoney() {
		return money;
	}

	public int getTickets() {
		return tickets;
	}

	public List<DispatchObject> getTransactions() {
		return transactions;
	}
	
}
