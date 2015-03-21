package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.dispatchObject.DispatchObject;


public class Club implements Serializable {
	
	private static final long serialVersionUID = -1802530257610968136L;
	private String clubName;
	private float balance;
	private float initialCashDrop;
	private int tickets;
	private int changedrops=0;
	private int cashdrops=0;
	private int fullsheets;
	private int halfsheets;
	private int singletickets;
	private int wristbands;
	private List<DispatchObject> transactions;
	
	public Club(String clubName){
		this.clubName = clubName;
		this.balance = 0;
		this.tickets = 0;
		transactions = new ArrayList<DispatchObject>();
	}
	
	public Club(String clubName, int money, int tickets){
		this.clubName = clubName;
		this.balance = money;
		this.tickets = tickets;
		transactions = new ArrayList<DispatchObject>();
	}
	
	//Initial Cash Drop can be different for each club, but should not be
	//changed after it was made except to fix an administrative error.
	public float getInitialCashDrop(){
		return initialCashDrop;
	}
	
	public void setInitialCashDrop(float drop){
		initialCashDrop = drop;
	}
	
	public int getChangedrops() {
		return changedrops;
	}

	public int getCashdrops() {
		return cashdrops;
	}

	public int getFullsheets() {
		return fullsheets;
	}

	public int getHalfsheets() {
		return halfsheets;
	}

	public int getSingletickets() {
		return singletickets;
	}

	public int getWristbands() {
		return wristbands;
	}

	// This will later be used to return a list of all transactions for this club
	// Currently, this pulls from transactions, but that variable isn't being populated yet in the server. 3/21/2015
	public String totalTransactions(){
		String printTransactions = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //2014/08/06 15:59:48\
		if (transactions.size()!=0){
			for (DispatchObject transaction : transactions){
				Date date = new Date();
				printTransactions += dateFormat.format(date) + transaction.getObjectType() + "\n";
			}
		}
		if (printTransactions.compareTo("")==0){
			return "No transactions in this history!";
		} else
		return printTransactions;
	}
	
	public void addTransaction(DispatchObject dispatchObject){
		transactions.add(dispatchObject);
	}
	
	public void addMoney(int more_money){
		balance += more_money;
	}
	
	public void subtractMoney(int less_money){
		balance -= less_money;
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

	public float getBalance() {
		return balance;
	}

	public int getTickets() {
		return tickets;
	}
	
	public void putChangeDrop() { System.err.println("PUTTING CASH DROP"); this.changedrops++; }
	public void putCashDrop() { this.cashdrops++; }
	public void putFullSheet(int amount) { this.fullsheets += amount; }
	public void putHalfSheet(int amount) { this.halfsheets += amount; }
	public void putSingleTickets(int amount) { this.singletickets += amount; }

	
}
