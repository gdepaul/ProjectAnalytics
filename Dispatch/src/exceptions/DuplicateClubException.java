package exceptions;

public class DuplicateClubException extends Exception {
	private static final long serialVersionUID = 1090418467312760071L;
	
	public DuplicateClubException() { super("Club already exists!"); }
	public DuplicateClubException(String message) { super(message); } 
	public DuplicateClubException(Throwable cause) { super(cause); }
	
}