package exceptions;

public class NotDispatchedException extends Exception {
	private static final long serialVersionUID = 6095373327255757761L;
	
	public NotDispatchedException() { super("Field Supervisor is not currently dispatched"); }
	public NotDispatchedException(String message) { super(message); }
	public NotDispatchedException(Throwable cause) { super(cause); }
}
