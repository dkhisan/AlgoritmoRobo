package robot.exception;

public class AlreadyStoppedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyStoppedException() {
		super("o robô já está parado.\n");
	}
}
