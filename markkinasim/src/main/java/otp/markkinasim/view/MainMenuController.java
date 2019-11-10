package otp.markkinasim.view;
/**
*
* @author Joonas Lapinlampi
*/
import javafx.fxml.FXML;

public class MainMenuController{
	
	private IView view;
	
	public MainMenuController(View view) {
		this.view = view;
	}
	
	@FXML
	private void startSimulation() {
		view.setScene(3);
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
