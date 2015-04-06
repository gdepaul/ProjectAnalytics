package model.dispatch;

import server.DispatchServer;

public class DisconnectDispatch extends Dispatch<DispatchServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8603451953969189498L;

	public DisconnectDispatch(String source) {
		super(source);
	}


	@Override
	public void execute(DispatchServer executeOn) {
		executeOn.disconnect(this.getSource());
	}
}
