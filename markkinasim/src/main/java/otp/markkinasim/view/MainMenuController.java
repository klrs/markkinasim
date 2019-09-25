package otp.markkinasim.view;

import javafx.fxml.FXML;

public class MainMenuController implements IMainMenuController{
	
	private IView view;
	
	public MainMenuController(View view2) {
		view = view2;
	}
	
	@FXML
	private void startSimulation() {
		view.setScene(1);
	}
	
	@FXML
	private void simulationOptions() {
		view.setScene(2);
	}
	
	@FXML
	private void exitButton() {
		System.exit(0);
	}
}
