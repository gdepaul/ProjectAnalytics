package model.dispatchObject;

public class ChangeDrop extends DispatchObject<DispatchObject>{

	public ChangeDrop(String club, int cash, int tickets, int change) {
		super(club, cash, tickets, change);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getObjectType(){
		return "ChangeDrop";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2715653630216322407L;

}
