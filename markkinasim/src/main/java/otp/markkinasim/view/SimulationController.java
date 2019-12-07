package otp.markkinasim.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import otp.markkinasim.simulation.Simulator;
import otp.markkinasim.simulation.Item;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;
/**
* Tämä luokka kontrolloi simulaation ajo ikkunaa.
* 
* @author Joonas Lapinlampi
*/
public class SimulationController{

	private IView view;
	private Simulator core;
	
	@FXML
	private TextArea simulationLog;
	@FXML
	private TableView<Party> partyTable;
	@FXML
	private TableColumn<Party, String> partyName;
	@FXML
	private TableColumn<Party, String> partyProduct;
	@FXML
	private TableColumn<Party, Number> partyProductAmount;
	@FXML
	private TableColumn<Party, String> partyRawmaterial;
	@FXML
	private TableColumn<Party, Number> partyRawmaterialAmount;
	@FXML
	private TableColumn<Party, Number> partyMoney;
	
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, Number> personCount;
	@FXML
	private TableColumn<Person, String> personWork;
	@FXML
	private TableColumn<Person, Number> personMoney;
	
	public SimulationController(View view) {
		this.view = view;
		core = Simulator.getInstance();
	
	}
	
	/**
	 * Asetetaan taulukkojen sarakkeiden sisältämät tiedot, sekä taulukkoihin kuuluvat listat.
	 */
	@FXML
	private void initialize() {
		 // Party taulukko alustus
		partyName.setCellValueFactory(
                cellData -> cellData.getValue().partyNameProperty());	
		partyProduct.setCellValueFactory(
				cellData -> cellData.getValue().productToProduceProperty());
		partyProductAmount.setCellValueFactory(new Callback<CellDataFeatures<Party, Number>, ObservableValue<Number>>() {
		     public ObservableValue<Number> call(CellDataFeatures<Party, Number> p) {
		    	 Item item = p.getValue().getProducedItemInventory();
		    	 IntegerProperty fill = new SimpleIntegerProperty(0);	
		    	 if(item!=null) {
		    	 		return item.amountProperty();
		    	 }return fill;
		     }
		  });
		partyRawmaterial.setCellValueFactory(new Callback<CellDataFeatures<Party, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Party, String> p) {
		    	 // p.getValue() palauttaa kyseisen rivin party olion
		    	 if(p.getValue().getProductToProduce().getProductNeededId()>=0) {
		    		 for(Product i:view.getAllProductData()) {
		    			 if(p.getValue().getProductToProduce().getProductNeededId()==i.getId()) {
		    				 return i.productNameProperty();
		    			 }
		    		 }
		    	 }return null;
		     }
		  });
		partyRawmaterialAmount.setCellValueFactory(new Callback<CellDataFeatures<Party, Number>, ObservableValue<Number>>() {
		     public ObservableValue<Number> call(CellDataFeatures<Party, Number> p) {
		    	 Item item = p.getValue().getNeededItemInventory();
		    	 	if(item!=null) {
		    	 		return item.amountProperty();
		    	 	}else if(p.getValue().getProductToProduce().getProductNeededId()>=0) {
		    	 		IntegerProperty fill = new SimpleIntegerProperty(0);
		    	 		return fill;
		    	 	}return null;
		     }
		  });
		partyMoney.setCellValueFactory(
                cellData -> cellData.getValue().moneyProperty());
		partyTable.setItems(view.getSimulationPartyData());
		//partyTable end
		//personTable Start
		personCount.setCellValueFactory(new Callback<CellDataFeatures<Person, Number>, ObservableValue<Number>>() {
		     public ObservableValue<Number> call(CellDataFeatures<Person, Number> p) {
		    	 IntegerProperty fill = new SimpleIntegerProperty(p.getValue().getId());	
		    	 return fill;
		     }
		  });
		personWork.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
		    	 if(p.getValue().getEmployer() != null) { return p.getValue().getEmployer().partyNameProperty();}
		    	 StringProperty fill = new SimpleStringProperty("Työtön");
		    	 return fill;
		     }
		  });		
		personMoney.setCellValueFactory(new Callback<CellDataFeatures<Person, Number>, ObservableValue<Number>>() {
		     public ObservableValue<Number> call(CellDataFeatures<Person, Number> p) {
		    	 DoubleProperty fill = new SimpleDoubleProperty(p.getValue().getMoney());	
		    	 return fill;
		     }
		  });
		personTable.setItems(view.getPersonData());
	}
	
	@FXML
	private void nextRound() {	
		view.nextDay();
		partyTable.refresh();
		personTable.refresh();
	}
	@FXML
	private void backToMenu() {
		view.setScene(0);
	}
	
	/**
	 * Tulostaa parametrinä saadun tekstin simulationLog TextArea:n
	 * 
	 * @param msg	tulostettava String
	 */
	public void printText(String msg) {
		simulationLog.appendText(msg);
	}
	
	@FXML
	private void exitButton() {
		System.exit(0);
	}
}
