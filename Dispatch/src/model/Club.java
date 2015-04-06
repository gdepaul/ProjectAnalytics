package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import server.DispatchServer;
import model.dispatch.Dispatch;
import model.dispatchObject.DispatchObject;


public class Club implements Serializable {

	private static final long serialVersionUID = 6552795683175716091L;
	private String clubName;
	private float balance;
	private float initialCashDrop;
	private int initialTickets;
	private int changedrops;
	private int cashdrops;
	private int fullsheets;
	private int halfsheets;
	private int singletickets;
	private int wristbands;
	private int initialWristbands;
	private String location;
	private List<Dispatch<DispatchServer>> transactions;
	private int wristbandPrice = 0;
//--Checkout values
	private float expected_ending_cash;
	private float expected_revenue;
	private float collected_revenue;
	private int tickets_sold;
	private int wristbands_sold;
	private float misc_credits_promos;
	private boolean on_credit_terminal;
	private float diff;
	
	public Club(String clubName){
		this.clubName = clubName;
		this.balance = 0;
		this.initialCashDrop = 0;
		this.initialTickets = 0;
		this.changedrops = 0;
		this.cashdrops = 0;
		this.fullsheets = 0;
		this.halfsheets = 0;
		this.singletickets = 0;
		this.wristbands = 0;
		this.initialWristbands = 0;
		this.location = "(Unassigned)";
		
		transactions = new ArrayList<Dispatch<DispatchServer>>();
	}
	
	public void setLocation(String location)   { this.location = location; }
	public float getInitialCashDrop()          { return initialCashDrop; }
	public void setInitialCashDrop(float drop) { initialCashDrop = drop; }	
	public void setInitialTickets(int drop)    { initialTickets = drop; }	
	public void setInitialWristbands(int drop) { initialWristbands = drop; }
	
	public void addMoney(int more_money) 		{ balance += more_money; }
	public void subtractMoney(int less_money) 	{ balance -= less_money; }
	public void addTransaction(Dispatch<DispatchServer> dispatch) { transactions.add(dispatch); }
	public void putChangeDrop(int amount) { this.changedrops += amount; }
	public void putCashDrop(int amount)   { this.cashdrops   += amount; }
	public void putFullSheet(int amount)  { this.fullsheets  += amount; }
	public void putHalfSheet(int amount)  { this.halfsheets  += amount; }
	public void putSingleTickets(int amount) {	this.singletickets+= amount;}
	public void putWristbands(int num_wristbands) { wristbands+=num_wristbands; }
	public void setCollected_revenue(float collected_revenue) { this.collected_revenue = collected_revenue; }
	public void setTickets_sold(int tickets_sold) { this.tickets_sold = tickets_sold; }
	public void setWristbands_sold(int wristbands_sold) { this.wristbands_sold = wristbands_sold; }
	public void setMisc_credits_promos(float misc_credits_promos) { this.misc_credits_promos = misc_credits_promos; }
	public void setOn_credit_terminal(boolean on_credit_terminal) { this.on_credit_terminal = on_credit_terminal; }

	public String getLocation()   { return location; }
	public int getChangedrops()   { return changedrops; }
	public int getCashdrops()     { return cashdrops; }
	public int getFullsheets()    { return fullsheets; }
	public int getHalfsheets()    { return halfsheets; }
	public int getSingletickets() { return singletickets; }
	public int getWristbands()    { return wristbands; }
	public String getClubName()   { return clubName; }
	public float getBalance()     { return balance; }
	public int getTickets()       { return getFullsheets()*40 + getHalfsheets()*20 + getSingletickets(); }
	public int getInitialTickets(){ return initialTickets; }
	public int getInitialWristbands() { return initialWristbands; }	
	public List<Dispatch<DispatchServer>> getTransactions() { return this.transactions;	}
	public boolean isOn_credit_terminal() { return on_credit_terminal; }
	public float getCollected_revenue() { return collected_revenue; }
	public int getTickets_sold() { return tickets_sold; }
	public int getWristbands_sold() { return wristbands_sold; }
	public float getMisc_credits_promos() { return misc_credits_promos; }
	public float getDifference() { return this.diff; }
	public float getExpectedEndingCash() { return this.expected_ending_cash; }
	public float getExpectedRevenue() { return this.expected_revenue; }
	
	
	public float checkout() {
		Calendar cal = new GregorianCalendar();
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			System.out.println("YES TODAY IS SUNDAY!");
		}
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
			wristbandPrice = 30;
		else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			wristbandPrice = 20;
		this.expected_revenue = (this.tickets_sold*.50f) + (this.wristbands_sold*this.wristbandPrice);
		this.expected_ending_cash = this.expected_revenue - this.misc_credits_promos;
		calculateDiff();
		return this.expected_ending_cash;
	}
	private void calculateDiff() {
		this.diff = this.collected_revenue - this.expected_ending_cash;
	}
	
	public String totalTransactions(){
		String printTransactions = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //2014/08/06 15:59:48\
		if (transactions.size()!=0){
			for (Dispatch<?> transaction : transactions){
				Date date = new Date();
				printTransactions += dateFormat.format(date) + transaction.getClass() + "\n";
			}
		}
		if (printTransactions.compareTo("")==0){
			return "No transactions in this history!";
		} else
		return printTransactions;
	}
	public void printTickets() {
		System.out.println("Tickets for " + getClubName() + "\n" +
							"    Full Sheets: " + getFullsheets() + "\n"+
							"    Half Sheets: " + getHalfsheets() + "\n"+
							"    Single tickets: " + getSingletickets() + "\n"+
							"       TOTAL TICKETS: " + getTickets());		
	}
	public String printTransactions() {
		String out="";
		for(Dispatch d : this.transactions) {
			out += d.toString() +"\n";
		}
		return out;
	}


}