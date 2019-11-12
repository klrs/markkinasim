package otp.markkinasim.view;
/**
*
* @author Joonas Lapinlampi
*/
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import otp.markkinasim.controller.Controller;
import otp.markkinasim.controller.IController;
import otp.markkinasim.simulation.Simulator;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;

public class View extends Application implements IView{
	
	private static IView view;
	private IController dataController;
	private int personCount, simulationTime;
	private MainMenuController MainMenuController;
	private SimulationController SimulationController;
	private SimulationOptionsController SimulationOptionsController;
	private SimulationSelectionController SimulationSelectionController;
	private static Stage window;
	private Scene scene;
	private List<Parent> sceneList = new ArrayList<Parent>();
	
	private ObservableList<Person> personList;
	private ObservableList<Party> partyList;
	private ObservableList<Product> productList;
	private ObservableList<Party> simulationPartyList;
	private ObservableList<Product> simulationProductList;
	private Locale locale = Locale.ENGLISH;
	public View() {
		view = this;
	}
	public void init() {
		//Luodaan perus scenet
		MainMenuController = new MainMenuController(this);
		SimulationController = new SimulationController(this);
		SimulationOptionsController = new SimulationOptionsController(this);
		SimulationSelectionController = new SimulationSelectionController(this);
		
		dataController = new Controller(this);
		
		productList = dataController.getProductFromDatabase();
		partyList = dataController.getPartyFromDatabase();
		simulationPartyList = FXCollections.observableArrayList();
		simulationProductList = FXCollections.observableArrayList();
		personList = FXCollections.observableArrayList();
	}
	
	@Override
	public void start(Stage primaryStage){
		try {
			Locale locale = Locale.ENGLISH;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
			loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));			
		    loader.setController(MainMenuController);
		    Parent mainMenuParent = loader.load();
		    
		    loader = new FXMLLoader(getClass().getResource("SimulationView.fxml"));
		    loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));	
		    loader.setController(SimulationController);
		    Parent simulationParent = loader.load();
		    
		    loader = new FXMLLoader(getClass().getResource("SimulationOptionsView.fxml"));
		    loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));	
		    loader.setController(SimulationOptionsController);
		    Parent simulationOptionsParent = loader.load();
		    
		    loader = new FXMLLoader(getClass().getResource("SimulationSelection.fxml"));
		    loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));	
		    loader.setController(SimulationSelectionController);
		    Parent simulationSelectionParent = loader.load();
		    
		    
			sceneList.add(mainMenuParent);
			sceneList.add(simulationParent);
			sceneList.add(simulationOptionsParent);
			sceneList.add(simulationSelectionParent);
			window = primaryStage;
			
			scene = new Scene(mainMenuParent);
			window.setMaximized(true);
			window.setScene(scene);
			window.show();
		
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
		scene.setRoot(sceneList.get(id));
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
            loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));
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
            loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));
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
            loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));
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
		return personList;
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
	@Override
	public ObservableList<Party> getSimulationPartyData() {
		return simulationPartyList;
	}
	@Override
	public ObservableList<Product> getSimulationProductData() {
		return simulationProductList;		
	}
	
	public void startSimulation() {
		dataController.startSimulation(simulationPartyList, simulationProductList, personList);
	}
	public int getPersonCount() {
		return personCount;
	}
	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}
	public int getSimulationTime() {
		return simulationTime;
	}
	public void setSimulationTime(int simulatinTime) {
		this.simulationTime = simulatinTime;
	}
	
}

