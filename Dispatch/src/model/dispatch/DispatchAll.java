package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.NullClubException;

public class DispatchAll  extends Dispatch<DispatchServer>  {
	
	private int cashDrops;
	private int changeDrops;
	private int fullSheets;
	private int halfSheets;
	private int singleTickets;
	private int wristbands;

	public DispatchAll(String source, String club, int cashDrops, int changeDrops,
			int fullSheets, int halfSheets, int singleTickets, int addWristbands) {
		super(source,club);
		this.cashDrops = cashDrops;
		this.changeDrops = changeDrops;
		this.fullSheets = fullSheets;
		this.halfSheets = halfSheets;
		this.singleTickets = singleTickets;
		this.wristbands = addWristbands;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.dispatchAll(this.club, this.cashDrops, this.changeDrops, this.fullSheets, this.halfSheets, this.singleTickets, this.wristbands);
	}
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "DispatchAllTypes:     "
				+ format.format(date) 
				+"     Source: " + this.getSource() 
				+ "     Cashier: " + this.club 
				+ "      # of Cash Drops: " + this.cashDrops  
				+ "      # of Change Drops: " + this.changeDrops  
				+ "      # of Full Sheets: " + this.fullSheets 
				+ "      # of Half Sheets: " + this.halfSheets
				+ "      # of Single tickets: " + this.singleTickets
				+ "      # of Wristbands: " +this.wristbands;
	}
	public void undoDispatch() {
		this.cashDrops *= -1;
		this.changeDrops *= -1;
		this.fullSheets *= -1;
		this.halfSheets *= -1;
		this.singleTickets *= -1;
		this.wristbands *= -1;
	}
}