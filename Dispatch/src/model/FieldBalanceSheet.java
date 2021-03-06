/*
 * FieldBalanceSheet
 * 2/1/15
 * Tyler Bernth
 * 
 * This class is used to implement the pink field
 * balance sheets that were used in the past. Various
 * functions include taking cash out, returning cash etc.
 * 
 */

/*
 * CHANGE: Added model package declaration for compilation. 
 * -Kris Cabulong 3/5/2015 1:55 pm
 */
package model;


public class FieldBalanceSheet {

	private String organizationName;
	private String location;
	private String cashierName, cashBoxNumber;
	private double cashOut, expectedRevenue;
	private int fullSheets, halfSheets, singleTickets, wristbands;
	private boolean returnedCorrectAmount;
	
	/*
	 * Constructor for a fieldBalanceSheet. 
	 * The only thing needed to make one is 
	 * the organization name. Everything else
	 * is initialized to 0 or ""
	 */
	
	FieldBalanceSheet(String organization){
		this.organizationName=organization;
		this.location="";
		this.cashierName="";
		this.cashBoxNumber="0";
		this.cashOut=0.0;
		this.fullSheets=0;
		this.halfSheets=0;
		this.singleTickets=0;
		this.wristbands=0;
		this.expectedRevenue=0.0;
		this.setReturnedCorrectAmount(false);
	}
	
	/*
	 * takeCashOut takes the number of pennies
	 * nickels, dimes, quarters, and dollars taken
	 * out by that organization.
	 */
	
	public String getOrganization() {
		return this.organizationName;
	}

	public double getCashOut() {
		return this.cashOut;
	}
	
	private int getWristbands() {
		return this.wristbands;
	}

	private int getSingleTickets() {
		return this.singleTickets;
	}

	private int getHalfSheets() {
		return this.halfSheets;
	}

	private int getFullSheets() {
		return this.fullSheets;
	}
	
	private void setExpectedRevenue(double revenue) {
		this.expectedRevenue=revenue;
	}
	private void setReturnedCorrectAmount(boolean b) {
		this.returnedCorrectAmount=b;	
	}
	
	void takeCashOut(int pennies, int nickels, int dimes,
			int quarters, int dollars, int full, int half,
			int single, int wrist){
		
		double totalDollars=0.0;
		
		totalDollars=pennies *0.01;
		totalDollars+=nickels *0.05;
		totalDollars+=dimes *0.10;
		totalDollars+=quarters *0.25;
		totalDollars+=dollars;
		
		this.cashOut=totalDollars;
		
		this.fullSheets=full;
		this.halfSheets=half;
		this.singleTickets=single;
		this.wristbands=wrist;
	}

	public void returnCash(int pennies, int nickels, int dimes,
			int quarters, int dollars, int full, int half,
			int single, int wrist) {

		int unsoldFull=0, unsoldHalf=0, unsoldSingle=0, unsoldWrist=0;
		double revenue=0.0, cashBack=0.0;
		
		cashBack=pennies*0.01;
		cashBack+=nickels*0.05;
		cashBack+=dimes*0.10;
		cashBack+=quarters*0.25;
		cashBack+=dollars;
		
		unsoldFull=this.getFullSheets()-full;
		unsoldHalf=this.getHalfSheets()-half;
		unsoldSingle=this.getSingleTickets()-single;
		unsoldWrist=this.getWristbands()-wrist;
		
		revenue=(unsoldFull*20)+(unsoldHalf*10)+(unsoldSingle*.75)+(unsoldWrist*15);
		this.setExpectedRevenue(revenue);
		
		if(this.getCashOut()+revenue == cashBack)
			this.setReturnedCorrectAmount(true);
	}	
}
