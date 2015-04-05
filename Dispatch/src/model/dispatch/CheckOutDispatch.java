package model.dispatch;

import server.DispatchServer;
import exceptions.NullClubException;

public class CheckOutDispatch extends Dispatch<DispatchServer>{
	
	public CheckOutDispatch(String source, String club,
			float collected_revenue, int tickets_sold, int wristbands_sold,
			float misc_credits_promos, boolean on_credit_terminal) {
		super(source);
		this.club = club;
		this.collected_revenue = collected_revenue;
		this.tickets_sold = tickets_sold;
		this.wristbands_sold = wristbands_sold;
		this.misc_credits_promos = misc_credits_promos;
		this.on_credit_terminal = on_credit_terminal;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445909438060758561L;
	private String club;
	private float collected_revenue;
	private int tickets_sold;
	private int wristbands_sold;
	private float misc_credits_promos;
	private boolean on_credit_terminal;
	
	

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.checkout(club, collected_revenue, tickets_sold, wristbands_sold, misc_credits_promos, on_credit_terminal);
		//executeOn.addTransaction(this);
	}
	public void undoDispatch() {
		//Not sure if this is implemented as expected by server
		this.collected_revenue = 0;
		this.tickets_sold = 0;
		this.wristbands_sold = 0;
		this.misc_credits_promos = 0;
		this.on_credit_terminal = false;
	}
}
