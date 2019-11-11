package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class Manufacturer extends Party {
	//jalostaja-taho. Tuottaa producteja, mutta vaatii raaka-aineita(product) toimiakseen.
	
	public Manufacturer(String partyName, float money, Product productToProduce) {
		super(partyName, money, productToProduce);
	}
	public Manufacturer() {
		super();
	}
	
	@Override
	public void produce() {
		double producedAmount = checkProducedAmount();
		try {
			neededItemInventory.subtractAmount((int)producedAmount);
			producedItemInventory.addAmount((int)producedAmount);
			calculateRemainder(producedAmount);
		}
		catch(InvalidParameterException e) {
			//ERROR HANDLAA VIDU
			System.out.println(e.getMessage());
		}
	}
	@Override
	public void buyProduct(ObservableList<Party> partyList) {
		
	}
}
