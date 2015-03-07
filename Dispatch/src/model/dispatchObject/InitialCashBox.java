package model.dispatchObject;

public class InitialCashBox extends DispatchObject<DispatchObject>{

	public InitialCashBox(String club, int cash, int tickets, int change) {
		super(club, cash, tickets, change);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getObjectType(){
		return "InitialCashBox";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7373109302488497709L;

}
