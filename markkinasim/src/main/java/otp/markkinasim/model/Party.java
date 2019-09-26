package otp.markkinasim.model;

import java.util.ArrayList;

public class Party extends Trader {
	
	private String partyName;
	private float money;
	private ArrayList<Person> employees;
	private Product productToProduce;
	//private float expenses;
	//private int actionPoints;
	//private int resources;
	//private int productToSell;
	
	//private int locationX;
	//private int locationY;
	
	//private String productName;
	
	public Party(String partyName, float money, Product productToProduce) {
		super();
		this.partyName = partyName;
		this.money = money;
		this.productToProduce = productToProduce;
		//setProductName("Example product");
		//setActionPoints(10);
		//setExpenses(2);
	}
	
	public void addToInventory(Product product, int amount) {
		inventory.put(product, amount);
	}
	
	public void produce() {
		inventory.forEach((k, v) -> if(k == productToProduce.getProductNeeded()) {});
		productToProduce.getProductNeeded();
	}

	public void buy() {
		// TODO Auto-generated method stub
		
	}

	public void sell() {
		// TODO Auto-generated method stub
		
	}
}
