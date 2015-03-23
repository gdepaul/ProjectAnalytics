package model.dispatch;

import server.DispatchServer;
import exceptions.NotDispatchedException;
import exceptions.NullFieldSupeException;

public class FreeFieldSupe extends Dispatch<DispatchServer>  {
	private static final long serialVersionUID = 5907587365404657210L;
	private String name;
	
	public FreeFieldSupe(String source, String fs) {
		super(source);
		this.name = fs;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NotDispatchedException, NullFieldSupeException {
		executeOn.freeFieldSupe(name);
	}
}
