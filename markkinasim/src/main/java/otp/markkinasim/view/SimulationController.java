package otp.markkinasim.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import otp.markkinasim.model.Core;

public class SimulationController{

	private IView view;
	private Core core;
	@FXML
	private TextArea simulationLog;
	
	public SimulationController(View view) {
		this.view = view;
		core = Core.getInstance();
	}
	
	@FXML
	private void nextRound() {
		core.start();
	}
	@FXML
	private void backToMenu() {
		view.setScene(0);
	}
	
	public void printText(String msg) {
		simulationLog.appendText(msg);
	}
}
