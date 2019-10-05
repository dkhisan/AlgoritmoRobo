import robot.core.Robot;

public class TestFlight {
	public static void main(String[] args) {
		final Robot matias = new Robot("Matias", 80.0f, (byte) 60);
		try {
			matias
				.wake()
				.explore()
				.speedUp(40.0f)
				.move()
				.move()
				.move()
				.back()
				.stop()
				.turnOff()
				.show();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
