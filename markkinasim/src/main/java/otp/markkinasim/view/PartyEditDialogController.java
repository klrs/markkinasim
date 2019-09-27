package otp.markkinasim.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		
		partyTypeChoice.setValue(party.getPartyType());
		partyName.setText(party.getPartyName());
		partyRawmaterialChoice.setValue(party.getResource());
		partyProductChoice.setValue(party.getProduct());
		partyMoney.setText(Float.toString(party.getMoney()));
		partyPersons.setText(Integer.toString(party.getWorkForce()));
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	public void setOkClicked(boolean okClicked) {
		this.okClicked = okClicked;
	}
	
	//asettaa raaka-aineet ja tuotettavat tuotteet k�ytt�j�n valitseman taho tyypin mukaan.
	@FXML 
	private void handleType() {
		partyRawmaterialChoice.getItems().clear();
		partyProductChoice.getItems().clear();
		if(partyTypeChoice.getValue()=="Raaka-aine") {
			partyRawmaterialChoice.getItems().addAll("Rautamalmi","Karja");
	  		partyProductChoice.getItems().addAll("Rauta","Liha");
		}else if(partyTypeChoice.getValue()=="Jalostus") {
			partyRawmaterialChoice.getItems().addAll("Rauta","Liha");
	  		partyProductChoice.getItems().addAll("Ter�s","Pihvi");
		}
	}
	
	@FXML
	private void handleCancel() {
	    dialogStage.close();
	}
	
	@FXML
	   private void handleOk() {
	       if (isInputValid()) {
	           party.setPartyType(partyTypeChoice.getValue());
	           party.setPartyName(partyName.getText());
	           party.setResource(partyRawmaterialChoice.getValue());
	           party.setProduct(partyProductChoice.getValue());
	           party.setMoney(Float.parseFloat(partyMoney.getText()));
	           party.setWorkForce(Integer.parseInt(partyPersons.getText()));

	           okClicked = true;
	           dialogStage.close();
	       }
	}
	
	@FXML
	private boolean isInputValid() {
        String errorMessage = "";

        if (partyTypeChoice.getValue() == null) {
            errorMessage += "Valitse taho tyyppi!\n"; 
        }
        if (partyName.getText() == null || partyName.getText().length() == 0) {
            errorMessage += "Nime� taho!\n"; 
        }
        if (partyRawmaterialChoice.getValue() == null) {
            errorMessage += "Valitse tahon tarvitsema raaka-aine\n"; 
        }

        if (partyProductChoice.getValue() == null) {
            errorMessage += "Valitse tahon tuottama tuote!\n"; 
        }

        if (partyMoney.getText() == null || partyMoney.getText().length() == 0) {
            errorMessage += "Tahon aloitus raham��r� on virheellinen!\n"; 
        } else {
            // try to parse the money amount into an float.
            try {
                Float.parseFloat(partyMoney.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Tahon aloitus raham��r�n t�ytyy olla numero!\n"; 
            }
        }

        if (partyPersons.getText() == null || partyPersons.getText().length() == 0) {
            errorMessage += "Tahon aloitus ty�ntekij�iden m��r�n on virheellinen!\n";
        } else {
            // parse Int persons.
            try {
                Integer.parseInt(partyPersons.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Tahon aloitus ty�ntekij�iden m��r�n t�ytyy olla kokonaisluku!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Korjaa v��rin t�ytetyt kent�t");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
    
}
