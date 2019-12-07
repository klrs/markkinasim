package otp.markkinasim.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;
/**
* Tämä luokka on käyttöliittymän pääluokka, joka käynnistää käyttöliittymän.
* Käynnistyksen yhteydessä luokka määrittää käytettävän kielen, sekä luo käyttöliittymän muut osat.
* Tämä luokka sisältää muiden käyttöliittymän osien tarvitsemat yhteiset metodit ja datan.
* Tämä luokka on singleton ja sen ilmentymän voi pyytää metodilla {@link #getInstance()}.
* 
*  
* @author Joonas Lapinlampi
*/
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
	/**
	 * Käytetään vain kerran käyttöliittymän käynnistyksen yhteydessä.
	 * Tästä luokasta ei saa luoda uutta ilmentymää missään muussa tilanteessa.
	 */
	public View() {
		view = this;
	}
	
	/**
	 * Luo ohjelman ikkunoiden kontrollerit. 
	 * Luo dataControllerin jota käytetään ohjelman muiden osien kanssa kommonikointiin.
	 * Hakee productList ja partyList sisällön tietokannasta.
	 * Alustaa simulationPartyList-, simulationProductList- ja personList-listan myöhempää käyttöä varten.
	 */
	public void init() {
		//Luodaan controllerit ja olio-listat
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
	/**
	 * Käyttöliittymän käynnistys metodi.
	 * Valitsee oikean kieliasetuksen ja luo, sekä käynnistää ohjelman pääikkunan.
	 * 
	 * @exception IOException io		config.property tiedostoa ei ole ja uutta ei voida luoda
	 * @exception Exception	e	käyttöliittymän pääikkunaa ei voida käynnistää
	 */
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
	/**
	 * Palauttaa singleton ilmentymän.
	 * @return view
	 */
	public static IView getInstance() {
	
		if (view == null) {
			view = new View();
		}
		return view;
	}
	
	/**
	 * Vaihtaa scenen parentin.
	 * @param id	int arvo, joka kertoo mikä sceneList listan parenteista asetetaan scenelle.
	 */
	@Override
	public void setScene(int id) {
		scene.setRoot(sceneList.get(id));
	}
	
	/**
	 * Palauttaa pääikkunnan ilmentymän
	 * @return window	stage muuttuja joka sisältää primaryStagen
	 */
	@Override
	public Window getPrimaryStage() {
		return window;
	}
	
	/**
	 * Palauttaa kaikki luodut tahot sisältävän listan.
	 * @return partyList	party olioita sisältävä ObservableList 
	 */
	@Override
	public ObservableList<Party> getPartyData() {
		return partyList;
	}
	
	/**
	 * Palauttaa kaikki tuotteet sisältävän listan.
	 * @return productList	product olioita sisältävä ObservableList
	 */
	@Override
	public ObservableList<Product> getAllProductData(){
		return productList;
	}
	
	/**
	 * Määrittää parametrinä saadun avaimen perusteella oikean tekstin ja lähettää sen SimulationControllerille.
	 * @param key			avain jolla valitaan oikea teksti
	 * @param amount		lukumäärä antaa tarvittaessa tekstiin numeroarvon esim. "taho tuotti 4 tuotetta"
	 * @param partyName		antaa tekstissä käytettävän tahon nimen
	 * @param productName	antaa tekstissä käytettävän tuotteen nimen, tai person id:n "FOUND_WORK" tapauksessa
	 */
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
	
	/**
	 * Luo ja käynnistää tahon muokkaus ja luonti ikkunan.
	 * @param party		luotava tai muokattava party olio
	 */
	@Override
    public boolean showPartyEditDialog(Party party) {
        try {
            // Lataa fxml tiedoston ja luo uuden stagen ponnahdus ikkunalle.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("PartyEditDialog.fxml"));
            loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Party");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Luodaan ikkunan controlleri.
            PartyEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setView(this);
            controller.setParty(party);
                        
            // Avataan ikkuna ja odotetaan että käyttäjä sulkee sen
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * Luo ja käynnistää tuotteen muokkaus ja luonti ikkunan.
	 * @param product		luotava tai muokattava product olio
	 */
	@Override
    public boolean showProductEditDialog(Product product) {
        try {
        	// Lataa fxml tiedoston ja luo uuden stagen ponnahdus ikkunalle.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("ProductEditDialog.fxml"));
            loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));
            AnchorPane page = (AnchorPane) loader.load();
          
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Luodaan ikkunan controlleri.
            ProductEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setView(this);
            controller.setProduct(product);
            
            // Avataan ikkuna ja odotetaan että käyttäjä sulkee sen
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * Luo ja käynnistää raaka-aine tyyppiä olevan tuotteen muokkaus ja luonti ikkunan.
	 * @param product		luotava tai muokattava raaka-aine tyyppiä oleva product olio
	 */
	@Override
    public boolean showRawmaterialEditDialog(Product product) {
        try {
        	// Lataa fxml tiedoston ja luo uuden stagen ponnahdus ikkunalle.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("RawmaterialEditDialog.fxml"));
            loader.setResources(ResourceBundle.getBundle("languageResources/language",locale));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Rawmaterial");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Luodaan ikkunan controlleri.
            RawmaterialEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRawmaterial(product);

            // Avataan ikkuna ja odotetaan että käyttäjä sulkee sen
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * Palauttaa personit sisältävän listan.
	 * @return personList	person olioita sisältävä ObservableList
	 */
	@Override
	public ObservableList<Person> getPersonData() {
		return personList;
	}
	
	/**
	 * Lähettää parametrinä saadun object olion dataControllerin kautta tietokannalle.
	 * Jos olion tallentaminen tietokantaa onnistui tämän luokan partyList tai productList päivitetään katsomalla kumman listan sisältöä vastaavan olio intanssi object on.
	 * 
	 * @param o		objekti olio joka on party tai product
	 */
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
	
	/**
	 * Lähettää parametrinä saadun object olion dataControllerin kautta tietokannalle.
	 * Jos olion poistaminen tietokannasta onnistui tämän luokan partyList tai productList päivitetään katsomalla kumman listan sisältöä vastaavan olio intanssi object on.
	 * 
	 * @param o		objekti olio joka on party tai product
	 */
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
	
	/**
	 * Palauttaa simulaatioon valittujen tahojen listan.
	 * 
	 * @return simulationPartyList		party olioista koostuva ObservableList
	 */
	@Override
	public ObservableList<Party> getSimulationPartyData() {
		return simulationPartyList;
	}
	
	/**
	 * Palauttaa simulaatioon valittujen tuotteiden listan.
	 * 
	 * @return simulationProductList		product olioista koostuva ObservableList
	 */
	@Override
	public ObservableList<Product> getSimulationProductData() {
		return simulationProductList;		
	}
	
	/**
	 * Lähettää simulaation aloitus komennon.
	 */
	public void startSimulation() {
		dataController.startSimulation(simulationPartyList, simulationProductList, personList);
	}
	
	/**
	 * Palauttaa luotavien person olioiden lukumäärän.
	 * 
	 * @return personCount		person oliodeen lukumäärä int
	 */
	public int getPersonCount() {
		return personCount;
	}
	
	/**
	 * Asettaa luotavien person olioiden lukumäärän.
	 * 
	 * @param personCount	int arvo jolla asetetaan luotavien person olioiden lukumäärä
	 */
	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}
	
	/**
	 * Palauttaa simulaation pituuden eli simuloitavien kierrosten lukumäärän.
	 * 
	 * @return simulationTime		int arvo joka kertoo simulaation kierrosten lukumäärän
	 */
	public int getSimulationTime() {
		return simulationTime;
	}
	
	/**
	 * Asettaa simulaation pituuden eli simuloitavien kierrosten lukumäärän.
	 * 
	 * @param simulationTime		int arvo jolla asetetaan simulaation kierrosten lukumäärän
	 */
	public void setSimulationTime(int simulatinTime) {
		this.simulationTime = simulatinTime;
	}
	
	/**
	 * Lataa ohjelman kieli tiedoston (language.property) locale muuttujan perusteella.
	 * Asettaa valitun kielen kaikille ohjelman ikkunoille.
	 * 
	 * @throws IOException		palauttaa virheen jos language.property tiedoston lataaminen tai parrenttien uusiminen ei onnistu
	 */
	public void setLanguage() throws IOException {
			
		language.load(new FileInputStream("markkinasim/src/main/resources/languageResources/language_"+locale.getLanguage()+".properties"));
	
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
	
	/**
	 * Palalauttaa locale muuttujan.
	 * 
	 * @return locale		muuttuja joka sisältää ohjelmalle asetetun lacalen
	 */
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * Asettaa locale muuttujan.
	 * 
	 * @param locale		muuttuja joka sisältää ohjelmalle asetettavan lacalen
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * Palauttaa ohjelman config.property tiedoston.
	 * 
	 * @return config		sisältää muistiin ladatun config.property tiedoston
	 */
	public Properties getConfig() {
		return config;
	}
	
	/**
	 * Palauttaa ohjelman language.property tiedoston.
	 * 
	 * @return language		sisältää muistiin ladatun language.property tiedoston
	 */
	public Properties getLanguage() {
		return language;
	}
	
	/**
	 * Käskee simulaatiota siirtymään seuraavaan kierrokseen.
	 */
	public void nextDay() {
		dataController.nextIteration();
	}
}

