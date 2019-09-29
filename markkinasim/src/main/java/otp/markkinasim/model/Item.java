package otp.markkinasim.model;

import java.security.InvalidParameterException;

public class Item {
	//DATA TYPE FOR WRAPPING PRODUCT AND AMOUNT TOGETHER. HUOM PUBLIC FIELDIT!!
	public Product product;
	public float priceEach;
	public int amount;
	
	public Item(Product product, int amount) throws InvalidParameterException {
		if(amount > 0) {
			//jos määrä on <1 heittää exceptionia, koska item on tarkoitettu olemassaolevaksi vain kun itemissä on jtn.
			this.product = product;
			this.priceEach = 0;
			this.amount = amount;
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	public Item(Product product, float priceEach, int amount) throws InvalidParameterException {
		if(amount > 0) {
			//jos määrä on <1 heittää exceptionia
			this.product = product;
			this.priceEach = priceEach;
			this.amount = amount;
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	public void subtractAmount(int subtractableAmount) throws InvalidParameterException {
		//PIENENNÄ ITEMIN MÄÄRÄÄ! KUNHAN PIENENNETTÄVÄ ARVO EI OLE ISOMPI KUIN ITEMIN MÄÄRÄ
		if(amount-subtractableAmount >= 0) {
			amount -= subtractableAmount;
		}
		else { throw new InvalidParameterException("Subtractable value too large!"); }
	}
	public void addAmount(int addableAmount) {
		//lisää määrä itemin amounttiin.
		amount += addableAmount;
	}
}
