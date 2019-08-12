package robot.exception;

public class AlreadyGoingHomeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadyGoingHomeException() {
		super("o robô já está retornado.\n");
	}
}
