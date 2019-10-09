package otp.markkinasim.view;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;


public class SimulationOptionsController {
	
	private IView view;
	@FXML
	private TableView<Party> partyTable;
	@FXML
	private TableColumn<Party, String> partyName;
	@FXML
	private TableColumn<Party, String> partyProduct;
	@FXML
	private TableColumn<Party, String> partyRawmaterial;
	@FXML
	private TableColumn<Party, Number> partyMoney;
	
	@FXML
	private TableView<Product> productTable;
	@FXML
	private TableColumn<Product, String> productName;
	@FXML
	private TableColumn<Product, String> productRawmaterial;

	@FXML
	private TableView<Product> rawmaterialTable;
	@FXML
	private TableColumn<Product, Number> rawmaterialId;
	@FXML
	private TableColumn<Product, String> rawmaterialName;
	
	//constructor
	public SimulationOptionsController(View view) {
		this.view = view;
	}
	
	//inits
	@FXML
    private void initialize() {
		
        // Party taulukko alustus
		partyName.setCellValueFactory(
                cellData -> cellData.getValue().partyNameProperty());	
		partyProduct.setCellValueFactory(
				cellData -> cellData.getValue().productToProduceProperty());
		partyRawmaterial.setCellValueFactory(new Callback<CellDataFeatures<Party, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Party, String> p) {
		         // p.getValue() returns the Party instance for a particular TableView row
		    	 if(p.getValue().getProductToProduce().getProductNeededId()>=0) {
		    		 for(Product i:view.getAllProductData()) {
		    			 if(p.getValue().getProductToProduce().getProductNeededId()==i.getId()) {
		    				 return i.productNameProperty();
		    			 }
		    		 }
		    	 }return null;
		     }
		  });
		partyMoney.setCellValueFactory(
                cellData -> cellData.getValue().moneyProperty());
		
		partyTable.setItems(view.getPartyData());
		
		
		//Rawmaterial taulukon alustus
		rawmaterialId.setCellValueFactory(new Callback<CellDataFeatures<Product, Number>, ObservableValue<Number>>() {
		     public ObservableValue<Number> call(CellDataFeatures<Product, Number> p) {
		         // p.getValue() returns the Product instance for a particular TableView row
		    	 ObservableValue<Number> obsInt = new ReadOnlyObjectWrapper<>(p.getValue().getId());
		         return obsInt;
		     }
		  });
		rawmaterialName.setCellValueFactory(
				cellData -> cellData.getValue().productNameProperty());
		rawmaterialTable.setItems(view.getAllProductData());
		
		//Tuote taulukon alustus
		productName.setCellValueFactory(
				cellData -> cellData.getValue().productNameProperty());
		productRawmaterial.setCellValueFactory(new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Product, String> p) {
		         // p.getValue() returns the Product instance for a particular TableView row
		   		 for(Product i:view.getAllProductData()) {
		   			 if(p.getValue().getProductNeededId()==i.getId()) {
		   				 return i.productNameProperty();
		   			 }
		   		 }return null;
		     }
		  });
		productTable.setItems(view.getAllProductData());
	}
	
	@FXML
	private void backToMenu() {
		view.setScene(0);
	}
	private void tableRefresh() {
		partyTable.refresh();
		productTable.refresh();
		rawmaterialTable.refresh();
	}
	
	@FXML
    private void handleNewParty() {
		if(!view.getAllProductData().isEmpty() && !view.getAllProductData().isEmpty()) {
        Party tempParty = new Party();
        boolean okClicked = view.showPartyEditDialog(tempParty);
        if (okClicked) {
            view.getPartyData().add(tempParty);
            tableRefresh();
        }
		}else {
   		 // No rawmaterials.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Ei raaka-aineita tai tuotteita");
            alert.setHeaderText("Ei raaka-aineita tai tuotteita.");
            alert.setContentText("Luo v�hint��n yksi raaka-aine ja tuote ennen kuin luot tahon.");

            alert.showAndWait();
    	}
    }
	
    @FXML
    private void handleEditParty() {
        Party selectedParty = partyTable.getSelectionModel().getSelectedItem();
        if (selectedParty != null) {
            boolean okClicked = view.showPartyEditDialog(selectedParty);
            tableRefresh();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Ei valintaa");
            alert.setHeaderText("Tahoa ei ole valitty.");
            alert.setContentText("Valitse taho taulukosta.");

            alert.showAndWait();
        }
    }
	
	@FXML
	private void handleDeleteParty() {
	        int selectedIndex = partyTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	            partyTable.getItems().remove(selectedIndex);
	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(view.getPrimaryStage());
	            alert.setTitle("Ei valintaa");
	            alert.setHeaderText("Tahoa ei ole valittu.");
	            alert.setContentText("Valitse taho taulukosta.");

	            alert.showAndWait();
	        }
	    }
	@FXML
    private void handleNewRawmaterial() {
        Product tempRawmaterial = new Product();
        boolean okClicked = view.showRawmaterialEditDialog(tempRawmaterial);
        if (okClicked) {
            view.getAllProductData().add(tempRawmaterial);
            tableRefresh();
        }
    }
	
    @FXML
    private void handleEditRawmaterial() {
        Product selectedRawmaterial = rawmaterialTable.getSelectionModel().getSelectedItem();
        if (selectedRawmaterial != null) {
            boolean okClicked = view.showRawmaterialEditDialog(selectedRawmaterial);
            tableRefresh();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Ei valintaa");
            alert.setHeaderText("Raaka-ainetta ei ole valittu.");
            alert.setContentText("Valitse raaka-aine taulukosta.");

            alert.showAndWait();
        }
    }
	
	@FXML
	private void handleDeleteRawmaterial() {
	        int selectedIndex = rawmaterialTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	        	if(!rawmaterialUsed(rawmaterialTable.getSelectionModel().getSelectedItem())) {
	        		rawmaterialTable.getItems().remove(selectedIndex);
	        	}else {
	        		// Rawmaterial in use
		            Alert alert = new Alert(AlertType.WARNING);
		            alert.initOwner(view.getPrimaryStage());
		            alert.setTitle("Valittu raaka-aine k�yt�ss�");
		            alert.setHeaderText("K�yt�ss� olevaa raaka-ainetta ei voida poistaa.");
		            alert.setContentText("Poista tuotte/tuotteet, jotka k�ytt�v�t raaka-ainetta.");

		            alert.showAndWait();
	        	}
	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(view.getPrimaryStage());
	            alert.setTitle("Ei valintaa");
	            alert.setHeaderText("Raaka-ainetta ei ole valittu.");
	            alert.setContentText("Valitse raaka-aine taulukosta.");

	            alert.showAndWait();
	        }
	    }
	
	private boolean rawmaterialUsed(Product rawmaterial) {
		for(Product i:view.getAllProductData()) {
			if(i.getProductNeededId()==rawmaterial.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean productUsed(Product product) {
		for(Party i:view.getPartyData()) {
			if(i.getProductToProduceName()==product.getProductName()) {
				return true;
			}
		}
		return false;
	}

	@FXML
    private void handleNewProduct() {
		if(!view.getAllProductData().isEmpty()) {
			
			Product tempProduct = new Product();
        	boolean okClicked = view.showProductEditDialog(tempProduct);
		
        	if (okClicked) {
            	view.getAllProductData().add(tempProduct);
            	tableRefresh();
        	}
		}else {
   		 // No rawmaterials.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Ei raaka-aineita");
            alert.setHeaderText("Ei raaka-aineita.");
            alert.setContentText("Luo v�hint��n yksi raaka-aine ennen kuin luot tuotteen.");

            alert.showAndWait();
    	}
    }
	
    @FXML
    private void handleEditProduct() {
    	Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean okClicked = view.showProductEditDialog(selectedProduct);
            tableRefresh();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Ei valintaa");
            alert.setHeaderText("Tuotetta ei ole valittu.");
            alert.setContentText("Valitse tuote taulukosta.");

            alert.showAndWait();
        }
    }
	
	@FXML
	private void handleDeleteProduct() {
	        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
	        if (selectedIndex >= 0) {
	        	if(productUsed(productTable.getSelectionModel().getSelectedItem())) {
	        		productTable.getItems().remove(selectedIndex);
	        	}else {
	        		 // Nothing selected.
		            Alert alert = new Alert(AlertType.WARNING);
		            alert.initOwner(view.getPrimaryStage());
		            alert.setTitle("Valittu tuote on k�yt�ss�");
		            alert.setHeaderText("K�yt�ss� olevaa tuotetta ei voida poistaa.");
		            alert.setContentText("Poista tai muokkaa tahoa/tahoja, jotka k�ytt�v�t tuotetta.");

		            alert.showAndWait();
	        	}
	        	
	        } else {
	            // Nothing selected.
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(view.getPrimaryStage());
	            alert.setTitle("Ei valintaa");
	            alert.setHeaderText("Tuotetta ei ole valittu.");
	            alert.setContentText("Valitse tuote taulukosta.");

	            alert.showAndWait();
	        }
	    }
}
