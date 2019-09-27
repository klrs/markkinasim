package otp.markkinasim.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import otp.markkinasim.model.Rawmaterial;

public class RawmaterialEditDialogController {
	private Stage dialogStage;
    private Rawmaterial rawmaterial;
    private boolean okClicked = false;
    
    @FXML
    private TextField rawmaterialName;
    @FXML
    private TextField rawmaterialSource;
    @FXML
    private TextField rawmaterialSourcePool;
    
  	public Stage getDialogStage() {
		return dialogStage;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setRawmaterial(Rawmaterial rawmaterial) {
		this.rawmaterial = rawmaterial;
		rawmaterialName.setText(rawmaterial.getRawmaterialName());
		rawmaterialSource.setText(rawmaterial.getRawmaterialSource()); 
		rawmaterialSourcePool.setText(Float.toString(rawmaterial.getrawmaterialSourcePool()));
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	public void setOkClicked(boolean okClicked) {
		this.okClicked = okClicked;
	}
	
	@FXML
	private void handleCancel() {
	    dialogStage.close();
	}
	@FXML
	   private void handleOk() {
	       if (isInputValid()) {
	           rawmaterial.setRawmaterialName(rawmaterialName.getText());
	           rawmaterial.setRawmaterialSource(rawmaterialSource.getText());
	           rawmaterial.setRawmaterialSourcePool(Float.parseFloat(rawmaterialSourcePool.getText()));

	           okClicked = true;
	           dialogStage.close();
	       }
	}
	
	@FXML
	private boolean isInputValid() {
        String errorMessage = "";

        if (rawmaterialName.getText() == null || rawmaterialName.getText().length() == 0) {
            errorMessage += "Nime‰ raaka-aine!\n"; 
        }
        if (rawmaterialSource.getText() == null || rawmaterialSource.getText().length() == 0) {
            errorMessage += "Anna raaka-aineelle l‰hde!\n"; 
        }
        if (rawmaterialSourcePool.getText() == null || rawmaterialSourcePool.getText().length() == 0) {
            errorMessage += "Anna l‰hteelle koko!\n"; 
        } else {
            // try to parse the money amount into an float.
            try {
                Float.parseFloat(rawmaterialSourcePool.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Raaka-aineen l‰hteen koko t‰ytyy olla numeroarvo!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Korjaa v‰‰rin t‰ytetyt kent‰t");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}
