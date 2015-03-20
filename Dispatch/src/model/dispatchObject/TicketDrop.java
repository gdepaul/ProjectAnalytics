package model.dispatchObject;

public class TicketDrop extends DispatchObject<DispatchObject> {

	private int full;
	private int half;
	private int single;
	
	public TicketDrop(String club, int full, int half, int single) {
		this.club   = club;
		this.full   = full;
		this.half   = half;
		this.single = single;
	}
	
	@Override
	public String getObjectType(){
		return "TicketDrop";
	}

	public int getFullSheets()    { return full;   }
	public int getHalfSheets()    { return half;   }
	public int getSingleTickets() { return single; }
	
	private static final long serialVersionUID = -7907417359925041211L;

}
