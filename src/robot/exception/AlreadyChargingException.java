package robot.exception;

public class AlreadyChargingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyChargingException() {
		super("o robô já está carregando.\n");
	}
}
