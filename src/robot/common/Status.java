package robot.common;

import java.security.InvalidParameterException;

public enum Status {
	STAND_BY, EXPLORING, STOPPED, GOING_HOME, CHARGING;

	public static String getStatus(int statusCode) throws InvalidParameterException {
		if (statusCode == STAND_BY.ordinal()) {
			return "Em espera";
		}
		if (statusCode == EXPLORING.ordinal()) {
			return "Explorando";
		}
		if (statusCode == STOPPED.ordinal()) {
			return "Parado";
		}
		if (statusCode == GOING_HOME.ordinal()) {
			return "Retornando";
		}
		if (statusCode == CHARGING.ordinal()) {
			return "Carregando";
		}
		throw new InvalidParameterException("O código informado não é válido.");
	}
}
