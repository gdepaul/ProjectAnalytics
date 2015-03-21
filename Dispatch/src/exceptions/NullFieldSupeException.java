package exceptions;

public class NullFieldSupeException extends Exception {
	private static final long serialVersionUID = 9003098277724658110L;
	
	public NullFieldSupeException() { super("Field Supervisor does not exist"); }
	public NullFieldSupeException(String message) { super(message); }
	public NullFieldSupeException(Throwable cause) { super(cause); }
}
