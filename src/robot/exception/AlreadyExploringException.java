package robot.exception;

public class AlreadyExploringException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadyExploringException() {
		super("o robô já está explorando.\n");
	}
}
