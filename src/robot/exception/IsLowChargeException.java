package robot.exception;

public class IsLowChargeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IsLowChargeException() {
		super("a carga está baixa!\n");
	}
}
