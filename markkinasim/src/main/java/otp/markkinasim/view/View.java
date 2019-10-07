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
import otp.markkinasim.model.Core;
import otp.markkinasim.model.Manufacturer;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;


public class View extends Application implements IView{
	
	private static View view;
	
	private MainMenuController MainMenuController;
	private SimulationController SimulationController;
	private SimulationOptionsController SimulationOptionsController;
	
	private Core core = Core.getInstance();
	private static Stage window;
	private Scene mainMenu,simulation,simulationOptions;
	private List<Scene> sceneList = new ArrayList<Scene>();
	
	private ObservableList<Party> partyData = FXCollections.observableArrayList();
	private ObservableList<Product> allProductData = FXCollections.observableArrayList();
	private ObservableList<Product> productData = FXCollections.observableArrayList();
	private ObservableList<Product> rawmaterialData = FXCollections.observableArrayList();
	
	public void init() {
		//Luodaan perus scenet
		MainMenuController = new MainMenuController(this);
		SimulationController = new SimulationController(this);
		SimulationOptionsController = new SimulationOptionsController(this);
		
	    core.init(this);
		//default datan luonti
		allProductData.add(new Product("Cow"));
		allProductData.add(new Product("Beef patty", 0));	//INDEX????
		
		partyData.add(new Manufacturer("Jimbo's Beef", 1000.0f, allProductData.get(1)));
		partyData.add(new Party("Cowman", 100, allProductData.get(0)));
		
		try {
			partyData.get(0).addToInventory(allProductData.get(0), 3);
		} catch (InvalidParameterException e) {
			System.out.println(e.getMessage());
		}
		
		//erotellaan raaka-aineet ja tuotteet
		for(Product i : allProductData) {
			if(i.getProductNeededId()>=0) {
				productData.add(i);
			}else {
				rawmaterialData.add(i);
			}
		}

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
		//kutsu tätä funktiota luodaksesi Core olion!
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
		return partyData;
	}
	@Override
	public ObservableList<Product> getProductData() {
		return productData;
	}
	@Override
	public ObservableList<Product> getRawmaterialData() {
		return rawmaterialData;
	}
	@Override
	public ObservableList<Product> getAllProductData(){
		return allProductData;
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
}

