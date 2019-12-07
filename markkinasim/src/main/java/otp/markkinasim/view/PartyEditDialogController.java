package otp.markkinasim.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Product;
/**
* Tämä luokka on tahon muokkaus ja luonti ponnahdus ikkunan kontrolleri.
* 
* @author Joonas Lapinlampi
*/
public class PartyEditDialogController {
	
	private Stage dialogStage;
    private Party party;
    private boolean okClicked = false;
    private IView view;
    @FXML
    private TextField partyName;
    @FXML
    private ChoiceBox<String> partyProductChoice;
    @FXML
    private TextField partyMoney;
    @FXML 
    private TextField partyRawmaterial;

    /**
	 * Tuodaan view luokan ilmentymän viite ja haetaan productData lista, jonka sisältö asetetaan ikkunan ChoiceBox:n.
	 * 
	 * @param view		view luokan ilmentymä
	 */
  	public void setView(View view) {
  		this.view = view;
  		if(!view.getAllProductData().isEmpty()) {
  			for(Product i:view.getAllProductData()) {
  				partyProductChoice.getItems().add(i.getProductName());
  			}
  		}
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
	 * Asetetaa luotava tai muokattava taho.
	 * 
	 * @param party		party olio joka sisältää muokattavan tahon tiedot tai on tyhjä uutta tahoa luotaessa
	 */
	public void setParty(Party party) {
		this.party = party;

		partyName.setText(party.getPartyName());
		if(party.getProductToProduce()!=null) {
		partyProductChoice.setValue(party.getProductToProduceName());
		}
		partyMoney.setText(Float.toString(party.getMoney()));

	}
	
	@FXML  //tuotettavan tuotteen valinta ja tarvittavan raaka-aineen asettaminen tuotteen mukaan
	private void handleProductChoice() {
		for(Product i:view.getAllProductData()) {
			if(i.getProductName() == partyProductChoice.getValue()) {
				if(i.getProductNeededId()>=0) {
					for(Product I:view.getAllProductData()) {
						if(i.getProductNeededId() == I.getId()) {
							partyRawmaterial.setText(I.getProductName());
							break;
						}
					}
				}else {
					partyRawmaterial.setText(view.getLanguage().getProperty("rawmaterialNotNeeded"));
					break;
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
	
	@FXML	//tarkistetaan että kaikki kentät on täytetty oikein ennen muutosten tallentamista
	   private void handleOk() {
	       if (isInputValid()) {
	           party.setPartyName(partyName.getText());
	           for(Product i:view.getAllProductData()) {
	        	   if(partyProductChoice.getValue() == i.getProductName()) {
	        		   party.setProductToProduce(i);
	        		   party.setProductToProduceId(i.getId());
	        	   }
	           }
	           party.setMoney(Float.parseFloat(partyMoney.getText()));

	           okClicked = true;
	           dialogStage.close();
	       }
	}
	
	@FXML	//kenttien tarkistus metodi
	private boolean isInputValid() {
        String errorMessage = "";

        if (partyName.getText() == null || partyName.getText().length() == 0) {
            errorMessage += view.getLanguage().getProperty("nameParty")+"\n"; 
        }
        
        if (partyProductChoice.getValue() == null) {
            errorMessage += view.getLanguage().getProperty("selectProduct")+"\n"; 
        }

        if (partyMoney.getText() == null || partyMoney.getText().length() == 0) {
            errorMessage += view.getLanguage().getProperty("partyMoneyError")+"\n"; 
        } else {
            try {
                Float.parseFloat(partyMoney.getText());
            } catch (NumberFormatException e) {
                errorMessage += view.getLanguage().getProperty("partyMoneyNotNumber")+"\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
           
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
