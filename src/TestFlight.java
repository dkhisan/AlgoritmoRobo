import robot.core.Robot;

public class TestFlight {
	public static void main(String[] args) {
		final Robot matias = new Robot("Matias", 80.0f, 10);
		try {
			matias
				.wake()
				.speedUp(40.0f)
				.explore()
				.stop()
				.stop()
				.explore()
				.stop()
				.explore()
				.wake()
				.show();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
