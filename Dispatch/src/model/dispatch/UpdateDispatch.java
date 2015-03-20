package model.dispatch;

import java.util.List;

import model.Club;
import controller.CompleteClient;

public class UpdateDispatch extends Dispatch<CompleteClient> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6956919293952269369L;
	List<Club> clubs;
	List<String> availableFS;
	List<String> dispatchedFS;

	public UpdateDispatch(String source, List<Club> clubs, List<String> availableFS, List<String> dispatchedFS) {
		super(source);
		this.clubs = clubs;
		this.availableFS = availableFS;
		this.dispatchedFS = dispatchedFS;
	}


	@Override
	public void execute(CompleteClient executeOn) {
		executeOn.update(clubs, availableFS, dispatchedFS);
	}

}
