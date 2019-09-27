package otp.markkinasim.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import otp.markkinasim.model.Party;

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
		
	/*partyTable.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> showPersonDetails(newValue));*/
		partyTable.setItems(view.getPartyData());
	}
	
	@FXML
	private void backToMenu() {
		view.setScene(0);
	}
	
	@FXML
    private void handleNewParty() {
        Party tempParty = new Party();
        boolean okClicked = view.showPartyEditDialog(tempParty);
        if (okClicked) {
            view.getPartyData().add(tempParty);
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
}
