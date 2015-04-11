package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class FieldSupervisor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7173499165790925141L;

	private static DateFormat formatter; 
	
	private String name;
	private Date timein;
	private Date timeout;
	private boolean dispatched;
	private String action;
	private String location;
	
	public FieldSupervisor(String name) {
		this.name = name;
		this.timein = new Date();
		this.dispatched = false;
	}
	
	public void clockOut() { this.timeout = new Date(); }
	public void dispatch() { this.dispatched = true; }
	public void free()     { this.dispatched = false;  }
	
	public String getName()  { return name;    }
	public Date getTimeIn()  { return timein;  }
	public Date getTimeOut() { return timeout; }
	public Date getHours() {
		long timein = this.timein.getTime();
		long timeout = this.timeout.getTime();
		return new Date(timeout-timein);
	}
	public boolean isDispatched() { return dispatched; }
	
	public String printTimeIn()  { return formatter.format(timein);  }
	public String printTimeout() { return formatter.format(timeout); }

	public void actionLocation(String actionSelected, String locationSelected) {
		this.action = actionSelected;
		this.location = locationSelected;
	}
	
	public String getAction(){
		return action;
	}
	
	public String getLocation(){
		return location;
	}
	
}

