package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.*;
import javax.persistence.*;

@Entity
@Table(name="Party")
@Access(AccessType.FIELD)


public class Party {
	//party elikkä kaupankäyntiä harrastava taho
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	protected int id;
	
	@Transient
	protected Inventory inventory;
	@Transient
	protected ObservableList<Item> sellables;	//Myytävät productit -lista. EI KÄYTTÖÄ VIELÄ. TODO TODO TODO
	@Transient
	protected StringProperty partyName;
	@Transient
	protected FloatProperty money;
	@Transient
	protected ObservableList<Person> employees;	//TODO TODO
	@Transient
	protected Product productToProduce;		//TUOTETTAVA TUOTE. TÄLLÄ HETKELLÄ PARTYT TUOTTAVAT VAIN YHTÄ TUOTETTA

	public Party(String partyName, float money, Product productToProduce) {
		inventory = new Inventory();
		this.partyName = new SimpleStringProperty(partyName);
		this.money = new SimpleFloatProperty(money);
		this.productToProduce = productToProduce;
	}
	public Party() {
		inventory = new Inventory();
		this.partyName = new SimpleStringProperty();
		this.money = new SimpleFloatProperty();
		this.productToProduce = productToProduce;
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name="party_name")
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
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public ObservableList<Item> getSellables() {
		return sellables;
	}
	public void setSellables(ObservableList<Item> sellables) {
		this.sellables = sellables;
	}
	public FloatProperty getMoney() {
		return money;
	}
	public void setMoney(FloatProperty money) {
		this.money = money;
	}
	public ObservableList<Person> getEmployees() {
		return employees;
	}
	public void setEmployees(ObservableList<Person> employees) {
		this.employees = employees;
	}
	public Product getProductToProduce() {
		return productToProduce;
	}
	public void setProductToProduce(Product productToProduce) {
		this.productToProduce = productToProduce;
	}
	public void setPartyName(String partyName) {
		this.partyName.set(partyName);
	}
	
	
	
}
