package otp.markkinasim.simulation;

import javax.persistence.Transient;

import otp.markkinasim.controller.Controller;
import otp.markkinasim.view.View;


/**
 * Producer tarkoittaa tahoa, joka tuottaa raaka-aineta, eikä täten tarvitse
 * tuotteita tuottaakseen mitään. Perii Partyä.
 * @author Kalle Rissanen
 * @version 1.0
 * @see Party
 */
public class Producer extends Party {
	
	public Producer() {
		super();
	}
	
	public Producer(Party party) {
		super();
		super.setId(party.getId());
		super.setPartyName(party.getPartyName());
		super.setMoney(party.getMoney());
		super.setProductToProduce(party.getProductToProduce());
		super.setEffency(party.getEffency());
		super.setQuality(party.getQuality());
		
	}
	
	/**
	 * Tuottaa x määrän tuotetta. Ylikirjoittaa Partyn tyhjän toteutuksen.
	 */
	
	@Override
	public void produce() {
		double producedAmount = checkProducedAmount();
		producedAmount = producedAmount + calculateRemainder(producedAmount);
		
		producedItemInventory.addAmount((int)producedAmount);
		Controller.log("PRODUCE", producedAmount, partyName.get(), productToProduce.getProductName());
	}
	
	/**
	 * Tarkistaa onko taholla jotain parannettavaa.
	 * Ylikirjoittaa Partyn tyhjän toteutuksen.
	 * @param day, simulaation päivä
	 */
	
	 @Override
	 public void evaluate(int day) {
		 putForSale();
		 changePrice();
		 kickEmployees(day);
	 }
}
