package model.dispatch;

import model.Club;
import model.dispatchObject.DispatchObject;
import controller.DispatchClient;

public class UpdateDispatch extends Dispatch<DispatchClient> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6956919293952269369L;


	public UpdateDispatch(String source) {
		super(source);

	}


	@Override
	public void execute(DispatchClient executeOn) {

	}

}
