package log;

import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;

public class TradeObject implements HistoryObject{
	private int day;
	private Party buyerParty;
	private Party sellerParty;
	private Person buyerPerson;
	private Product product;
	private int amount;
	private float price;
	
	private Accountant accountant;

	// Constructor for trade between two parties
	public TradeObject(Accountant accountant, int day, Party buyerParty, Party sellerParty, 
						Product product, int amount, float price) {
		this.day = day;
		this.buyerParty = buyerParty;
		this.sellerParty = sellerParty;
		this.product = product;
		this.amount = amount;
		this.price = price;
		this.accountant = accountant;
		register();
		
	}
	
	public void register() {
		accountant.registerTrade(this);
	}
	
	public int getDay() {
		return day;
	}
	

}
