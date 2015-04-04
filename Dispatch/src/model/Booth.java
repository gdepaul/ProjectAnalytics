package model;

public class Booth {

	private String name;
	private float earnings;
	
	public Booth(String name) {
		this.name = name;
		this.earnings = 0.0f;
	}
	
	public void addEarnings(float earnings) { this.earnings += earnings; }
	public float getEarnings() { return this.earnings; }
	
}
