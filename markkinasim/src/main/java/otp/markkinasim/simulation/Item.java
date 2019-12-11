package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

/**
 * Item on tarkoitettu wrappaamaan tuote, määrä, hinta ja tuotteen omistaja 
 * yhdeksi datatyypiksi.
 * Huom. public-kentät!
 * @author Kalle Rissanen
 * @version 1.0
 */

public class Item {
	
	public Party partyHolder;
	public Product product;
	public FloatProperty priceEach;
	public IntegerProperty amount;
	
	/**
	 * @throws InvalidParameterException, jos amount on <0
	 */
	public Item(Product product, int amount, Party partyHolder) throws InvalidParameterException {
		if(amount >= 0) {
			this.product = product;
			this.priceEach = new SimpleFloatProperty(0);
			this.amount = new SimpleIntegerProperty(amount);
			this.partyHolder = partyHolder;
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	
	/**
	 * @throws InvalidParameterException, jos amount on <0
	 */
	public Item(Product product, float priceEach, int amount, Party partyHolder) throws InvalidParameterException {
		if(amount >= 0) {
			this.product = product;
			this.priceEach = new SimpleFloatProperty(priceEach);
			this.amount = new SimpleIntegerProperty(amount);
			this.partyHolder = partyHolder;
		}
		else {
			throw new InvalidParameterException("Invalid amount!");
		}
	}
	
	/**
	 * Pienentää itemin määrää
	 * @param subtractableAmount, pienennettävä määrä
	 * @throws InvalidParameterException, jos subtractableAmount on isompi kuin määrä.
	 */
	public void subtractAmount(int subtractableAmount) throws InvalidParameterException {

		if(amount.get()-subtractableAmount >= 0) {
			amount.set(amount.get()-subtractableAmount);
		}
		else { throw new InvalidParameterException("Subtractable value too large!"); }
	}
	
	/**
	 * Lisää itemin määrää
	 * @param addableAmount, lisättävä määrä
	 */
	public void addAmount(int addableAmount) {

		amount.set(amount.get() + addableAmount);
	}
	
	/**
	 * Palauttaa amount-kentän View-oliota varten.
	 * @return amount, määrä
	 */
	public IntegerProperty amountProperty() {

		return amount;
	}
}
