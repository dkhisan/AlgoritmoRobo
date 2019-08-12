package robot.exception;

public class IsNotStandingByException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IsNotStandingByException() {
        super("o robô nâo está em espera.\n");
    }
}
