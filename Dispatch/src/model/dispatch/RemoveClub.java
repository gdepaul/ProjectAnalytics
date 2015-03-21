package model.dispatch;

import exceptions.DeployedException;
import server.DispatchServer;

public class RemoveClub extends Dispatch<DispatchServer> {
	
	private static final long serialVersionUID = -9120985204247957675L;
	
	String clubName;
	
	public RemoveClub(String source, String name) {
		super(source);
		this.clubName = name;
	}

	@Override
	public void execute(DispatchServer executeOn) throws DeployedException {
		executeOn.removeClub(clubName);
	}
}
