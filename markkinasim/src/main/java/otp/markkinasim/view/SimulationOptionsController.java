package otp.markkinasim.view;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;
import otp.markkinasim.model.Rawmaterial;

public class SimulationOptionsController implements ISimulationOptionsController {
	
	private IView view;
	@FXML
	private TableView<Party> partyTable;
	@FXML
	private TableColumn<Party, String> partyType;
	@FXML
	private TableColumn<Party, String> partyName;
	@FXML
	private TableColumn<Party, String> partyResource;
	@FXML
	private TableColumn<Party, String> partyProduct;
	@FXML
	private TableColumn<Party, Number> partyPersons;
	@FXML
	private TableColumn<Party, Number> partyMoney;
	@FXML
	private TableView<Product> productTable;
	@FXML
	private TableColumn<Product, String> productName;
	@FXML
	private TableColumn<Product, String> productRawmaterial;
	@FXML
	private TableColumn<Product, Number> productRawmaterialNeeded;
	@FXML
	private TableView<Rawmaterial> rawmaterialTable;
	@FXML
	private TableColumn<Rawmaterial, String> rawmaterialName;
	@FXML
	private TableColumn<Rawmaterial, String> rawmaterialSource;
	@FXML
	private TableColumn<Rawmaterial, Number> rawmaterialSourcePool;
	
	//constructor
	public SimulationOptionsController(View view) {
		this.view = view;
	}
	
	//inits
	@FXML
    private void initialize() {
        // Initialize the party table with the five columns.
		partyType.setCellValueFactory(
                cellData -> cellData.getValue().partyTypeProperty());
		partyName.setCellValueFactory(
                cellData -> cellData.getValue().partyNameProperty());
		partyResource.setCellValueFactory(
                cellData -> cellData.getValue().partyResourceProperty());
		partyProduct.setCellValueFactory(
                cellData -> cellData.getValue().partyProductProperty());
		partyPersons.setCellValueFactory(
                cellData -> cellData.getValue().partyWorkForceProperty());
		partyMoney.setCellValueFactory(
                cellData -> cellData.getValue().partyMoneyProperty());
		partyTable.setItems(view.getPartyData());
		
		rawmaterialName.setCellValueFactory(
				cellData -> cellData.getValue().rawmaterialNameProperty());
		rawmaterialSource.setCellValueFactory(
				cellData -> cellData.getValue().rawmaterialSourceProperty());
		rawmaterialSourcePool.setCellValueFactory(
				cellData -> cellData.getValue().rawmaterialSourcePoolProperty());
		rawmaterialTable.setItems(view.getRawmaterialData());
		
		productName.setCellValueFactory(
				cellData -> cellData.getValue().productNameProperty());
		productRawmaterial.setCellValueFactory(
				cellData -> cellData.getValue().rawmaterialNameProperty());
		productRawmaterialNeeded.setCellValueFactory(
				cellData -> cellData.getValue().rawmaterialNeededProperty());
		productTable.setItems(view.getProductData());
	}
	
	@FXML
	private void backToMenu() {
		view.setScene(0);
	}
	
	@FXML
    private void handleNewParty() {
		if(!view.getRawmaterialData().isEmpty() && !view.getProductData().isEmpty()) {
        Party tempParty = new Party();
        boolean okClicked = view.showPartyEditDialog(tempParty);
        if (okClicked) {
            view.getPartyData().add(tempParty);
        }
		}else {
   		 // No rawmaterials.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Ei raaka-aineita tai tuotteita");
            alert.setHeaderText("Ei raaka-aineita tai tuotteita.");
            alert.setContentText("Luo vähintään yksi raaka-aine ja tuote ennen kuin luot tahon.");

            alert.showAndWait();
    	}
    }
	
    @FXML
    private void handleEditParty() {
        Party selectedParty = partyTable.getSelectionModel().getSelectedItem();
        if (selectedParty != null) {
            boolean okClicked = view.showPartyEditDialog(selectedParty);

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
        Rawmaterial tempRawmaterial = new Rawmaterial();
        boolean okClicked = view.showRawmaterialEditDialog(tempRawmaterial);
        if (okClicked) {
            view.getRawmaterialData().add(tempRawmaterial);
        }
    }
	
    @FXML
    private void handleEditRawmaterial() {
        Rawmaterial selectedRawmaterial = rawmaterialTable.getSelectionModel().getSelectedItem();
        if (selectedRawmaterial != null) {
            boolean okClicked = view.showRawmaterialEditDialog(selectedRawmaterial);

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
		            alert.setTitle("Valittu raaka-aine käytössä");
		            alert.setHeaderText("Käytössä olevaa raaka-ainetta ei voida poistaa.");
		            alert.setContentText("Poista tuotte/tuotteet, jotka käyttävät raaka-ainetta.");

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
	
	private boolean rawmaterialUsed(Rawmaterial rawmaterial) {
		for(Product i:view.getProductData()) {
			if(i.getRawmaterialName()==rawmaterial.getRawmaterialName()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean productUsed(Product product) {
		for(Party i:view.getPartyData()) {
			if(i.getProduct()==product.getProductName()) {
				return true;
			}
		}
		return false;
	}

	@FXML
    private void handleNewProduct() {
		if(!view.getRawmaterialData().isEmpty()) {
			
			Product tempProduct = new Product();
        	boolean okClicked = view.showProductEditDialog(tempProduct);
		
        	if (okClicked) {
            	view.getProductData().add(tempProduct);
        	}
		}else {
   		 // No rawmaterials.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Ei raaka-aineita");
            alert.setHeaderText("Ei raaka-aineita.");
            alert.setContentText("Luo vähintään yksi raaka-aine ennen kuin luot tuotteen.");

            alert.showAndWait();
    	}
    }
	
    @FXML
    private void handleEditProduct() {
    	Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean okClicked = view.showProductEditDialog(selectedProduct);

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
		            alert.setTitle("Valittu tuote on käytössä");
		            alert.setHeaderText("Käytössä olevaa tuotetta ei voida poistaa.");
		            alert.setContentText("Poista tai muokkaa tahoa/tahoja, jotka käyttävät tuotetta.");

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
