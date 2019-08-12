package robot.exception;

public class IsStandByException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IsStandByException() {
		super("o robô está em espera.\n");
	}
}
