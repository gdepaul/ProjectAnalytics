package model.dispatchObject;

public class RemoveActiveClub extends DispatchObject<DispatchObject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6033090124052583532L;

	public RemoveActiveClub(String club, int cash, int tickets, int change) {
		super(club, cash, tickets, change);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getObjectType(){
		return "RemoveActiveClub";
	}

}
