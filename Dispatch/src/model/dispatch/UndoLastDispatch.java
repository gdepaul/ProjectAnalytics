package model.dispatch;

import server.DispatchServer;

public class UndoLastDispatch extends Dispatch<DispatchServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3092761297174589351L;

	public UndoLastDispatch(String source) {
		super(source);
	}

	@Override
	public void execute(DispatchServer executeOn) {
		executeOn.undoLast(getSource());
		
	}

}
