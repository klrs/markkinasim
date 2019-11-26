package otp.markkinasim.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
*
* @author Joonas Lapinlampi
*/
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Product;

public class SimulationOptionsController {

	private IView view;

	@FXML
	private TextField simulationDuration;
	@FXML
	private TextField personCount;
	@FXML
    private ChoiceBox<String> languageChoiceBox;
	
	// taulukoiden ja niiden sarakkeiden esittely
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

	// constructor
	public SimulationOptionsController(View view) {
		this.view = view;
	}

	// inits
	@FXML
	private void initialize() {

		// Party taulukko alustus
		partyName.setCellValueFactory(cellData -> cellData.getValue().partyNameProperty());
		partyProduct.setCellValueFactory(cellData -> cellData.getValue().productToProduceProperty());
		partyRawmaterial.setCellValueFactory(new Callback<CellDataFeatures<Party, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Party, String> p) {
				// p.getValue() returns the Party instance for a particular TableView row
				if (p.getValue().getProductToProduce() != null
						&& p.getValue().getProductToProduce().getProductNeededId() >= 0) {
					for (Product i : view.getAllProductData()) {
						if (p.getValue().getProductToProduce().getProductNeededId() == i.getId()) {
							return i.productNameProperty();
						}
					}
				}
				return null;
			}
		});
		partyMoney.setCellValueFactory(cellData -> cellData.getValue().moneyProperty());

		partyTable.setItems(view.getPartyData());

		// Rawmaterial taulukon alustus
		rawmaterialId.setCellValueFactory(new Callback<CellDataFeatures<Product, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<Product, Number> p) {
				// p.getValue() returns the Product instance for a particular TableView row
				ObservableValue<Number> obsInt = new ReadOnlyObjectWrapper<>(p.getValue().getId());
				return obsInt;
			}
		});
		rawmaterialName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		rawmaterialTable.setItems(view.getAllProductData().filtered(Product -> Product.getProductNeededId() < 0));

		// Tuote taulukon alustus
		productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
		productRawmaterial
				.setCellValueFactory(new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Product, String> p) {
						// p.getValue() returns the Product instance for a particular TableView row
						for (Product i : view.getAllProductData()) {
							if (p.getValue().getProductNeededId() == i.getId()) {
								return i.productNameProperty();
							}
						}
						return null;
					}
				});

		productTable.setItems(view.getAllProductData().filtered(Product -> Product.getProductNeededId() >= 0));
		
		//Kielivalinta alustus
		languageChoiceBox.getItems().addAll(view.getLanguage().getProperty("language_en"),view.getLanguage().getProperty("language_fi_FI"));
		if(view.getConfig().getProperty("locale").equals("fi")) {
			languageChoiceBox.setValue(view.getLanguage().getProperty("language_fi_FI"));
		}else {
			languageChoiceBox.setValue(view.getLanguage().getProperty("language_en"));
		}
		
		languageChoiceBox.getSelectionModel()
	    .selectedItemProperty()
	    .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			try {
				languageChange();
				view.setScene(2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} );
	}
	
	//takaisin main menuun painikkeen toiminto
	@FXML
	private void backToMenu(){
		if(isInputValid()) {
			view.setPersonCount(Integer.parseInt(personCount.getText()));
			view.setSimulationTime(Integer.parseInt(simulationDuration.getText()));
			view.setScene(0);
		}		
	}
	
	//taullukoiden päivitys
	private void tableRefresh() {
		partyTable.refresh();
		productTable.refresh();
		rawmaterialTable.refresh();
	}
	
	//uuden tahon luonti
	@FXML
	private void handleNewParty() {
		if (!view.getAllProductData().isEmpty() && !view.getAllProductData().isEmpty()) {
			Party tempParty = new Party();
			boolean okClicked = view.showPartyEditDialog(tempParty); 
			//tarkistetaan että taholle on annettu kaikki tarvittvat tiedot oikein
			if (okClicked) {
				//jos tahon tuottaman tuotteen tarvittu raaka-aine id on pienempi kuin nolla taho ei tarvitse raaka-ainetta ja on producer, muuten manufacturer
				if(tempParty.getProductToProduce().getProductNeededId()<0) {
					tempParty.setPartyType(2);
				}else {
					tempParty.setPartyType(1);
				}
				view.createNewObject(tempParty);
				tableRefresh();
			}
		} else {
			// No rawmaterials.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle(view.getLanguage().getProperty("noProductOrRawmaterial"));
			alert.setHeaderText(view.getLanguage().getProperty("noProductOrRawmaterial"));
			alert.setContentText(view.getLanguage().getProperty("noProductOrRawmaterialInfo"));

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
			alert.setTitle(view.getLanguage().getProperty("noSelection"));
			alert.setHeaderText(view.getLanguage().getProperty("noSelectedParty"));
			alert.setContentText(view.getLanguage().getProperty("noSelectedPartyInfo"));

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteParty() {
		int selectedIndex = partyTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			view.removeObject(partyTable.getSelectionModel().getSelectedItem());
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle(view.getLanguage().getProperty("noSelection"));
			alert.setHeaderText(view.getLanguage().getProperty("noSelectedParty"));
			alert.setContentText(view.getLanguage().getProperty("noSelectedPartyInfo"));

			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewRawmaterial() {
		Product tempRawmaterial = new Product();
		boolean okClicked = view.showRawmaterialEditDialog(tempRawmaterial);
		if (okClicked) {
			view.createNewObject(tempRawmaterial);
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
			alert.setTitle(view.getLanguage().getProperty("noSelection"));
			alert.setHeaderText(view.getLanguage().getProperty("noSelectedRawmaterial"));
			alert.setContentText(view.getLanguage().getProperty("noSelectedRawmaterialInfo"));

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteRawmaterial() {
		int selectedIndex = rawmaterialTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			if (!rawmaterialUsed(rawmaterialTable.getSelectionModel().getSelectedItem())) {
				view.removeObject(rawmaterialTable.getSelectionModel().getSelectedItem());
			} else {
				// Rawmaterial in use
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(view.getPrimaryStage());
				alert.setTitle(view.getLanguage().getProperty("selectedRawmaterialInUse"));
				alert.setHeaderText(view.getLanguage().getProperty("deleteRawmaterialError"));
				alert.setContentText(view.getLanguage().getProperty("deleteRawmaterialErrorInfo"));

				alert.showAndWait();
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle(view.getLanguage().getProperty("noSelection"));
			alert.setHeaderText(view.getLanguage().getProperty("noSelectedRawmaterial"));
			alert.setContentText(view.getLanguage().getProperty("noSelectedRawmaterialInfo"));

			alert.showAndWait();
		}
	}

	private boolean rawmaterialUsed(Product rawmaterial) {
		for (Product i : view.getAllProductData()) {
			if (i.getProductNeededId() == rawmaterial.getId()) {
				return true;
			}
		}

		for (Party i : view.getPartyData()) {
			if (i.getProductToProduceId() == rawmaterial.getId()) {
				return true;
			}
		}
		return false;
	}

	private boolean productUsed(Product product) {
		for (Party i : view.getPartyData()) {
			if (i.getProductToProduceId() == product.getId()) {
				return true;
			}
		}
		return false;
	}

	@FXML
	private void handleNewProduct() {
		if (!view.getAllProductData().isEmpty()) {

			Product tempProduct = new Product();
			boolean okClicked = view.showProductEditDialog(tempProduct);

			if (okClicked) {
				view.createNewObject(tempProduct);
				tableRefresh();
			}
		} else {
			// No rawmaterials.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle(view.getLanguage().getProperty("noSelection"));
			alert.setHeaderText(view.getLanguage().getProperty("noRawmaterial"));
			alert.setContentText(view.getLanguage().getProperty("noRawmaterialInfo"));

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
			alert.setTitle(view.getLanguage().getProperty("noSelection"));
			alert.setHeaderText(view.getLanguage().getProperty("noSelectedProduct"));
			alert.setContentText(view.getLanguage().getProperty("noSelectedProductInfo"));

			alert.showAndWait();
		}
	}

	@FXML
	private void handleDeleteProduct() {
		int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			if (!productUsed(productTable.getSelectionModel().getSelectedItem())) {
				view.removeObject(productTable.getSelectionModel().getSelectedItem());
			} else {
				// Nothing selected.
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(view.getPrimaryStage());
				alert.setTitle(view.getLanguage().getProperty("selectedProductInUse"));
				alert.setHeaderText(view.getLanguage().getProperty("deleteProductError"));
				alert.setContentText(view.getLanguage().getProperty("deleteProductErrorInfo"));

				alert.showAndWait();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle(view.getLanguage().getProperty("noSelection"));
			alert.setHeaderText(view.getLanguage().getProperty("noSelectedProduct"));
			alert.setContentText(view.getLanguage().getProperty("noSelectedProductInfo"));

			alert.showAndWait();
		}
	}

	@FXML
	private boolean isInputValid() {
		String errorMessage = "";

		if (personCount.getText() == null || personCount.getText().length() == 0) {
			errorMessage += view.getLanguage().getProperty("populationNumberError")+"\n";
		} else {
			// try to parse the money amount into an float.
			try {
				Float.parseFloat(personCount.getText());
			} catch (NumberFormatException e) {
				errorMessage += view.getLanguage().getProperty("populationNumberErrorInfo")+"\n";
			}
		}

		if (simulationDuration.getText() == null || simulationDuration.getText().length() == 0) {
			errorMessage += view.getLanguage().getProperty("simulationDurationError")+"\n";
		} else {
			// try to parse the money amount into an float.
			try {
				Float.parseFloat(simulationDuration.getText());
			} catch (NumberFormatException e) {
				errorMessage += view.getLanguage().getProperty("simulationDurationErrorInfo")+"\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle(view.getLanguage().getProperty("invalidFields"));
			alert.setHeaderText(view.getLanguage().getProperty("invalidFields"));
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
	
	@FXML
	private void languageChange() throws IOException {
		if(languageChoiceBox.getValue().equals(view.getLanguage().getProperty("language_fi_FI"))&&view.getConfig().getProperty("locale").equals("en")) {
			view.getConfig().setProperty("locale", "fi");
			view.setLocale(new Locale("fi"));
			view.getConfig().store(new FileOutputStream("markkinasim/src/main/resources/config.properties"), null);
			view.setLanguage();
		}else if(languageChoiceBox.getValue().equals(view.getLanguage().getProperty("language_en"))&&view.getConfig().getProperty("locale").equals("fi")){
			view.getConfig().setProperty("locale", "en");
			view.setLocale(new Locale("en"));
			view.getConfig().store(new FileOutputStream("markkinasim/src/main/resources/config.properties"), null);
			view.setLanguage();
		}
	}
}
