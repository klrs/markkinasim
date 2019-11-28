package otp.markkinasim.simulation;

import javax.persistence.Transient;

import otp.markkinasim.view.View;

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
	
	@Override
	public void produce() {
		double producedAmount = checkProducedAmount();
		System.out.println(producedItemInventory);
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
