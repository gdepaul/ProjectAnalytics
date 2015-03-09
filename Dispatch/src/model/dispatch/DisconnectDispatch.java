package model.dispatch;

import server.DispatchServer;

public class DisconnectDispatch extends Dispatch<DispatchServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8603451953969189498L;

	public DisconnectDispatch(String source) {
		super(source);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void execute(DispatchServer executeOn) {
		// TODO Auto-generated method stub
		executeOn.disconnect(this.getSource());
		
	}

}
