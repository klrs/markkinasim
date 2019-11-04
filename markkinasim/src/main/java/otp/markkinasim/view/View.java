package otp.markkinasim.view;

import java.io.IOException;
import java.security.InvalidParameterException;
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
import otp.markkinasim.App;
import otp.markkinasim.simulation.Simulator;
import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;


public class View extends Application implements IView{
	
	private static View view;
	
	private MainMenuController MainMenuController;
	private SimulationController SimulationController;
	private SimulationOptionsController SimulationOptionsController;
	
	private App app;
	private Simulator core;
	private static Stage window;
	private Scene mainMenu,simulation,simulationOptions;
	private List<Scene> sceneList = new ArrayList<Scene>();
	
	public void init() {
		//Luodaan perus scenet
		MainMenuController = new MainMenuController(this);
		SimulationController = new SimulationController(this);
		SimulationOptionsController = new SimulationOptionsController(this);
		
		core = Simulator.getInstance();
		app = App.getInstance();
	    core.init(this);
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
		   
			sceneList.add((mainMenu = new Scene(mainMenuParent)));
			sceneList.add((simulation = new Scene(simulationParent)));
			sceneList.add(simulationOptions = new Scene(simulationOptionsParent));
			System.out.println(sceneList);
			window = primaryStage;
			window.setScene(mainMenu);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static View getInstance() {
		//kutsu t�t� funktiota luodaksesi Core olion!
		if (view == null) {
			view = new View();
		}
		return view;
	}
	
	/*public static void main(String[] args) {
		launch(args);
	}*/
	
	@Override
	public void setScene(int id) {
		window.setScene(sceneList.get(id));
	}

	@Override
	public Window getPrimaryStage() {
		return window;
	}
	
	@Override
	public ObservableList<Party> getPartyData() {
		return app.getPartyList();
	}
	@Override
	public ObservableList<Product> getAllProductData(){
		return app.getProductList();
	}
	@Override
	public void writeSimulationLog(String msg) {
		SimulationController.printText(msg);
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
            controller.setView(this);
            controller.setParty(party);
                        
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
    public boolean showProductEditDialog(Product product) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("ProductEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ProductEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setView(this);
            controller.setProduct(product);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
    public boolean showRawmaterialEditDialog(Product product) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("RawmaterialEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Rawmaterial");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            RawmaterialEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRawmaterial(product);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public ObservableList<Person> getPersonData() {
		return app.getPersonList();
	}
}

