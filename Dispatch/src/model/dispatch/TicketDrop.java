package model.dispatch;

import server.DispatchServer;
import exceptions.*;

public class TicketDrop extends Dispatch<DispatchServer>  {

	private String name;
	private String type; 
	private int amount;
	
	public TicketDrop(String source, String club, String type, int amount) {
		super(source);
		this.name = club;
		this.type=type;
		this.amount = amount;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException, IllegalTicketOperation {
		executeOn.ticketDrop(name,type,amount);
	}
	
	public String getName() { return name; }
	public String getType() { return type; }
	public int getAmount() { return amount; }
}
