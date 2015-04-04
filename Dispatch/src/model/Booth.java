package model;

import java.io.Serializable;

public class Booth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7082964825779594149L;
	private String name;
	private float earnings;
	
	public Booth(String name) {
		this.name = name;
		this.earnings = 0.0f;
	}
	
	public void addEarnings(float earnings) { this.earnings += earnings; }
	public float getEarnings() { return this.earnings; }
	public String getName() { return this.name; }
	
}
