package model.dispatch;

import model.Club;
import server.DispatchServer;
import exceptions.DeployedException;
import exceptions.DuplicateClubException;
import exceptions.DuplicateFieldSupeException;
import exceptions.NullFieldSupeException;

public class RemoveFieldSupe extends Dispatch<DispatchServer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6271368255268207017L;
	private String name;
	
	public RemoveFieldSupe(String source, String name) {
		super(source);
		this.name=name;
	}

	@Override
	public void execute(DispatchServer executeOn) throws DeployedException, NullFieldSupeException {
		executeOn.removeFieldSupe(name);		
	}

}
