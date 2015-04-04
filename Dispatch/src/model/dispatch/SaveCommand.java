package model.dispatch;

import exceptions.NullClubException;
import server.DispatchServer;

public class SaveCommand extends Dispatch<DispatchServer>  {

	
	public SaveCommand(String source) {
		super(source);
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.saveState();
	}

}
