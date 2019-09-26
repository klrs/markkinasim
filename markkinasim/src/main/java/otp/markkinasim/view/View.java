package otp.markkinasim.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import otp.markkinasim.model.Party;

public class View extends Application implements IView{
	
	private IMainMenuController MainMenuController;
	private ISimulationController SimulationController;
	private ISimulationOptionsController SimulationOptionsController;
	
	private Stage window;
	private Scene mainMenu,simulation,simulationOptions;
	private List<Scene> sceneList = new ArrayList<Scene>();
	private ObservableList<Party> partyData = FXCollections.observableArrayList();
	
	public void init() {
		MainMenuController = new MainMenuController(this);
		SimulationController = new SimulationController(this);
		SimulationOptionsController = new SimulationOptionsController(this);
	}
	
	@Override
	public void start(Stage primaryStage){
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
		    loader.setController(MainMenuController);
		    Parent mainMenuParent = loader.load();
		    loader = new FXMLLoader(getClass().getResource("SimulationView.fxml"));
		    loader.setController(SimulationController);
		    Parent simulationParent = loader.load();
		    loader = new FXMLLoader(getClass().getResource("SimulationOptionsView.fxml"));
		    loader.setController(SimulationOptionsController);
		    Parent simulationOptionsParent = loader.load();
			/*
			Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenuView.fxml"));
			Parent simulationParent = FXMLLoader.load(getClass().getResource("SimulationView.fxml"));
			Parent simulationOptionsParent = FXMLLoader.load(getClass().getResource("SimulationOptionsView.fxml"));
			*/
			sceneList.add((mainMenu = new Scene(mainMenuParent)));
			sceneList.add((simulation = new Scene(simulationParent)));
			sceneList.add(simulationOptions = new Scene(simulationOptionsParent));
			window = primaryStage;
			window.setScene(mainMenu);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void setScene(int id) {
		window.setScene(sceneList.get(id));
	}

	@Override
	public Window getPrimaryStage() {
		// TODO Auto-generated method stub
		return window;
	}
	
	@Override
	public ObservableList<Party> getPartyData() {
		return partyData;
	}
	
	@Override
    public boolean showPartyEditDialog(Party party) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("PartyEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Party");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PartyEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setParty(party);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
}
