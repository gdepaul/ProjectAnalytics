package exceptions;

public class DeployedException extends Exception {
	private static final long serialVersionUID = -9140655405848107198L;
	
	public DeployedException() { super("Field Supervisor is dispatched!"); }
	public DeployedException(String message) { super(message); }
	public DeployedException(Throwable cause) { super(cause); }

}
