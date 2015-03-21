package model.dispatch;

import java.io.Serializable;

import exceptions.DeployedException;
import exceptions.DuplicateClubException;
import exceptions.DuplicateFieldSupeException;
import exceptions.IllegalTicketOperation;
import exceptions.NotDispatchedException;
import exceptions.NullClubException;
import exceptions.NullFieldSupeException;

/*
 * Thank you, Mr. Kishi
 * 
 * 				-kc
 */

/**
 * Adapted from: Command
 * 
 * <p>
 * This abstract class defines a serializable command that can be sent and
 * executed on either a client or server.
 * <p>
 * 
 * @author Gabriel Kishi
 */

public abstract class Dispatch<T> implements Serializable {
	private static final long serialVersionUID = -4838155228547508978L;

	private String source; // client or server name

	// On the UML, the constructor says "Command", but I think
	// "Dispatch" was intended.
	public Dispatch(String source) {
		this.source = source;
	}

	public abstract void execute(T executeOn) throws DuplicateFieldSupeException, DuplicateClubException, DeployedException, NullFieldSupeException, NotDispatchedException, NullClubException, IllegalTicketOperation;

	public void undo(T undoOn) {
		// TO BE IMPLEMENTED
	}

	public String getSource() {
		return source;
	}
}
