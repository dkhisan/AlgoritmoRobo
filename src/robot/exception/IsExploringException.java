package robot.exception;

public class IsExploringException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IsExploringException() {
        super("o robô está explorando.\n");
    }
}
