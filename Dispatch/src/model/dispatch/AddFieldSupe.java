package model.dispatch;

import model.Club;
import server.DispatchServer;
import exceptions.DuplicateClubException;
import exceptions.DuplicateFieldSupeException;

public class AddFieldSupe extends Dispatch<DispatchServer> {
	private String name;
	
	public AddFieldSupe(String source, String name) {
		super(source);
		this.name=name;
	}

	@Override
	public void execute(DispatchServer executeOn) throws DuplicateFieldSupeException {
		executeOn.addFieldSupe(name);		
	}

}
