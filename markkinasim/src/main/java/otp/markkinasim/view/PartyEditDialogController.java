package otp.markkinasim.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import otp.markkinasim.model.Party;

public class PartyEditDialogController {
	
	private Stage dialogStage;
    private Party party;
    private boolean okClicked = false;
    
    @FXML
    private ChoiceBox<String> partyTypeChoice;
    @FXML
    private TextField partyName;
    @FXML
    private ChoiceBox<String> partyRawmaterialChoice;
    @FXML
    private ChoiceBox<String> partyProductChoice;
    @FXML
    private TextField partyMoney;
    @FXML
    private TextField partyPersons;
    
  //inits
  	@FXML
    private void initialize() {
  		partyTypeChoice.getItems().addAll("Raaka-aine","Jalostus");
  		partyRawmaterialChoice.getItems().addAll("Rauta","Liha");
  		partyProductChoice.getItems().addAll("Teräs","Pihvi");
  	}
  	
	public Stage getDialogStage() {
		return dialogStage;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	public Party getParty() {
		return party;
	}
	public void setParty(Party party) {
		this.party = party;
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	public void setOkClicked(boolean okClicked) {
		this.okClicked = okClicked;
	}
    
}
