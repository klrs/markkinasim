package otp.markkinasim.view;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/**
*
* @author Joonas Lapinlampi
*/
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
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
	
	private Locale locale;
	private Properties config;
	private Properties language;
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
			locale = Locale.getDefault();
			
			config = new Properties();
			language = new Properties();
			
			 try (InputStream input = new FileInputStream("markkinasim/src/main/resources/config.properties")) {				 	
				 config.load(input);
		        } catch (IOException ex) {
		        	try (OutputStream output = new FileOutputStream("markkinasim/src/main/resources/config.properties")) {

		        		config.setProperty("locale", locale.getLanguage());
			            config.store(output, null);

			        } catch (IOException io) {
			            io.printStackTrace();
			        }
		        }
			 
			if(locale.getLanguage().equals(new Locale(config.getProperty("locale")).getLanguage())) {
				setLanguage();
			}else {
				locale = new Locale("en");
				setLanguage();
			}
			
			window = primaryStage;
			
			scene = new Scene(sceneList.get(0));
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
	public void writeSimulationLog(String key, double amount, String partyName, String productName) {
		
		String textToPrint = "";
		
		if(key.equals("PRODUCE")) {
			
			textToPrint = partyName + " " + language.getProperty("produce")+" "+amount+" "+productName+".\n";
			SimulationController.printText(textToPrint);
			
		}else if(key.equals("NO_PRODUCE")) {
			
			textToPrint = partyName + " " + language.getProperty("noProduce")+".\n";
 			SimulationController.printText(textToPrint);
			
		}else if(key.equals("FOUND_WORK")) {
			
			textToPrint = productName +":"+ language.getProperty("personFoundWork")+" "+partyName+".\n";
			SimulationController.printText(textToPrint);
			
		}else if(key.equals("SET_SELL")) {
			
			textToPrint = partyName + " " + language.getProperty("putSell")+" "+amount+" "+productName+" "+ language.getProperty("sell")+".\n";
			SimulationController.printText(textToPrint);
			
		}else if(key.equals("CONSUME")) {
			
			textToPrint = partyName + " " + language.getProperty("consumed")+" "+amount+" "+productName+".\n";
			SimulationController.printText(textToPrint);
			
		}else if(key.equals("SOLD")) {
			
			textToPrint = partyName + " " + language.getProperty("sold")+" "+amount+" "+productName+".\n";
			SimulationController.printText(textToPrint);
		}
		
		
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
	
	public void setLanguage() throws IOException {
		
		try {
			language.load(new FileInputStream("markkinasim/src/main/resources/languageResources/language_"+locale.getLanguage()+".properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
		loader.setResources(ResourceBundle.getBundle("languageResources/language", locale));			
	    loader.setController(MainMenuController);
	    Parent mainMenuParent = loader.load();

	    loader = new FXMLLoader(getClass().getResource("SimulationView.fxml"));
	    loader.setResources(ResourceBundle.getBundle("languageResources/language", locale));	
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
	    
	    sceneList.clear();
	    
		sceneList.add(mainMenuParent);
		sceneList.add(simulationParent);
		sceneList.add(simulationOptionsParent);
		sceneList.add(simulationSelectionParent);
		
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public Properties getConfig() {
		return config;
	}
	
	public Properties getLanguage() {
		return language;
	}
	
	public void nextDay() {
		dataController.nextIteration();
	}
}

