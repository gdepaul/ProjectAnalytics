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

	public DispatchAll(String source, String club, int cashDrops, int changeDrops,
			int fullSheets, int halfSheets, int singleTickets) {
		super(source,club);
		this.cashDrops = cashDrops;
		this.changeDrops = changeDrops;
		this.fullSheets = fullSheets;
		this.halfSheets = halfSheets;
		this.singleTickets = singleTickets;
	}

	
	
	
	
//	public DispatchAll(String source, String club, int ) {
//		super(source,club);
//		this.amount = amount;
//	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.dispatchAll(this.club, this.cashDrops, this.changeDrops, this.fullSheets, this.halfSheets, this.singleTickets);
	}
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "DispatchAllTypes:     "+ format.format(date) +"     Source: " + this.getSource() + "     Club: " + this.club +  "      # of Cash Drops: " + this.cashDrops  + "      # of Change Drops: " + this.changeDrops  + "      # of Full Sheets: " + this.fullSheets +  "     # of Half Sheets: " + this.halfSheets;
	}
}