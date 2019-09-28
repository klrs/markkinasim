package otp.markkinasim.model;

import java.security.InvalidParameterException;

public class Item {
	//DATA TYPE FOR WRAPPING PRODUCT AND AMOUNT TOGETHER
	public Product product;
	public float priceEach;
	public int amount;
	
	public Item(Product product, int amount) throws InvalidParameterException {
		if(amount > 0) {
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
			this.product = product;
			this.priceEach = priceEach;
			this.amount = amount;
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	public void subtractAmount(int subtractableAmount) throws InvalidParameterException {
		if(amount-subtractableAmount >= 0) {
			amount -= subtractableAmount;
		}
		else { throw new InvalidParameterException("Subtractable value too large!"); }
	}
	public void addAmount(int addableAmount) {
		amount += addableAmount;
	}
}
