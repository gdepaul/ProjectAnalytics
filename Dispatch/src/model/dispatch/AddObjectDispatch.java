package model.dispatch;

import model.dispatchObject.DispatchObject;
import server.DispatchServer;

public class AddObjectDispatch extends Dispatch<DispatchServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 640029361107769297L;

	private DispatchObject object;
	
	public AddObjectDispatch(String source, DispatchObject object) {
		super(source);
		this.object = object;
	}

	@Override
	public void execute(DispatchServer executeOn) {
		//executeOn.addObject(object);		
	}
}
