package otp.markkinasim.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import otp.markkinasim.model.Product;
import otp.markkinasim.model.Rawmaterial;

public class ProductEditDialogController {
	private Stage dialogStage;
    private Product product;
    private Rawmaterial rawmaterial;
    private boolean okClicked = false;
    private IView view;
    
    @FXML
    private TextField productName;
    @FXML
    private ChoiceBox<String> productRawmaterial;
    @FXML
    private TextField productRawmaterialNeeded;
    
    //inits
  	@FXML
    private void initialize() {

  	}

  	public Stage getDialogStage() {
		return dialogStage;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setProduct(Product product) {
		this.product = product;
		productName.setText(product.getProductName());
		productRawmaterial.setValue(product.getRawmaterialName()); //TODO
		productRawmaterialNeeded.setText(Float.toString(product.getRawmaterialNeeded()));
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
	           product.setProductName(productName.getText());
	           product.setRawmaterialName(productRawmaterial.getValue());
	           product.setRawmaterialNeeded(Float.parseFloat(productRawmaterialNeeded.getText()));

	           okClicked = true;
	           dialogStage.close();
	       }
	}
	@FXML
	private boolean isInputValid() {
        String errorMessage = "";

        if (productName.getText() == null || productName.getText().length() == 0) {
            errorMessage += "Nime‰ tuote!\n"; 
        }
        if (productRawmaterial.getValue() == null) {
            errorMessage += "Valitse raaka-aine!\n"; 
        }
        if (productRawmaterialNeeded.getText() == null || productRawmaterialNeeded.getText().length() == 0) {
            errorMessage += "Anna tarvittavan raaka-aineen m‰‰r‰!\n"; 
        } else {
            // try to parse the money amount into an float.
            try {
                Float.parseFloat(productRawmaterialNeeded.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Tarvittavan raaka-aineen m‰‰r‰n t‰ytyy olla numeroarvo!\n"; 
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

	public void setRawmaterials(ObservableList<Rawmaterial> rawmaterialData) {
		for(Rawmaterial i: rawmaterialData) {
			productRawmaterial.getItems().add(i.getRawmaterialName());
		}
		
	}
}
