package exceptions;

public class IllegalTicketOperation extends Exception {
	private static final long serialVersionUID = 6288984744389121395L;

	public IllegalTicketOperation() { super("IllegalTicketOperation");}
	public IllegalTicketOperation(String message) { super(message);}
}