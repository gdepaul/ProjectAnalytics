package model.dispatchObject;

public class CashDrop extends DispatchObject<DispatchObject>{

	public CashDrop(String club) {
		this.club=club;
	}

	@Override
	public String getObjectType(){
		return "CashDrop";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5773002521425641776L;
	


}
