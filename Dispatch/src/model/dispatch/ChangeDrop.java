package model.dispatch;

import server.DispatchServer;
import exceptions.NullClubException;

public class ChangeDrop extends Dispatch<DispatchServer>  {

	private String name;
	
	public ChangeDrop(String source, String club) {
		super(source);
		this.name = club;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.changeDrop(name);
	}
}
