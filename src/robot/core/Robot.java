package robot.core;

import robot.common.Status;
import robot.exception.*;

public class Robot {
	private String name;
	private float speed;
	private float maxSpeed;
	private float temperature;
	private byte status;
	private int power;
	
	public Robot() {
		super();
		this.name = "JBOT-001";
		this.speed = 0.0f;
		this.maxSpeed = 50.0f;
		this.temperature = 0.0f;
		this.status = (byte) Status.STAND_BY.ordinal();
		this.power = 30;
	}
	public Robot(String name) {
		super();
		this.name = name;
		this.speed = 0.0f;
		this.maxSpeed = 50.0f;
		this.temperature = 0.0f;
		this.status = (byte) Status.STAND_BY.ordinal();
		this.power = 30;
	}
	public Robot(String name, float maxSpeed) {
		super();
		this.name = name;
		this.speed = 0.0f;
		this.maxSpeed = maxSpeed;
		this.temperature = 0.0f;
		this.status = (byte) Status.STAND_BY.ordinal();;
		this.power = 30;
	}
	public Robot(String name, float maxSpeed, int power) {
		super();
		this.name = name;
		this.speed = 0.0f;
		this.maxSpeed = maxSpeed;
		this.temperature = 0.0f;
		this.status = (byte) Status.STAND_BY.ordinal();;
		this.power = power;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpeed() {
		return speed + " m/s";
	}
	public String getMaxSpeed() {
		return maxSpeed + " m/s";
	}
	public String getTemperature() {
		return temperature + " ºF";
	}
	private void setTemperature(float temperature) {
		this.temperature = temperature;
		this.log("A temperatura agora é " + this.getTemperature() + "\n");
	}

	/**
	 * Retorna o estado atual do robô.
	 * @return texto representando o estado.
	 */
	public String getStatus() {
		return Status.getStatus(this.status);
	}
	public String getPower() {
		return power + " %";
	}

	/**
	 * Aumenta a temperatura do robô.
	 * @param temperature indica a quantidade em Fahrenheit a ser aumentada.
	 */
	private void heatUp(float temperature) {
		final float OLD = this.temperature;
		this.temperature += temperature;
		this.log(this.name + " teve sua temperatura aumentada em " + temperature + " ºF.\n\t\t"
				+ "Era " + OLD + " ºF e agora é " + this.getTemperature() + ".\n");
	}

	/**
	 * Diminui a temperatura do robô.
	 * @param temperature indica a quantidade em Fahrenheit a ser reduzida.
	 */
	private void coolDown(float temperature) throws TemperatureIsZeroException {
		final float OLD = this.temperature;
		if (this.temperature == 0.0f) {
			this.log("Não é possível diminuir a temperatura:\n\t\t");
			throw new TemperatureIsZeroException();
		}
		if (this.temperature - temperature < 0.0f) {
			this.temperature = 0.0f;
		} else {
			this.temperature -= temperature;
		}
		this.log(this.name + " teve sua temperatura reduzida em " + temperature + " ºF.\n\t\t"
				+ "Era " + OLD + " ºF e agora é " + this.getTemperature() + ".\n");
	}

	/**
	 * Diminui a velocidade do robô em 10% caso já esteja em movimento.
	 * @return instância de Robot.
	 * @throws AlreadyStoppedException se o robô já estiver parado.
	 * @throws IsLowChargeException se a carga da bateria estiver baixa.
	 * @throws IsStandByException se o robô já estiver em espera.
	 * @throws IsStoppedException se o robô já estiver parado.
	 * @throws TemperatureIsZeroException se a temperatura estiver em 0 ºF.
	 */
	public Robot speedDown() throws AlreadyStoppedException, IsLowChargeException, IsStandByException
								, IsStoppedException, TemperatureIsZeroException {
		final byte STOPPED = (byte) Status.STOPPED.ordinal();
		final float SPEED = this.speed * 0.1f;
		final float OLD = this.speed;
		if (this.status == STOPPED) {
			this.log("Não é possível diminuir a velocidade: ");
			throw new IsStoppedException();
		}
		this.execute();
		if (this.speed - SPEED < 0.0f) {
			this.stop();
		} else {
			this.speed -= SPEED;
			this.log(this.name + " teve sua velocidade reduzida em " + SPEED + " m/s.\n\t\t"
					+ "Era " + OLD + " m/s e agora é " + this.getSpeed() + ".\n");
			this.coolDown(SPEED / 2.0f);
		}
		return this;
	}

	/**
	 * Diminui a velocidade do robô na velocidade passada como argumento caso já esteja em movimento.
	 * @param speed indica a velocidade em m/s a ser aumentada.
	 * @return instância de Robot.
	 * @throws AlreadyStoppedException se o robô já estiver parado.
	 * @throws IsLowChargeException se a carga da bateria estiver baixa.
	 * @throws IsStandByException se o robô já estiver em espera.
	 * @throws IsStoppedException se o robô já estiver parado.
	 * @throws TemperatureIsZeroException se a temperatura estiver em 0 ºF.
	 */
	public Robot speedDown(float speed) throws AlreadyStoppedException, IsLowChargeException, IsStandByException
											, IsStoppedException, TemperatureIsZeroException {
		final byte STOPPED = (byte) Status.STOPPED.ordinal();
		final float OLD = this.speed;
		if (this.status == STOPPED) {
			this.log("Não é possível diminuir a velocidade: ");
			throw new IsStoppedException();
		}
		this.execute();
		if (this.speed - speed < 0.0f) {
			this.stop();
		} else {
			this.speed -= speed;
			this.log(this.name + " teve sua velocidade reduzida em " + speed + " m/s.\n\t\t"
					+ "Era " + OLD + " m/s e agora é " + this.getSpeed() + ".\n");
			this.coolDown(speed / 2.0f);
		}
		return this;
	}

	/**
	 * Aumenta a velocidade em 10% caso já esteja em movimento.
	 * @return instância de Robot.
	 * @throws MaxSpeedReachedException se o robô já tiver alcançado a velocidade máxima estabalecida.
	 */
	public Robot speedUp() throws MaxSpeedReachedException {
		final float SPEED = this.speed * 0.1f;
		final float OLD = this.speed;
		if (this.speed == this.maxSpeed) {
			this.log("Não é possível aumentar a velocidade: ");
			throw new MaxSpeedReachedException();
		}
		try {
			if (SPEED == 0.0f) {
				this.speedUp(5);
				return this;
			} else {
				this.execute();
			}
			if (this.speed + SPEED > this.maxSpeed) {
				this.speed = this.maxSpeed;
			} else {
				this.speed += SPEED;
			}
			this.log(this.name + " teve sua velocidade aumentada em " + SPEED + " m/s.\n\t\t"
					+ "Era " + OLD + " m/s e agora é " + this.getSpeed() + ".\n");
			this.heatUp(SPEED / 2.0f);
			this.discharge((int) SPEED / 4);
		} catch (Exception e) {
			this.log(e.getMessage());
		}
		return this;
	}

	/**
	 * Aumenta a velocidade em 10% caso já esteja em movimento.
	 * @param speed indica a velocidade a ser aumentada.
	 * @return instância de Robot
	 * @throws AlreadyStandingByException se o robô já estiver em espera.
	 * @throws IsLowChargeException se a carga da bateria estiver baixa.
	 * @throws IsStandByException se o robô já estiver em espera.
	 * @throws MaxSpeedReachedException se o robô já tiver alcançado a velocidade máxima estabalecida.
	 */
	public Robot speedUp(float speed) throws AlreadyStandingByException, IsLowChargeException, IsStandByException
											, MaxSpeedReachedException  {
		final float OLD = this.speed;
		if (this.speed == this.maxSpeed) {
			this.log("Não é possível aumentar a velocidade: ");
			throw new MaxSpeedReachedException();
		}
		this.execute();
		if (this.speed + speed > this.maxSpeed) {
			this.speed = this.maxSpeed;
		} else {
			this.speed += speed;
		}
		this.log(this.name + " teve sua velocidade aumentada em " + speed + " m/s.\n\t\t"
				+ "Era " + OLD + " m/s e agora é " + this.getSpeed() + ".\n");
		this.heatUp(speed / 2.0f);
		this.discharge((int) speed / 4);
		return this;
	}

	/**
	 * Altera o estado do robô para <tt>STOPPED</tt>, parado.
	 * @return instância de Robot.
	 * @throws AlreadyStoppedException se o robô já estiver parado.
	 * @throws IsLowChargeException se a carga da bateria estiver baixa.
	 * @throws IsStandByException se o robô estiver em espera.
	 * @throws TemperatureIsZeroException se a temperatura estiver em 0 ºF.
	 */
	public Robot stop() throws AlreadyStoppedException, IsLowChargeException, IsStandByException
							, TemperatureIsZeroException {
		final byte STOPPED = (byte) Status.STOPPED.ordinal();
		if (this.status == STOPPED) {
			this.log("Não é possível alternar para parado:\n\t\t");
			throw new AlreadyStoppedException();
		}
		this.execute();
		this.log(this.name + " agora está parado.\n");
		this.status = STOPPED;
		this.coolDown(this.temperature * 0.1f);
		return this;
	}

	/**
	 * Altera o estado do robô para <tt>STAND_BY</tt>, em espera.
	 * @return instância de Robot.
	 * @throws AlreadyStandingByException se o robô já estiver em espera.
	 */
	public Robot standBy() throws AlreadyStandingByException {
		final byte STAND_BY = (byte) Status.STAND_BY.ordinal();
		if (this.status == STAND_BY) {
			this.log("Não é possível alternar para em espera:\n\t\t");
			throw new AlreadyStandingByException();
		}
		this.log(this.name + " agora está em espera.\n");
		this.speed = 0.0f;
		this.status = STAND_BY;
		this.setTemperature(0.0f);
		return this;
	}

	/**
	 * Altera o estado do robô para <tt>STAND_BY</tt>, em espera.
	 * @param reason texto explicando o motivo da alteração do estado.
	 * @return instância de Robot.
	 * @throws AlreadyStandingByException se o robô já estiver em espera.
	 */
	public Robot standBy(String reason) throws AlreadyStandingByException {
		final byte STAND_BY = (byte) Status.STAND_BY.ordinal();
		if (this.status == STAND_BY) {
			this.log("Não é possível alternar para em espera:\n\t\t");
			throw new AlreadyStandingByException();
		}
		this.log(this.name + " foi colocado em espera automaticamente,\n\t\tdevido: " + reason + "\n");
		this.speed = 0.0f;
		this.status = STAND_BY;
		this.setTemperature(0.0f);
		return this;
	}

	/**
	 * Altera o estado do robô para <tt>EXPLORING</tt>, explorando.
	 * @return instância de Robot.
	 * @throws AlreadyExploringException se o robô já estiver explorando.
	 * @throws AlreadyStandingByException se o robô já estiver em espera.
	 * @throws IsLowChargeException se a carga da bateria do robô estiver baixa.
	 * @throws IsStandByException se o robo está em espera.
	 */
	public Robot explore() throws AlreadyExploringException, AlreadyStandingByException, IsLowChargeException
								, IsStandByException {
		final byte EXPLORING = (byte) Status.EXPLORING.ordinal();
		if (this.status == EXPLORING) {
			this.log("Não é possível explorar: ");
			throw new AlreadyExploringException();
		}
		this.execute();
		this.log(this.name + " está explorando.\n");
		this.status = EXPLORING;
		this.heatUp(20.0f);
		this.discharge(20);
		return this;
	}
	public Robot move() throws IsNotExploringException {
		final byte EXPLORING = (byte) Status.EXPLORING.ordinal();
		if (this.status != EXPLORING) {
			this.log("Não é possível vasculhar: ");
			throw new IsNotExploringException();
		}
		try {
			this.execute();
			this.log(this.name + " está vasculhando o local.\n");
			this.heatUp(this.speed * 0.01f);
			this.discharge(1);
		} catch (Exception e) {
			this.log(e.getMessage());
		}
		return this;
	}

	/**
	 * Altera o estado do robô para <tt>GOING_HOME</tt>, retornando para casa.
	 * @return instância de Robot.
	 * @throws AlreadyGoingHomeException se o robô já estiver retornando.
	 * @throws AlreadyStandingByException se o robô já estiver em espera.
	 * @throws IsLowChargeException se a carga da bateria estiver baixa.
	 * @throws IsNotExploringException se o robô não estiver explorando.
	 */
	public Robot back() throws AlreadyGoingHomeException, AlreadyStandingByException, IsLowChargeException
							, IsNotExploringException, IsStandByException {
		final byte EXPLORING = (byte) Status.EXPLORING.ordinal();
		final byte GOING_HOME = (byte) Status.GOING_HOME.ordinal();
		if (this.status != EXPLORING) {
			this.log("Não é possível retornar: ");
			throw new IsNotExploringException();
		}
		if (this.status == GOING_HOME) {
			this.log("Não é possível retornar: ");
			throw new AlreadyGoingHomeException();
		}
		this.execute();
		this.log(this.name + " está retornando.\n");
		this.status = GOING_HOME;
		this.discharge(10);
		return this;
	}

	/**
	 * Aumenta a carga da bateria em 10%.
	 * @return instância de Robot
	 * @throws AlreadyChargingException se o robô já estiver carregando.
	 * @throws AlreadyFullChargedException se a carga da bateria do robô já estiver em 100%.
	 * @throws IsExploringException se o robô estiver explorando.
	 * @throws IsNotStoppedException se o robô não estiver parado.
	 */
	public Robot charge() throws AlreadyChargingException, AlreadyFullChargedException, IsExploringException
								, IsNotStoppedException {
		final byte EXPLORING = (byte) Status.EXPLORING.ordinal();
		final byte GOING_HOME = (byte) Status.GOING_HOME.ordinal();
		final byte CHARGING = (byte) Status.CHARGING.ordinal();
		final float OLD = this.power;
		final int percent = (int) (this.power * 0.1);
		if (this.status == CHARGING) {
			this.log("Não é possível carregar: ");
			throw new AlreadyChargingException();
		}
		if (this.status == EXPLORING) {
			this.log("Não é possível carregar: ");
			throw new IsExploringException();
		}
		if (this.status == GOING_HOME) {
			this.log("Não é possível carregar: ");
			throw new IsNotStoppedException();
		}
		if (this.power == 100) {
			this.log("Não é possível carregar: ");
			throw new AlreadyFullChargedException();
		}
		if (this.power * percent > 100) {
			this.power = 100;
		} else {
			this.power += percent;
		}
		this.log(this.name + " teve sua bateria carregada em " + percent + " %.\n\t\t"
				+ "Era " + OLD + " % e agora é " + this.getPower() + ".\n");
		return this;
	}

	/**
	 * Diminui o percentual da bateria.
	 * @param percent indica o percentual a ser subtraído da carga atual.
	 * @throws AlreadyStandingByException se o robô já estiver em espera.
	 */
	private void discharge(int percent) throws AlreadyStandingByException {
		final int OLD = this.power;
		if (this.power - percent > 0) {
			this.power -= percent;
		} else {
			this.power = 0;
			this.standBy("carga da bateria baixa.");
		}
		this.log(this.name + " teve sua bateria descarregada em " + percent + " %.\n\t\t"
				+ "Era " + OLD + " % e agora é " + this.getPower() + ".\n");
	}

	/**
	 * Passo para verificar se o robô pode executar uma ação previamente.
	 * @throws IsLowChargeException se a carga da bateria estiver em 0%.
	 * @throws IsStandByException se o robô não estiver acordado.
	 */
	private void execute() throws IsLowChargeException, IsStandByException {
		final byte STAND_BY = (byte) Status.STAND_BY.ordinal();
		if (!(this.power > 0)) {
			this.log("Não é possível executar esta ação:\n\t\t");
			throw new IsLowChargeException();
		}
		if (this.status == STAND_BY) {
			this.log("Não é possível executar esta ação:\n\t\t");
			throw new IsStandByException();
		}
	}

	/**
	 * Liga/acorda o robô, alterando seu estado para <tt>STOPPED</tt>, parado.
	 * @return instância de Robot.
	 * @throws IsLowChargeException se a carga da bateria estiver baixa.
	 * @throws IsNotStandingByException se o robô não estiver em espera.
	 */
	public Robot wake() throws IsLowChargeException, IsNotStandingByException {
		final byte STAND_BY = (byte) Status.STAND_BY.ordinal();
		final byte STOPPED = (byte) Status.STOPPED.ordinal();
		if (this.status != STAND_BY) {
			this.log("Não é possível acordar: ");
			throw new IsNotStandingByException();
		}
		if (!(this.power > 0)) {
			this.log("Não é possível acordar: ");
			throw new IsLowChargeException();
		}
		this.status = STOPPED;
		this.log(this.name + " foi ativado.\n");
		this.setTemperature(5.0f);
		return this;
	}

	/**
	 * Desliga o robô, alterando seu estado para <tt>STAND_BY</tt>, em espera.
	 * @return instância de Robot.
	 * @throws AlreadyStandingByException se o robô já estiver em espera.
	 */
	public Robot off() throws AlreadyStandingByException {
		this.standBy();
		this.log(this.name + " foi desativado.\n");
		return this;
	}

	/**
	 * Exibe no console todos os atributos do robô atualmente.
	 * @return instância de Robot
	 */
	public Robot show() {
		System.out.println(this);
		return this;
	}
	private void log(String message) {
		System.out.print("[ROBOT] " + message);
	}
	@Override
	public String toString() {
		return "+------------------------------------------+\n"
				+ "<< ROBOT >>\n"
				+ "[Nome] " + this.name + "\n"
				+ "[Vel. atual] " + this.getSpeed() + " de " + this.getMaxSpeed() + "\n"
				+ "[Temperatura] " + this.getTemperature() + "\n"
				+ "[Bateria] " + this.getPower() + "\n"
				+ "[Ação] " + this.getStatus() + "\n"
				+ "+------------------------------------------+";
	}
}
