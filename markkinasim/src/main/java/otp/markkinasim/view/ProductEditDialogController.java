package otp.markkinasim.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import otp.markkinasim.simulation.Product;
/**
* Tämä luokka on tuotteen muokkaus ja luonti ponnahdus ikkunan kontrolleri.
* 
* @author Joonas Lapinlampi
*/
public class ProductEditDialogController {
	private Stage dialogStage;
    private Product product;
    private boolean okClicked = false;
    private IView view;
    
    @FXML
    private TextField productName;
    @FXML
    private ChoiceBox<String> productRawmaterial;
    
    /**
     * Asettaa stage johon ponnahdus ikkuna on kiinnitetty.
     * 
     * @param dialogStage		käytettävä stage
     */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * Tuodaan view luokan ilmentymän viite ja haetaan productData lista, jonka sisältö asetetaan ikkunan ChoiceBox:n.
	 * 
	 * @param view		view luokan ilmentymä
	 */
	public void setView(View view) {
		this.view=view;
  		if(!view.getAllProductData().isEmpty()) {
  			for(Product i: view.getAllProductData()) {
  				productRawmaterial.getItems().add(i.getProductName());
  			}
  		}
	}
	
	/**
	 * Asetetaa luotava tai muokattava tuote.
	 * 
	 * @param product		product oli joka sisältää muokattavan productin tai tyhjän product olion uutta luotaessa
	 */
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
	
	@FXML //tarkistetaan että kaikki kentät ovat täytetty oikein ennen muutosten hyväksymistä
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
	
	@FXML  //kenttien sisällön tarkistus metodi
	private boolean isInputValid() {
        String errorMessage = "";

        if (productName.getText() == null || productName.getText().length() == 0) {
            errorMessage += view.getLanguage().getProperty("nameProduct")+"\n"; 
        }
        if (productRawmaterial.getValue() == null) {
            errorMessage += view.getLanguage().getProperty("selectRawmaterial")+"\n"; 
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
