package robot.exception;

public class IsNotExploringException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IsNotExploringException() {
        super("o robô não está explorando.\n");
    }
}
