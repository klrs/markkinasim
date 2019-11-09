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
import otp.markkinasim.controller.Controller;
import otp.markkinasim.controller.IController;
import otp.markkinasim.controller.Secretary;
import otp.markkinasim.simulation.Simulator;
import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;


public class View extends Application implements IView{
	
	private static IView view;
	private IController dataController;
	
	private MainMenuController MainMenuController;
	private SimulationController SimulationController;
	private SimulationOptionsController SimulationOptionsController;
	
	private Simulator core;
	private static Stage window;
	private Scene mainMenu,simulation,simulationOptions;
	private List<Scene> sceneList = new ArrayList<Scene>();
	
	private ObservableList<Person> personList;
	private ObservableList<Party> partyList;
	private ObservableList<Product> productList;
	
	public View() {
		view = this;
	}
	public void init() {
		//Luodaan perus scenet
		MainMenuController = new MainMenuController(this);
		SimulationController = new SimulationController(this);
		SimulationOptionsController = new SimulationOptionsController(this);
		
		dataController = new Controller(this);
		
		productList = dataController.getProductFromDatabase();
		partyList = dataController.getPartyFromDatabase();
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

			window = primaryStage;
	
			window.setMaximized(true);
			window.setScene(mainMenu);
			window.show();
			window.setMaximized(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static IView getInstance() {
	
		if (view == null) {
			view = new View();
		}
		return view;
	}
	
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
		return partyList;
	}
	@Override
	public ObservableList<Product> getAllProductData(){
		return productList;
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
		return null;
	}
	
	public void createNewObject(Object o) {
		boolean done = dataController.addToDatabase(o);
		if(done) {
			if(o instanceof Party) {
				partyList = dataController.getPartyFromDatabase();
			}else if(o instanceof Product){
				productList = dataController.getProductFromDatabase();
			}
		}else {
			System.out.println("ERROR");
		}
	}
	
	public void removeObject(Object o) {
		boolean done = dataController.removeFromDatabase(o);
		if(done) {
			if(o instanceof Party) {
				partyList = dataController.getPartyFromDatabase();
			}else if(o instanceof Product){
				productList = dataController.getProductFromDatabase();
			}
		}else {
			System.out.println("ERROR");
		}
	}
}

