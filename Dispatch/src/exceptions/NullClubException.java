package exceptions;

public class NullClubException extends Exception {
	static final long serialVersionUID = -7775294065712225093L;
 
	private String name;
	
	public NullClubException(String club) { super("Club does not exist"); name=club;}
	public NullClubException(String message, String club) { super(message); name=club; }
	
	public String getClubName() { return name; }
}
