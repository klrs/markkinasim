package otp.markkinasim.simulation;

import javax.persistence.Transient;

import otp.markkinasim.view.View;

public class Producer extends Party {
	public Producer() {
		super();
	}
	
	@Override
	public void produce() {
		double producedAmount = checkProducedAmount();
		producedItemInventory.addAmount((int)producedAmount);
		View.getInstance().writeSimulationLog(this.partyName + " produced " + producedAmount +
				" " + this.productToProduce.getProductName());
		calculateRemainder(producedAmount);
	}
	
	 @Override
	 public void evaluate() {
		 //TODO tekoäly
		 putForSale();
	 }
}
