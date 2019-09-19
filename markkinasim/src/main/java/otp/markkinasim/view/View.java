package otp.markkinasim.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application {
	
	private Stage window;
	private Scene mainMenu,simulation,simulationOptions;
	@Override
	public void start(Stage primaryStage){
		try {
			//BorderPane root = new BorderPane();
			
			Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenuView.fxml"));
			Parent simulationParent = FXMLLoader.load(getClass().getResource("SimulationView.fxml"));
			Parent simulationOptionsParent = FXMLLoader.load(getClass().getResource("SimulationOptionsView.fxml"));
			
			mainMenu = new Scene(mainMenuParent);
			simulation = new Scene(simulationParent);
			simulationOptions = new Scene(simulationOptionsParent);
			
			primaryStage.setScene(mainMenu);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
