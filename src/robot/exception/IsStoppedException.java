package robot.exception;

public class IsStoppedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IsStoppedException() {
		super("o robô está parado.\n");
	}
}
