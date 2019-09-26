package otp.markkinasim.view;

import javafx.stage.Stage;
import otp.markkinasim.model.Party;

public class PartyEditDialogController {
	
	private Stage dialogStage;
    private Party party;
    private boolean okClicked = false;
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
