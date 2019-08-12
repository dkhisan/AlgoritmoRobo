package robot.exception;

public class IsNotStoppedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IsNotStoppedException() {
		super("o robô nâo está parado.\n");
	}
}
