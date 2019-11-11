package otp.markkinasim.simulation;

import javax.persistence.Transient;

public class Producer extends Party {
	public Producer() {
		super();
	}
	
	@Override
	public void produce() {
		double producedAmount = checkProducedAmount();
		producedItemInventory.addAmount((int)producedAmount);
		calculateRemainder(producedAmount);
	}
	
	 @Override
	 public void evaluate() {
		 //TODO tekoäly
		 putForSale();
	 }
}
