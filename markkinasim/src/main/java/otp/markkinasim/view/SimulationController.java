package otp.markkinasim.view;

public class SimulationController implements ISimulationController{

	private IView view;
	
	public SimulationController(View view) {
		this.view = view;
	}
	
	@Override
	public void backToMenu() {
		view.setScene(0);
	}

}
