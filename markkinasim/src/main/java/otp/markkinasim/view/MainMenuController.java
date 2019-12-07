package otp.markkinasim.view;

import javafx.fxml.FXML;
/**
* Tämä luokka kontrolloi main menu ikkunaa.
* 
* @author Joonas Lapinlampi
*/
public class MainMenuController{
	
	private IView view;
	
	public MainMenuController(View view) {
		this.view = view;
	}
	
	//vaihdetaan ikkuna simulationSelection ikkunaksi
	@FXML
	private void startSimulation() {
		view.setScene(3);
	}
	
	//vaihdetaan ikkuna simulationOptions ikkunaksi
	@FXML
	private void simulationOptions() {
		view.setScene(2);
	}
	
	//sulkee ohjelman
	@FXML
	private void exitButton() {
		System.exit(0);
	}
}
