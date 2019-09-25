package otp.markkinasim.view;

import javafx.fxml.FXML;

public class SimulationController implements ISimulationController{

	private IView view;
	
	public SimulationController(View view) {
		this.view = view;
	}
	
	@FXML
	private void backToMenu() {
		view.setScene(0);
	}

}
