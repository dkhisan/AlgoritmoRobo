package robot.exception;

public class AlreadyStandingByException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyStandingByException() {
		super("o robô já está em espera.\n");
	}
}
