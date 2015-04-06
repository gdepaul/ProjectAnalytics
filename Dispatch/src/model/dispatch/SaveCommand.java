package model.dispatch;

import exceptions.NullClubException;
import server.DispatchServer;

public class SaveCommand extends Dispatch<DispatchServer>  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6144651262350033393L;

	public SaveCommand(String source) {
		super(source);
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.saveState();
	}

}
