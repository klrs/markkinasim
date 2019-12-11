package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import otp.markkinasim.controller.Controller;


/**
 * Manufacturer tuottaa tuotetta simulaatioon.
 * Toimii samalla periaatteella kuin Producer, mutta
 * tarvitsee toista tuotetta tuottaakseen itse mitään.
 * @see Party
 * @see Producer
 * @author Kalle Rissanen
 * @version 1.0
 */
public class Manufacturer extends Party {
	//jalostaja-taho. Tuottaa producteja, mutta vaatii raaka-aineita(product) toimi
	
//	public Manufacturer(String partyName, float money, Product productToProduce) {
//		super(partyName, money, productToProduce);
//	}
	public Manufacturer() {
		super();
	}
	
	public Manufacturer(Party party) {
		super();
		super.setId(party.getId());
		super.setPartyName(party.getPartyName());
		super.setMoney(party.getMoney());
		super.setProductToProduce(party.getProductToProduce());
		super.setEffency(party.getEffency());
		super.setQuality(party.getQuality());
		
	}
	
	/**
	 * Tuottaa vuorollaan tietyn määrän tuotetta.
	 */
	
	@Override
	public void produce() {
		
		//tuotettava määrä
		double producedAmount = checkProducedAmount();
		
		//Party pystyy tuottamaan myös ei-kokonaislukuja.
		//Tässä lasketaan jäännös mukaan, jos se on >=1
		producedAmount = producedAmount + calculateRemainder(producedAmount);
		
		//Manufacturer ominaisuus. Ei voi tuottaa jos ei ole tarpeeksi
		//tarvittavaa tuotetta.
		if(producedAmount > neededItemInventory.amount.get()) {
			int possibleAmount = neededItemInventory.amount.get();
			
			producedItemInventory.addAmount(possibleAmount);
			neededItemInventory.subtractAmount(possibleAmount);
			
			if(possibleAmount == 0) {
				Controller.log("NO_PRODUCE", possibleAmount, partyName.get(), productToProduce.getProductName());
			}
			else {
				Controller.log("PRODUCE", possibleAmount, partyName.get(), productToProduce.getProductName());
			}
		}
		else {
			producedItemInventory.addAmount((int)producedAmount);
			neededItemInventory.subtractAmount((int)producedAmount);
			
			Controller.log("PRODUCE", producedAmount, partyName.get(), productToProduce.getProductName());
		}
//		try {
//			neededItemInventory.subtractAmount((int)producedAmount);
//			producedItemInventory.addAmount((int)producedAmount);
//			calculateRemainder(producedAmount);
//		}
//		catch(InvalidParameterException e) {
//			//ERROR HANDLAA VIDU
//			System.out.println(e.getMessage());
//		}
	}
	
	/**
	 * Kutsuu paljon misc metodeita. Evalueteta kutsutaan kerran simulaation
	 * iteraatiossa.
	 * @param day, simulaation päivä
	 */
	public void evaluate(int day) {
		
		buyNeededProduct((int)checkProducedAmount());
		putForSale();
		changePrice();
		kickEmployees(day);
	}
	
	/**
	 * Ostaa tarvittavaa tuotetta
	 * @param amount, ostettava määrä
	 */
	private void buyNeededProduct(int amount) {
		
		//TODO TEKOÄLY
		int left = amount;
		List<Item> items = market.checkItems(productToProduce.getProductNeeded());
		if(left > 0) {
			Item cheapest = market.findNextCheapestItem(items);
			int buyAmount = 0;
			
			if(cheapest.amount.get() < left) {
				buyAmount = cheapest.amount.get();
			} else {
				buyAmount = left;
			}
			
			if(money.get() >= cheapest.priceEach.get() * buyAmount) {
				market.buy(this, cheapest, buyAmount);
			}
			market.cleanEmpty();
		}
		//market.buy(this, items, (int)checkProducedAmount());
	}
}
