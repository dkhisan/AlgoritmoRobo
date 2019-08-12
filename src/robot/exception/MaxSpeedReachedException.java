package robot.exception;

public class MaxSpeedReachedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaxSpeedReachedException() {
		super("o robô atingiu a velocidade máxima!\n");
	}
}
