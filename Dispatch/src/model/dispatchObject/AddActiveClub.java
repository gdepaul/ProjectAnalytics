package model.dispatchObject;

public class AddActiveClub extends DispatchObject<DispatchObject>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8709142730697251377L;

	public AddActiveClub(String club, int cash, int tickets, int change) {
		super(club, cash, tickets, change);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getObjectType(){
		return "AddActiveClub";
	}

}
