package model.dispatch;

import exceptions.NullFieldSupeException;
import server.DispatchServer;

public class DispatchFieldSupe extends Dispatch<DispatchServer>  {
	private static final long serialVersionUID = 5907587365404657210L;
	private String name;
	
	public DispatchFieldSupe(String source, String fs) {
		super(source);
		this.name = fs;
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullFieldSupeException {
		executeOn.dispatchFieldSupe(name);		
	}

}
