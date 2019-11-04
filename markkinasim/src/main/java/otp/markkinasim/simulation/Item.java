package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class Item {
	//DATA TYPE FOR WRAPPING PRODUCT AND AMOUNT TOGETHER. HUOM PUBLIC FIELDIT!!
	public Party partyHolder;
	public Product product;
	public FloatProperty priceEach;
	public IntegerProperty amount;
	
	public Item(Product product, int amount, Party partyHolder) throws InvalidParameterException {
		if(amount > 0) {
			//jos määrä on <1 heittää exceptionia, koska item on tarkoitettu olemassaolevaksi vain kun itemissä on jtn.
			this.product = product;
			this.priceEach = new SimpleFloatProperty(0);
			this.amount = new SimpleIntegerProperty(amount);
			this.partyHolder = partyHolder;
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	public Item(Product product, float priceEach, int amount, Party partyHolder) throws InvalidParameterException {
		if(amount > 0) {
			//jos määrä on <1 heittää exceptionia
			this.product = product;
			this.priceEach = new SimpleFloatProperty(priceEach);
			this.amount = new SimpleIntegerProperty(amount);
			this.partyHolder = partyHolder;
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	public void subtractAmount(int subtractableAmount) throws InvalidParameterException {
		//PIENENNÄ ITEMIN MÄÄRÄÄ! KUNHAN PIENENNETTÄVÄ ARVO EI OLE ISOMPI KUIN ITEMIN MÄÄRÄ
		if(amount.get()-subtractableAmount >= 0) {
			amount.set(amount.get()-subtractableAmount);
		}
		else { throw new InvalidParameterException("Subtractable value too large!"); }
	}
	public void addAmount(int addableAmount) {
		//lisää määrä itemin amounttiin.
		amount.set(amount.get() + addableAmount);
	}
	public IntegerProperty amountProperty() {
		// TODO Auto-generated method stub
		return amount;
	}
}
