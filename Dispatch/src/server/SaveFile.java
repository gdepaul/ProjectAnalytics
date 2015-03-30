package server;

import java.io.Serializable;
import java.util.*;

import model.Club;
import model.FieldSupervisor;
import model.dispatch.Dispatch;

public class SaveFile implements Serializable {

	private HashMap<String,Club> activeClubs;
	private HashMap<String, Club> inactiveClubs;
	private HashMap<String, FieldSupervisor> field_sups;
	private Map<String, Deque<Dispatch<DispatchServer>>> history;

	
	public SaveFile(HashMap<String,Club> clubs, HashMap<String,Club> inactiveClubs, HashMap<String, FieldSupervisor> field_sups, Map<String, Deque<Dispatch<DispatchServer>>> hist) {
		this.activeClubs = clubs;
		this.inactiveClubs = inactiveClubs;
		this.field_sups = field_sups;
		this.history = hist;
	}
	public SaveFile() {
		
	}
	
	public HashMap<String,Club> getClubs() { return this.activeClubs; }
	public HashMap<String, Club> getInactiveClubs() { return this.inactiveClubs; }
	public HashMap<String, FieldSupervisor> getFieldSupervisors()   { return this.field_sups; }
	public Map<String, Deque<Dispatch<DispatchServer>>> getHistory() { return this.history; }
	
}