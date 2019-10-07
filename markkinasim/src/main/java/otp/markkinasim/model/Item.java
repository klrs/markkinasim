package otp.markkinasim.model;

import java.security.InvalidParameterException;
import javafx.beans.property.*;

public class Item {
	//DATA TYPE FOR WRAPPING PRODUCT AND AMOUNT TOGETHER. HUOM PUBLIC FIELDIT!!
	public Product product;
	public FloatProperty priceEach;
	public IntegerProperty amount;
	
	public Item() {
		this.product = null;
		this.priceEach = new SimpleFloatProperty(0);
		this.amount = new SimpleIntegerProperty(0);
	}
	public Item(Product product, int amount) throws InvalidParameterException {
		if(amount > 0) {
			//jos m‰‰r‰ on <1 heitt‰‰ exceptionia, koska item on tarkoitettu olemassaolevaksi vain kun itemiss‰ on jtn.
			this.product = product;
			this.priceEach = new SimpleFloatProperty(0);
			this.amount = new SimpleIntegerProperty(amount);
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	public Item(Product product, float priceEach, int amount) throws InvalidParameterException {
		if(amount > 0) {
			//jos m‰‰r‰ on <1 heitt‰‰ exceptionia
			this.product = product;
			this.priceEach = new SimpleFloatProperty(priceEach);
			this.amount = new SimpleIntegerProperty(amount);
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	public void subtractAmount(int subtractableAmount) throws InvalidParameterException {
		//PIENENNƒ ITEMIN MƒƒRƒƒ! KUNHAN PIENENNETTƒVƒ ARVO EI OLE ISOMPI KUIN ITEMIN MƒƒRƒ
		if(amount.get()-subtractableAmount >= 0) {
			amount.add(-subtractableAmount);
		}
		else { throw new InvalidParameterException("Subtractable value too large!"); }
	}
	public void addAmount(int addableAmount) {
		//lis‰‰ m‰‰r‰ itemin amounttiin.
		amount.add(addableAmount);
	}
	public int getAmount() {
		return amount.get();
	}
	public IntegerProperty amountProperty() {
		return amount;		
	}
}