package otp.markkinasim.view;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;

/**
 *Tämä luokka kontroilloi simulaatiossa käytettävien tahojen valinta ikkunaa.
 *
 * @author Joonas Lapinlampi
 */
public class SimulationSelectionController {

	private IView view;
	// taulukoiden ja niiden sarakkeiden esittely
	@FXML
	private TableView<Party> savedPartyTable;
	@FXML
	private TableColumn<Party, String> partyName;
	@FXML
	private TableColumn<Party, String> partyProduct;
	@FXML
	private TableColumn<Party, String> partyRawmaterial;
	@FXML
	private TableColumn<Party, Number> partyMoney;

	@FXML
	private TableView<Party> simulationPartyTable;
	@FXML
	private TableColumn<Party, String> simulationPartyName;
	@FXML
	private TableColumn<Party, String> simulationPartyProduct;
	@FXML
	private TableColumn<Party, String> simulationPartyRawmaterial;
	@FXML
	private TableColumn<Party, Number> simulationPartyMoney;

	public SimulationSelectionController(View view) {
		this.view = view;
	}

	/**
	 * Asetetaan taulukkojen sarakkeiden sisältämät tiedot, sekä taulukkoihin kuuluvat listat.
	 */
	@FXML
	private void initialize() {
		// Tietokannasta haetun taho listan asettaminen savedPartyTable taulukkoon
		partyName.setCellValueFactory(cellData -> cellData.getValue().partyNameProperty());
		partyProduct.setCellValueFactory(cellData -> cellData.getValue().productToProduceProperty());
		partyRawmaterial.setCellValueFactory(new Callback<CellDataFeatures<Party, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Party, String> p) {
				// p.getValue() palauttaa kyseisen rivin party olion
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

		savedPartyTable.setItems(view.getPartyData());

		// Simulaatioon valittujen tahojen asettaminen simulationPartyTable taulukkon
		simulationPartyName.setCellValueFactory(cellData -> cellData.getValue().partyNameProperty());
		simulationPartyProduct.setCellValueFactory(cellData -> cellData.getValue().productToProduceProperty());
		simulationPartyRawmaterial
				.setCellValueFactory(new Callback<CellDataFeatures<Party, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Party, String> p) {
						// p.getValue() palauttaa kyseisen rivin party olion
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
		simulationPartyMoney.setCellValueFactory(cellData -> cellData.getValue().moneyProperty());

		simulationPartyTable.setItems(view.getSimulationPartyData());
	}

	@FXML
	private void backToMainmenu() {
		view.setScene(0);
	}

	@FXML
	private void startSimulation() {
		// Tarkistetaan että simulationPartyList ja simulationProductList sisältävät tarvittavat tiedot.
		
		Product hasNeededProduct = null;
		Product hasNeededRawmaterial = null;
		ObservableList<Product> tempRawmaterials = FXCollections.observableArrayList();
		ObservableList<Product> tempProducts = FXCollections.observableArrayList();

		if (!view.getSimulationPartyData().isEmpty()) {

			tempProducts.addAll(view.getSimulationProductData().filtered(Product -> Product.getProductNeededId() >= 0));
			tempRawmaterials.addAll(view.getSimulationProductData().filtered(Product -> Product.getProductNeededId() < 0));

			for (Product p : tempProducts) {
				for (Product P : view.getAllProductData()) {
					if (p.getProductNeededId() == P.getId() && !tempRawmaterials.contains(P)) {
						hasNeededRawmaterial = P;
					}
				}

			}

			for (Product p : tempRawmaterials) {
				if (!tempProducts.isEmpty()) {
					for (Product P : tempProducts) {
						if (p.getId() != P.getProductNeededId()) {
							hasNeededProduct = p;							
						} else {
							hasNeededProduct = null;
							break;
						}
					}
				} else {
					hasNeededProduct = p;
					break;
				}
			}

			if (hasNeededProduct == null && hasNeededRawmaterial == null) {
				view.getPersonData().clear();
				if(view.getPersonCount()<=0) {
					view.setPersonCount(10);
				}
				
				if(view.getSimulationTime()<=0) {
					view.setSimulationTime(10);
				}
				
				for(int i=0;i<view.getPersonCount();i++) {
					view.getPersonData().add(new Person());
				}
				view.startSimulation();
				view.setScene(1);
			} else if (hasNeededProduct != null && hasNeededRawmaterial == null) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(view.getPrimaryStage());
				alert.setTitle(view.getLanguage().getProperty("simulationSelectionIncomple"));
				alert.setHeaderText(view.getLanguage().getProperty("simulationMissingProduct"));
				alert.setContentText(view.getLanguage().getProperty("simulationMissingProductInfo")+" " + hasNeededProduct.getProductName());

				alert.showAndWait();
			} else if (hasNeededProduct == null && hasNeededRawmaterial != null) {
			
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(view.getPrimaryStage());
				alert.setTitle(view.getLanguage().getProperty("simulationSelectionIncomple"));
				alert.setHeaderText(view.getLanguage().getProperty("simulationMissingRawmaterial"));
				alert.setContentText(view.getLanguage().getProperty("simulationMissingRawmaterialInfo")+" " + hasNeededRawmaterial.getProductName());

				alert.showAndWait();
			} else {
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(view.getPrimaryStage());
				alert.setTitle(view.getLanguage().getProperty("simulationSelectionIncomple"));
				alert.setHeaderText(view.getLanguage().getProperty("simulationMissingRawmaterialAndProduct"));
				alert.setContentText(view.getLanguage().getProperty("simulationAddMissinManufacturer")+" " + hasNeededRawmaterial.getProductName()+"\n"+view.getLanguage().getProperty("simulationAddMissinParty")+" " + hasNeededProduct.getProductName());
				
				alert.showAndWait();
			}
		} else {
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(view.getPrimaryStage());
			alert.setTitle(view.getLanguage().getProperty("simulationEmpty"));
			alert.setHeaderText(view.getLanguage().getProperty("simulationNoSelections"));
			alert.setContentText(view.getLanguage().getProperty("simulationNoSelectionsInfo"));

			alert.showAndWait();
		}
	}
	
	@FXML //lisää valitun tahon simutalionPartyList listaan
	private void handleAddToSimulationPartyList() {
		Party tempParty = savedPartyTable.getSelectionModel().getSelectedItem();
		if (tempParty != null && !view.getSimulationPartyData().contains(tempParty)) {
			view.getSimulationPartyData().add(tempParty);
			if (!view.getSimulationProductData().contains(tempParty.getProductToProduce())) {
				view.getSimulationProductData().add(tempParty.getProductToProduce());
			}
		} else {
			if (tempParty == null) {
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(view.getPrimaryStage());
				alert.setTitle(view.getLanguage().getProperty("noSelection"));
				alert.setHeaderText(view.getLanguage().getProperty("noSelectedParty"));
				alert.setContentText(view.getLanguage().getProperty("noSavedPartySelectedInfo"));

				alert.showAndWait();
			} else if (view.getSimulationPartyData().contains(tempParty)) {
				// Onjo lisätty simulaatioon
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(view.getPrimaryStage());
				alert.setTitle(view.getLanguage().getProperty("inSimulation"));
				alert.setHeaderText(view.getLanguage().getProperty("allReadyInSimulation"));
				alert.setContentText(view.getLanguage().getProperty("allReadyInSimulationInfo"));

				alert.showAndWait();
			}

		}
	}

	@FXML //Poistetaan taho simulationPartyList listasta
	private void handleDeletePartyFromSimulationPartyList() {
		Party tempParty = simulationPartyTable.getSelectionModel().getSelectedItem();
		boolean productUsed = false;
		if (tempParty != null) {
			for(Party p:view.getSimulationPartyData()) {
				if(p.getId()!=tempParty.getId()&&p.getProductToProduceId()==tempParty.getProductToProduceId()) {
					productUsed = true;
				}
			}
			
			if(productUsed) {
				view.getSimulationPartyData().remove(tempParty);
			}else {
				view.getSimulationProductData().remove(tempParty.getProductToProduce());
				view.getSimulationPartyData().remove(tempParty);
			}
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
}
