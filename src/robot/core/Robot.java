package robot.core;

import robot.exception.*;

public class Robot implements IRobot {
    private String name;
    private float speed;
    private float maxSpeed;
    private float temperature;
    private byte status;
    private byte power;

    public Robot() {
        super();
        this.init(DEFAULT_NAME, DEFAULT_MAX_SPEED, DEFAULT_POWER);
    }

    public Robot(String name) {
        super();
        this.init(name, DEFAULT_MAX_SPEED, DEFAULT_POWER);
    }

    public Robot(String name, float maxSpeed) {
        super();
        this.init(name, maxSpeed, DEFAULT_POWER);
    }

    public Robot(String name, float maxSpeed, byte power) {
        super();
        this.init(name, maxSpeed, power);
    }

    private void init(String name, float maxSpeed, byte power) {
        this.name = name;
        this.speed = 0.0f;
        this.maxSpeed = maxSpeed;
        this.temperature = 0.0f;
        this.status = STAND_BY;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeed() {
        return String.format("%.1f m/s", speed);
    }

    public String getMaxSpeed() {
        return String.format("%.1f m/s", maxSpeed);
    }

    public String getTemperature() {
        return String.format("%.1f ºF", temperature);
    }

    private void setTemperature(float temperature) {
        this.temperature = temperature;
        this.log("A temperatura agora é " + this.getTemperature() + "\n");
    }

    /**
     * Retorna o estado atual do robô.
     *
     * @return texto representando o estado.
     */
    public String getStatus() {
        return IRobot.getStatus(status);
    }

    public String getPower() {
        return power + " %";
    }

    /**
     * Aumenta a temperatura do robô.
     *
     * @param temperature indica a quantidade em Fahrenheit a ser aumentada.
     */
    private void heatUp(float temperature) {
        final float OLD = this.temperature;
        this.temperature += temperature;
        final String MESSAGE = String.format("%s teve sua temperatura aumentada em %.1f ºF.\n\t\tEra %.1f ºF e agora " +
                "é" +
                " " +
                "%s.\n", name, temperature, OLD, getTemperature());
        log(MESSAGE);
    }

    /**
     * Diminui a temperatura do robô.
     *
     * @param temperature indica a quantidade em Fahrenheit a ser reduzida.
     * @throws TemperatureIsZeroException se a temperatura estiver em 0 ºF.
     */
    private void coolDown(float temperature) throws TemperatureIsZeroException {
        final float OLD = this.temperature;
        if (this.temperature == 0.0f) {
            log("Não é possível diminuir a temperatura:\n\t\t");
            throw new TemperatureIsZeroException();
        }
        if (this.temperature - temperature < 0.0f) {
            this.temperature = 0.0f;
        } else {
            this.temperature -= temperature;
        }
        final String MESSAGE = String.format("%s teve sua temperatura reduzida em %.1f ºF.\n\t\tEra %.1f ºF e agora é" +
                " " +
                "%s.\n", name, temperature, OLD, getTemperature());
        log(MESSAGE);
    }

    /**
     * Diminui a velocidade do robô em 10% caso já esteja em movimento.
     *
     * @return instância de Robot.
     * @throws AlreadyStoppedException    se o robô já estiver parado.
     * @throws IsLowChargeException       se a carga da bateria estiver baixa.
     * @throws IsStandByException         se o robô já estiver em espera.
     * @throws IsStoppedException         se o robô já estiver parado.
     * @throws TemperatureIsZeroException se a temperatura estiver em 0 ºF.
     */
    public Robot speedDown() throws AlreadyStoppedException, IsLowChargeException, IsStandByException,
            IsStoppedException, TemperatureIsZeroException {
        final float SPEED = speed * 0.1f;
        if (status == STOPPED) {
            log("Não é possível diminuir a velocidade: ");
            throw new IsStoppedException();
        }
        speedDown(SPEED);
        return this;
    }

    /**
     * Diminui a velocidade do robô na velocidade passada como argumento caso já esteja em movimento.
     *
     * @param speed indica a velocidade em m/s a ser aumentada.
     * @return instância de Robot.
     * @throws AlreadyStoppedException    se o robô já estiver parado.
     * @throws IsLowChargeException       se a carga da bateria estiver baixa.
     * @throws IsStandByException         se o robô já estiver em espera.
     * @throws IsStoppedException         se o robô já estiver parado.
     * @throws TemperatureIsZeroException se a temperatura estiver em 0 ºF.
     */
    public Robot speedDown(float speed) throws AlreadyStoppedException, IsLowChargeException, IsStandByException,
            IsStoppedException, TemperatureIsZeroException {
        final float OLD = this.speed;
        if (status == STOPPED) {
            log("Não é possível diminuir a velocidade: ");
            throw new IsStoppedException();
        }
        canExecute();
        if (this.speed - speed < 0.0f) {
            stop();
        } else {
            final String MESSAGE = String.format("%s teve sua velocidade reduzida em %.1f m/s.\n\t\tEra %.1f m/s e " +
                            "agora é %s.\n",
                    name, speed, OLD, getSpeed());
            log(MESSAGE);
            coolDown(speed / 2.0f);
        }
        return this;
    }

    /**
     * Aumenta a velocidade em 10% caso já esteja em movimento.
     *
     * @return instância de Robot.
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     * @throws IsLowChargeException       se a carga da bateria estiver baixa.
     * @throws IsNotExploringException    se o robô não estiver explorando.
     * @throws IsStandByException         se o robô já está em espera.
     * @throws MaxSpeedReachedException   se o robô já tiver alcançado a velocidade máxima estabalecida.
     */
    public Robot speedUp() throws AlreadyStandingByException, IsLowChargeException, IsNotExploringException,
            IsStandByException, MaxSpeedReachedException {
        final float SPEED = speed * SPEED_UP_PERCENT;
        if (SPEED == 0.0f) {
            speedUp(DEFAULT_SPEED_UP);
        } else {
            speedUp(SPEED);
        }
        return this;
    }

    /**
     * Aumenta a velocidade em 10% caso já esteja em movimento.
     *
     * @param speed indica a velocidade a ser aumentada.
     * @return instância de Robot
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     * @throws IsLowChargeException       se a carga da bateria estiver baixa.
     * @throws IsNotExploringException    se o robô não estiver explorando.
     * @throws IsStandByException         se o robô já está em espera.
     * @throws MaxSpeedReachedException   se o robô já tiver alcançado a velocidade máxima estabalecida.
     */
    public Robot speedUp(float speed) throws AlreadyStandingByException, IsLowChargeException, IsNotExploringException,
            IsStandByException, MaxSpeedReachedException {
        final float OLD = this.speed;
        if (speed == this.maxSpeed) {
            this.log("Não é possível aumentar a velocidade: ");
            throw new MaxSpeedReachedException();
        }
        this.canExecute();
        if (this.status != EXPLORING) {
            this.log("Não é possível aumentar a velocidade: ");
            throw new IsNotExploringException();
        }
        if (this.speed + speed > this.maxSpeed) {
            this.speed = this.maxSpeed;
        } else {
            this.speed += speed;
        }
        final String MESSAGE = String.format("%s teve sua velocidade aumentada em %.1f m/s.\n\t\tEra %.1f m/s e agora" +
                        " é" +
                        " %s.\n",
                name, speed, OLD, getSpeed());
        this.log(MESSAGE);
        this.heatUp(speed / 2.0f);
        this.discharge((byte) ((byte) speed / 4));
        return this;
    }

    /**
     * Altera o estado do robô para <tt>STOPPED</tt>, parado.
     *
     * @return instância de Robot.
     * @throws AlreadyStoppedException    se o robô já estiver parado.
     * @throws IsLowChargeException       se a carga da bateria estiver baixa.
     * @throws IsStandByException         se o robô estiver em espera.
     * @throws TemperatureIsZeroException se a temperatura estiver em 0 ºF.
     */
    public Robot stop() throws AlreadyStoppedException, IsLowChargeException, IsStandByException,
            TemperatureIsZeroException {
        if (status == STOPPED) {
            log("Não é possível alternar para parado:\n\t\t");
            throw new AlreadyStoppedException();
        }
        canExecute();
        log(name + " agora está parado.\n");
        status = STOPPED;
        speed = 0.0f;
        coolDown(temperature * 0.1f);
        return this;
    }

    /**
     * Altera o estado do robô para <tt>STAND_BY</tt> (em espera).
     *
     * @return instância de Robot.
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     */
    public Robot standBy() throws AlreadyStandingByException {
        if (status == STAND_BY) {
            log("Não é possível alternar para em espera:\n\t\t");
            throw new AlreadyStandingByException();
        }
        log(name + " agora está em espera.\n");
        speed = 0.0f;
        status = STAND_BY;
        setTemperature(0.0f);
        return this;
    }

    /**
     * Altera o estado do robô para <tt>STAND_BY</tt> (em espera).
     *
     * @param reason texto explicando o motivo da alteração do estado.
     * @return instância de Robot.
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     */
    private Robot standBy(String reason) throws AlreadyStandingByException {
        if (status == STAND_BY) {
            log("Não é possível alternar para em espera:\n\t\t");
            throw new AlreadyStandingByException();
        }
        log(name + " foi colocado em espera automaticamente,\n\t\tdevido: " + reason + "\n");
        speed = 0.0f;
        status = STAND_BY;
        setTemperature(0.0f);
        return this;
    }

    /**
     * Altera o estado do robô para <tt>EXPLORING</tt> (explorando).
     *
     * @return instância de Robot.
     * @throws AlreadyExploringException  se o robô já estiver explorando.
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     * @throws IsLowChargeException       se a carga da bateria do robô estiver baixa.
     * @throws IsStandByException         se o robo está em espera.
     */
    public Robot explore() throws AlreadyExploringException, AlreadyStandingByException, IsLowChargeException,
            IsStandByException {
        if (status == EXPLORING) {
            log("Não é possível explorar: ");
            throw new AlreadyExploringException();
        }
        canExecute();
        status = EXPLORING;
        log(name + " está explorando.\n");
        heatUp(EXPLORE_HEAT_UP_PERCENT);
        discharge(EXPLORE_DISCHARGE_PERCENT);
        return this;
    }

    /**
     * Movimenta o robô explorando o ambiente.
     *
     * @return instância de Robot
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     * @throws IsLowChargeException       se a carga da bateria estiver baixa.
     * @throws IsNotExploringException    se o robô não estiver explorando.
     * @throws IsStandByException         se o robô estiver em espera.
     */
    public Robot move() throws AlreadyStandingByException, IsLowChargeException, IsNotExploringException,
            IsStandByException {
        if (status != EXPLORING) {
            log("Não é possível vasculhar: ");
            throw new IsNotExploringException();
        }
        canExecute();
        log(name + " está vasculhando o local.\n");
        heatUp(speed * MOVE_HEAT_UP_PERCENT);
        discharge(MOVE_DISCHARGE_PERCENT);
        return this;
    }

    /**
     * Altera o estado do robô para <tt>GOING_HOME</tt> (retornando para casa).
     *
     * @return instância de Robot.
     * @throws AlreadyGoingHomeException  se o robô já estiver retornando.
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     * @throws IsLowChargeException       se a carga da bateria estiver baixa.
     * @throws IsNotExploringException    se o robô não estiver explorando.
     */
    public Robot back() throws AlreadyGoingHomeException, AlreadyStandingByException, IsLowChargeException,
            IsNotExploringException, IsStandByException {
        if (status != EXPLORING) {
            log("Não é possível retornar: ");
            throw new IsNotExploringException();
        }
        if (status == GOING_HOME) {
            log("Não é possível retornar: ");
            throw new AlreadyGoingHomeException();
        }
        canExecute();
        log(name + " está retornando.\n");
        status = GOING_HOME;
        discharge(GO_HOME_DISCHARGE_PERCENT);
        return this;
    }

    /**
     * Aumenta a carga da bateria em 10%.
     *
     * @return instância de Robot
     * @throws AlreadyChargingException    se o robô já estiver carregando.
     * @throws AlreadyFullChargedException se a carga da bateria do robô já estiver em 100%.
     * @throws IsExploringException        se o robô estiver explorando.
     * @throws IsNotStoppedException       se o robô não estiver parado.
     */
    public Robot charge() throws AlreadyChargingException, AlreadyFullChargedException, IsExploringException,
            IsNotStoppedException {
        final int OLD = power;
        final byte percent = (byte) (power * 0.1);
        if (status != STAND_BY || status != STOPPED) {
            log("Não é possível carregar: ");
        }
        if (status == CHARGING) {
            throw new AlreadyChargingException();
        }
        if (status == EXPLORING) {
            throw new IsExploringException();
        }
        if (status == GOING_HOME) {
            throw new IsNotStoppedException();
        }
        if (power == 100) {
            throw new AlreadyFullChargedException();
        }
        if (power * percent > 100) {
            power = 100;
        } else {
            power += percent;
        }
        final String MESSAGE = String.format("%s teve sua bateria carregada em %d %%.\n\t\tEra %d %% e agora é %s.\n",
                name, percent, OLD, getPower());
        log(MESSAGE);
        return this;
    }

    /**
     * Diminui o percentual da bateria.
     *
     * @param percent indica o percentual a ser subtraído da carga atual.
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     */
    private void discharge(byte percent) throws AlreadyStandingByException {
        final byte OLD = power;
        if (power - percent > 0) {
            power -= percent;
        } else {
            power = 0;
            standBy("carga da bateria baixa.");
        }
        final String MESSAGE = String.format("%s teve sua bateria descarregada em %d %%.\n\t\tEra %d %% e agora é %s" +
                        ".\n",
                name, percent, OLD, getPower());
        log(MESSAGE);
    }

    /**
     * Passo para verificar se o robô pode executar uma ação previamente.
     *
     * @throws IsLowChargeException se a carga da bateria estiver em 0%.
     * @throws IsStandByException   se o robô não estiver acordado.
     */
    private void canExecute() throws IsLowChargeException, IsStandByException {
        if (!(power > 0)) {
            log("Não é possível executar esta ação:\n\t\t");
            throw new IsLowChargeException();
        }
        if (status == STAND_BY) {
            log("Não é possível executar esta ação:\n\t\t");
            throw new IsStandByException();
        }
    }

    /**
     * Liga/acorda o robô, alterando seu estado para <tt>STOPPED</tt> (parado).
     *
     * @return instância de Robot.
     * @throws IsLowChargeException     se a carga da bateria estiver baixa.
     * @throws IsNotStandingByException se o robô não estiver em espera.
     */
    public Robot wake() throws IsLowChargeException, IsNotStandingByException {
        if (status != STAND_BY) {
            log("Não é possível acordar: ");
            throw new IsNotStandingByException();
        }
        if (!(power > 0)) {
            log("Não é possível acordar: ");
            throw new IsLowChargeException();
        }
        status = STOPPED;
        log(name + " foi ativado.\n");
        setTemperature(AWAKE_TEMPERATURE);
        return this;
    }

    /**
     * Desliga o robô, alterando seu estado para <tt>STAND_BY</tt> (em espera).
     *
     * @return instância de Robot.
     * @throws AlreadyStandingByException se o robô já estiver em espera.
     */
    public Robot turnOff() throws AlreadyStandingByException {
        standBy();
        log(name + " foi desativado.\n");
        return this;
    }

    /**
     * Exibe no console todos os atributos do robô atualmente.
     *
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
        return "\n+------------------------------------------+\n"
                + " << ROBOT >>\n"
                + " [Nome] " + name + "\n"
                + " [Vel. atual] " + getSpeed() + " de " + getMaxSpeed() + "\n"
                + " [Temperatura] " + getTemperature() + "\n"
                + " [Bateria] " + getPower() + "\n"
                + " [Ação] " + getStatus() + "\n"
                + "+------------------------------------------+\n";
    }
}
