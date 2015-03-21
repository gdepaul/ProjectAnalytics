package exceptions;

public class DuplicateFieldSupeException extends Exception {

	private static final long serialVersionUID = -4747111784510042179L;
	
	public DuplicateFieldSupeException() { super("Field Supervisor already exists!"); }
	public DuplicateFieldSupeException(String message) { super(message); } 
	public DuplicateFieldSupeException(Throwable cause) { super(cause); }
}
