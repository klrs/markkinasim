package otp.markkinasim.view;
/**
*
* @author Joonas Lapinlampi
*/
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import otp.markkinasim.simulation.Product;

public class ProductEditDialogController {
	private Stage dialogStage;
    private Product product;
    private boolean okClicked = false;
    private IView view;
    
    @FXML
    private TextField productName;
    @FXML
    private ChoiceBox<String> productRawmaterial;
    
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
	public void setView(View view) {
		this.view=view;
  		if(!view.getAllProductData().isEmpty()) {
  			for(Product i: view.getAllProductData()) {
  				productRawmaterial.getItems().add(i.getProductName());
  			}
  		}
	}
	public void setProduct(Product product) {
		this.product = product;
		productName.setText(product.getProductName());
		if(product.getProductNeededId()>=0) {
			for(Product i: view.getAllProductData()) {
     	   		if(i.getId()==product.getProductNeededId()) {
     		   		productRawmaterial.setValue(i.getProductName());
     	   		}
        	}
		}
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
	           
	           for(Product i: view.getAllProductData()) {
	        	   if(i.getProductName()==productRawmaterial.getValue()) {
	        		   product.setProductNeededId(i.getId());
	        	   }
	           }
	           okClicked = true;
	           dialogStage.close();
	       }
	}
	@FXML
	private boolean isInputValid() {
        String errorMessage = "";

        if (productName.getText() == null || productName.getText().length() == 0) {
            errorMessage += "Nime� tuote!\n"; 
        }
        if (productRawmaterial.getValue() == null) {
            errorMessage += "Valitse raaka-aine!\n"; 
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
