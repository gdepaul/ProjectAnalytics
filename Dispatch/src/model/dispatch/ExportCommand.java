package model.dispatch;

import exceptions.NullClubException;
import server.DispatchServer;

public class ExportCommand  extends Dispatch<DispatchServer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8013524975672048593L;

	public ExportCommand(String source) {
		super(source);
	}

	@Override
	public void execute(DispatchServer executeOn) throws NullClubException {
		executeOn.export();
	}
}
