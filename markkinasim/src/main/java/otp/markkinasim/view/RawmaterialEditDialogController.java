package otp.markkinasim.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import otp.markkinasim.simulation.Product;
/**
* Tämä luokka on raaka-aineen muokkaus ja luonti ponnahdus ikkunan kontrolleri.
* 
* @author Joonas Lapinlampi
*/

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
    
    /**
     * Asettaa stage johon ponnahdus ikkuna on kiinnitetty.
     * 
     * @param dialogStage		käytettävä stage
     */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Asetetaan raaka-ainetta edustava product olio
	 * 
	 * @param rawmaterial
	 */
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
	
	//ok painikkeen toiminta tarkistetaan että kaikki kentät ovat täytetty oikein ennen muutosten hyväksymistä
	@FXML
	private void handleOk() {
	    if (isInputValid()) {
	       rawmaterial.setProductName(rawmaterialName.getText());
	          
	       okClicked = true;
	       dialogStage.close();
	    }
	}
	
	//kenttien sisällön tarkistus
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
