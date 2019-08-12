package robot.exception;

public class TemperatureIsZeroException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TemperatureIsZeroException() {
        super("a temperatura é zero!");
    }
}
