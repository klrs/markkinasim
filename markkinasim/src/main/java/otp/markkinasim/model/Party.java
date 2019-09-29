package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Party {
	//party elikkä kaupankäyntiä harrastava taho
	
	protected Inventory inventory;
	protected ArrayList<Item> sellables;	//Myytävät productit -lista. EI KÄYTTÖÄ VIELÄ. TODO TODO TODO
	protected String partyName;
	protected float money;
	protected ArrayList<Person> employees;	//TODO TODO
	protected Product productToProduce;		//TUOTETTAVA TUOTE. TÄLLÄ HETKELLÄ PARTYT TUOTTAVAT VAIN YHTÄ TUOTETTA
	
	public Party(String partyName, float money, Product productToProduce) {
		inventory = new Inventory();
		this.partyName = partyName;
		this.money = money;
		this.productToProduce = productToProduce;
	}
	public String getPartyName() {
		return partyName;
	}
	
	public void addToInventory(Product product, int amount) throws InvalidParameterException {
		//LISÄÄ INVENTORYYN PRODUCTIN JA MÄÄRÄN WRAPPAAMALLA SEN ENSIN ITEM-OLIOON.
		//MUISTA KÄSITELLÄ InvalidParameterException!!!!!
		inventory.add(new Item(product, amount));
	}
	
	public void produce() {
		//TÄTÄ KUTSUTAAN KERRAN PÄIVÄSSÄ. LISÄÄ YHDEN PRODUCTIN INVENTORYYN.
		inventory.add(new Item(productToProduce, 1));
	}
	
	public ArrayList<Item> searchInventory() {
		//HAE *KAIKKI* ITEMIT INVENTORYSTÄ
		return inventory.getItemList();
	}
	public Item searchInventoryItem(int productId) {
		//HAE TIETTY ITEM INVENTORYSTÄ
		return inventory.search(productId);
	}
}
