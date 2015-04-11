package model.dispatch;

import java.util.HashMap;
import java.util.List;

import model.Booth;
import model.Club;
import controller.CompleteClient;

public class UpdateDispatch extends Dispatch<CompleteClient> {
	private static final long serialVersionUID = -6869351988512116145L;
	
	private List<Club> clubs;
	private List<String> availableFS;
	private List<String> dispatchedFS;

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

	public List<Club> getClubs() { return this.clubs; }
	public List<String> getAvailableFS() { return this.availableFS; }
	public List<String> getDispatchedFS() { return this.dispatchedFS; }
}

