package otp.markkinasim.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import otp.markkinasim.model.Core;
import otp.markkinasim.model.Item;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;

public class SimulationController{

	private IView view;
	private Core core;
	
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
		
	public SimulationController(View view) {
		this.view = view;
		core = Core.getInstance();
	
	}
	
	@FXML
	private void initialize() {
		 // Party taulukko alustus
		partyName.setCellValueFactory(
                cellData -> cellData.getValue().partyNameProperty());	
		partyProduct.setCellValueFactory(
				cellData -> cellData.getValue().productToProduceProperty());
		partyProductAmount.setCellValueFactory(new Callback<CellDataFeatures<Party, Number>, ObservableValue<Number>>() {
		     public ObservableValue<Number> call(CellDataFeatures<Party, Number> p) {
		    	 Item item = p.getValue().searchInventoryItem(p.getValue().getProductToProduce().getId());
		    	 IntegerProperty fill = new SimpleIntegerProperty(0);	
		    	 if(item!=null) {
		    	 		return item.amountProperty();
		    	 }return fill;
		     }
		  });
		partyRawmaterial.setCellValueFactory(new Callback<CellDataFeatures<Party, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Party, String> p) {
		         // p.getValue() returns the Party instance for a particular TableView row
		    	 if(p.getValue().getProductToProduce().getProductNeededId()>=0) {
		    		 for(Product i:view.getRawmaterialData()) {
		    			 if(p.getValue().getProductToProduce().getProductNeededId()==i.getId()) {
		    				 return i.productNameProperty();
		    			 }
		    		 }
		    	 }return null;
		     }
		  });
		partyRawmaterialAmount.setCellValueFactory(new Callback<CellDataFeatures<Party, Number>, ObservableValue<Number>>() {
		     public ObservableValue<Number> call(CellDataFeatures<Party, Number> p) {
		    	 Item item = p.getValue().searchInventoryItem(p.getValue().getProductToProduce().getProductNeededId());
		    	 	if(item!=null) {
		    	 		return item.amountProperty();
		    	 	}return null;
		     }
		  });
		partyMoney.setCellValueFactory(
                cellData -> cellData.getValue().moneyProperty());
		partyTable.setItems(view.getPartyData());		
	}
	
	@FXML
	private void nextRound() {	
		core.start();
		partyTable.refresh();
	}
	@FXML
	private void backToMenu() {
		view.setScene(0);
	}
	
	public void printText(String msg) {
		simulationLog.appendText(msg);
	}
}
