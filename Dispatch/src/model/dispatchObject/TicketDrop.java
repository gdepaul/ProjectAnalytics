package model.dispatchObject;

public class TicketDrop extends DispatchObject<DispatchObject> {

	public TicketDrop(String club, int cash, int tickets, int change) {
		super(club, cash, tickets, change);
	}
	
	@Override
	public String getObjectType(){
		return "TicketDrop";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7907417359925041211L;

}
