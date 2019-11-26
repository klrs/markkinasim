package otp.markkinasim.view;
/**
*
* @author Joonas Lapinlampi
*/
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import otp.markkinasim.simulation.Product;


public class RawmaterialEditDialogController {
	private Stage dialogStage;
    private Product rawmaterial;
    private boolean okClicked = false;
    private IView view;
    
    @FXML
    private TextField rawmaterialName;
    
    @FXML
    private void initialize() {
    	this.view = View.getInstance();
  	}
    
  	public Stage getDialogStage() {
		return dialogStage;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setRawmaterial(Product rawmaterial) {
		this.rawmaterial = rawmaterial;
		rawmaterialName.setText(rawmaterial.getProductName());
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
	       rawmaterial.setProductName(rawmaterialName.getText());
	          
	       okClicked = true;
	       dialogStage.close();
	    }
	}
	
	@FXML
	private boolean isInputValid() {
        String errorMessage = "";

        if (rawmaterialName.getText() == null || rawmaterialName.getText().length() == 0) {
            errorMessage += view.getLanguage().getProperty("nameRawmaterial")+"\n"; 
        }
       
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText(view.getLanguage().getProperty("invalidFields"));
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}
