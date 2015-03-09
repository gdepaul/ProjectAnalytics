package model.dispatch;

import java.util.List;

import model.Club;
import model.dispatchObject.DispatchObject;
import controller.DispatchClient;

public class UpdateDispatch extends Dispatch<DispatchClient> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6956919293952269369L;
	List<Club> clubs;

	public UpdateDispatch(String source, List<Club> clubs) {
		super(source);
		this.clubs = clubs;
	}


	@Override
	public void execute(DispatchClient executeOn) {
		executeOn.update(clubs);
	}

}
