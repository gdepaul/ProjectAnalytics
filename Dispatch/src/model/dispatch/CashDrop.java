package model.dispatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import server.DispatchServer;
import exceptions.NullClubException;

public class CashDrop  extends Dispatch<DispatchServer>  {

	private int amount;
	public CashDrop(String source, String club, int amount) {
		super(source,club);
		this.amount = amount;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.cashDrop(this.club, this.amount);
	}
	public String toString() {
		DateFormat format = new SimpleDateFormat("MMM dd YYYY HH:mm:ss");
		Date date = new Date();
		return "CashDrop:"+ format.format(date) +"\tSource: " + this.getSource() + "\tClub: " + this.club;
	}
}
