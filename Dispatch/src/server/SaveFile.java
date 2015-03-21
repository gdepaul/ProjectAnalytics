package server;

import java.io.Serializable;
import java.util.*;

import model.Club;
import model.dispatch.Dispatch;

public class SaveFile implements Serializable {

	private HashMap<String,Club> clubs;
	private List<String> availableFS;
	private List<String> dispatchedFS;
	
	private Map<String, Deque<Dispatch<DispatchServer>>> history;

	
	public SaveFile(HashMap<String,Club> clubs, List<String> avail, List<String> disp, Map<String, Deque<Dispatch<DispatchServer>>> hist) {
		this.clubs = clubs;
		this.availableFS = avail;
		this.dispatchedFS = disp;
		this.history = hist;
	}
	
	public HashMap<String,Club> getClubs() { return clubs; }
	public List<String> getAvailableFS()   { return this.availableFS; }
	public List<String> getDispatchedFS()  { return this.dispatchedFS; }
}