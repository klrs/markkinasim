package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.*;

public class Party {
	//party elikkä kaupankäyntiä harrastava taho
	
	protected static int nextId = 0;	//ID JÄRJESTELMÄ AINA OLIOTA ILMENNETTÄESSÄ. KTS. CONSTRUCTOR.
	protected final int id;

	protected Inventory inventory;
	protected ObservableList<Item> sellables;	//Myytävät productit -lista. EI KÄYTTÖÄ VIELÄ. TODO TODO TODO HUOM SIIRRÄ INVENTORYYN??
	protected StringProperty partyName;
	protected FloatProperty money;
	protected ObservableList<Person> employees;	//TODO TODO
	protected Product productToProduce;		//TUOTETTAVA TUOTE. TÄLLÄ HETKELLÄ PARTYT TUOTTAVAT VAIN YHTÄ TUOTETTA
	
	public Party(String partyName, float money, Product productToProduce) {
		id = nextId;
		nextId++;
		
		inventory = new Inventory();
		sellables = FXCollections.observableArrayList();
		this.partyName = new SimpleStringProperty(partyName);
		this.money = new SimpleFloatProperty(money);
		this.productToProduce = productToProduce;
	}
	public String getPartyName() {
		return partyName.get();
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
	public void setItemSellable(int productId) {
		//laittaa KAIKKI kyseiset itemit sellableksi!
		Item itemToSell = searchInventoryItem(productId);
		if(itemToSell != null) {
			itemToSell.priceEach = new SimpleFloatProperty(10);
			sellables.add(itemToSell);
			inventory.deleteItem(itemToSell);
		}
		//else ilmoita?
	}
	public void setItemSellable() {
		//laittaa kaikki productit tyyppiä productToProduce sellableksi!!
		setItemSellable(productToProduce.id);
	}
	public void searchSellables() {
		
	}
}
