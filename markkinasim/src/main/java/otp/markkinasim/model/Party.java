package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.*;

public class Party {
	//party elikk� kaupank�ynti� harrastava taho
	
	protected Inventory inventory;
	protected ObservableList<Item> sellables;	//Myyt�v�t productit -lista. EI K�YTT�� VIEL�. TODO TODO TODO
	protected StringProperty partyName;
	protected FloatProperty money;
	protected ObservableList<Person> employees;	//TODO TODO
	protected Product productToProduce;		//TUOTETTAVA TUOTE. T�LL� HETKELL� PARTYT TUOTTAVAT VAIN YHT� TUOTETTA
	
	
	public Party() {
		inventory = new Inventory();
		this.partyName = new SimpleStringProperty();
		this.money = new SimpleFloatProperty();
		productToProduce=null;
	}
	
	public Party(String partyName, float money, Product productToProduce) {
		inventory = new Inventory();
		this.partyName = new SimpleStringProperty(partyName);
		this.money = new SimpleFloatProperty(money);
		this.productToProduce = productToProduce;
	}
	public String getPartyName() {
		return partyName.get();
	}
	public void setPartyName(String partyName ) {
		this.partyName.set(partyName);
	}
	
	public StringProperty partyNameProperty() {
		return partyName;
	}
	
	public float getMoney() {
		return money.get();
	}
	
	public void setMoney(float money) {
		this.money.set(money);
	}
	
	public FloatProperty moneyProperty() {
		return money;
	}
	
	public Product getProductToProduce(){
		return productToProduce;
	}
	public void setProductToProduce(Product productToProduce) {
		this.productToProduce = productToProduce;
	}
	public void addToInventory(Product product, int amount) throws InvalidParameterException {
		//LIS�� INVENTORYYN PRODUCTIN JA M��R�N WRAPPAAMALLA SEN ENSIN ITEM-OLIOON.
		//MUISTA K�SITELL� InvalidParameterException!!!!!
		inventory.add(new Item(product, amount));
	}
	
	public void produce() {
		//T�T� KUTSUTAAN KERRAN P�IV�SS�. LIS�� YHDEN PRODUCTIN INVENTORYYN.
		inventory.add(new Item(productToProduce, 1));
	}
	
	public ArrayList<Item> searchInventory() {
		//HAE *KAIKKI* ITEMIT INVENTORYST�
		return inventory.getItemList();
	}
	public Item searchInventoryItem(int productId) {
		//HAE TIETTY ITEM INVENTORYST�
		return inventory.search(productId);
	}
	
	public String getProductToProduceName() {
		return productToProduce.getProductName();
	}
	public StringProperty productToProduceProperty() {
		return productToProduce.productNameProperty();
	}
	
	
}