package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Party {
	
	private Inventory inventory;
	private ArrayList<Item> sellables;
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
	
	public void addToInventory(Product product, int amount) throws InvalidParameterException {
		inventory.add(new Item(product, amount));
	}
	
	public void produce() {
		if(checkProducable(productToProduce)) {
			Item item = inventory.search(productToProduce.id);
			try {
				item.subtractAmount(1);
				inventory.add(new Item(productToProduce, 1));
			}
			catch(InvalidParameterException e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			System.out.println("Too few resources to produce!");
		}
	}
	private boolean checkProducable(Product productToProduce) {
		//Testaa onko Product tuotettavissa vertaamalla sitä inventoryssä oleviin itemeihin.
		//HUOM. TOIMII VAIN YHDELLÄ PRODUCTILLA. EI LISTOILLA. MUUTA?
		if(inventory.search(productToProduce.id) != null) { return true; } else { return false; }
	}

	public void buy() {
		// TODO Auto-generated method stub
		
	}

	public void sell() {
		// TODO Auto-generated method stub
		
	}
}
