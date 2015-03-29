package model.dispatch;

import exceptions.DuplicateClubException;
import model.*;
import server.DispatchServer;

public class AddActiveClub extends Dispatch<DispatchServer> {

	private static final long serialVersionUID = 5807572352608953015L;
	private Club club;
	
	public AddActiveClub(String source, Club newClub) {
		super(source);
		this.club = newClub;
	}
	public AddActiveClub(String source, String clubname) {
		super(source);
		this.club= new Club(clubname);		
	}

	@Override
	public void execute(DispatchServer executeOn) throws DuplicateClubException {
		executeOn.addClub(this.club);
	}
}
