package robot.core;

public interface IRobot {
    String DEFAULT_NAME = "T800";
    float DEFAULT_MAX_SPEED = 50.0f,
            SPEED_UP_PERCENT = 0.01f,
            EXPLORE_HEAT_UP_PERCENT = 20.0f,
            MOVE_HEAT_UP_PERCENT = 0.01f,
            AWAKE_TEMPERATURE = 5.0f;
    int DEFAULT_SPEED_UP = 5;
    byte DEFAULT_POWER = 30,
            EXPLORE_DISCHARGE_PERCENT = 20,
            MOVE_DISCHARGE_PERCENT = 1,
            GO_HOME_DISCHARGE_PERCENT = 10,
            STAND_BY = 0,
            EXPLORING = 1,
            STOPPED = 2,
            GOING_HOME = 3,
            CHARGING = 4;

    static String getStatus(byte statusCode) throws IllegalArgumentException {
        switch (statusCode) {
            case STAND_BY:
                return "Em espera";
            case EXPLORING:
                return "Explorando";
            case STOPPED:
                return "Parado";
            case GOING_HOME:
                return "Retornando";
            case CHARGING:
                return "Carregando";
            default:
                throw new IllegalArgumentException("O código informado não é válido.");
        }
    }
}
