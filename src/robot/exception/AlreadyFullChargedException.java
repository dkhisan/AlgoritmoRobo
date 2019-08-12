package robot.exception;

public class AlreadyFullChargedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyFullChargedException() {
		super("a carga de bateria do robô já está cheia.\n");
	}
}
