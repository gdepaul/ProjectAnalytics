package model.dispatch;

import server.DispatchServer;
import exceptions.NullClubException;

public class CashDrop  extends Dispatch<DispatchServer>  {

	private String name;
	
	public CashDrop(String source, String club) {
		super(source);
		this.name = club;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.cashDrop(name);
	}
}
