package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.*;
import javax.persistence.*;
import javax.swing.text.SimpleAttributeSet;

import org.hibernate.bytecode.enhance.internal.tracker.SimpleFieldTracker;

import javafx.collections.FXCollections;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXConsole;

@Entity
@Table(name = "Party")
@Access(AccessType.FIELD)

public class Party {
	// party elikkä kaupankäyntiä harrastava taho
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;

	@Transient
	protected Inventory inventory;
	@Transient
	protected Inventory sellables; // Myytävät productit -lista. EI KÄYTTÖÄ VIELÄ. TODO TODO TODO
	@Transient
	protected StringProperty partyName;
	@Transient
	protected FloatProperty money;
	@Transient
	protected ObservableList<Person> employees; // TODO TODO
	@Transient
	protected Product productToProduce; // TUOTETTAVA TUOTE. TÄLLÄ HETKELLÄ PARTYT TUOTTAVAT VAIN YHTÄ TUOTETTA
	@Column(name = "productToProduceId")
	protected int productToProduceId;
	@Transient
	protected FloatProperty defaultSalary; // daily salaary
	@Transient
	protected FloatProperty effency;
	@Transient
	protected FloatProperty quality;

	public Party(String partyName, float money, Product productToProduce) {
		inventory = new Inventory();
		sellables = new Inventory();
		employees = FXCollections.observableArrayList();
		this.partyName = new SimpleStringProperty(partyName);
		this.money = new SimpleFloatProperty(money);
		this.productToProduce = productToProduce;
		this.defaultSalary = new SimpleFloatProperty(5f);
		this.effency = new SimpleFloatProperty(1f);
		this.quality = new SimpleFloatProperty(1f);
	}

	public Party() {
		inventory = new Inventory();
		sellables = new Inventory();
		employees = FXCollections.observableArrayList();
		this.partyName = new SimpleStringProperty();
		this.money = new SimpleFloatProperty();
		this.productToProduce = null;
		this.defaultSalary = new SimpleFloatProperty(5f);
		this.effency = new SimpleFloatProperty(1f);
		this.quality = new SimpleFloatProperty(1f);
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "party_name")
	public String getPartyName() {
		return partyName.get();
	}

	public void addToInventory(Product product, int amount) throws InvalidParameterException {
		// LISÄÄ INVENTORYYN PRODUCTIN JA MÄÄRÄN WRAPPAAMALLA SEN ENSIN ITEM-OLIOON.
		// MUISTA KÄSITELLÄ InvalidParameterException!!!!!
		inventory.add(new Item(product, amount, this));
	}

	public void produce() {
		// TÄTÄ KUTSUTAAN KERRAN PÄIVÄSSÄ. LISÄÄ YHDEN PRODUCTIN INVENTORYYN.
		if (employees.size() > 0) {
			inventory.add(new Item(productToProduce, employees.size(), this));
		} else {
			System.out.println("Company is shit");
		}
	}

	public ArrayList<Item> searchInventory() {
		// HAE *KAIKKI* ITEMIT INVENTORYSTÄ
		return inventory.getItemList();
	}

	public Item searchInventoryItem(int productId) {
		// HAE TIETTY ITEM INVENTORYSTÄ
		return inventory.search(productId);
	}

	public void setItemSellable(int productId) {
		// laittaa KAIKKI kyseiset itemit sellableksi!
		Item itemToSell = searchInventoryItem(productId);
		if (itemToSell != null) {
			itemToSell.priceEach = new SimpleFloatProperty(10);
			sellables.add(itemToSell);
			inventory.deleteItem(itemToSell);
		}
		// else ilmoita?
	}

	public void setItemSellable() {
		// laittaa kaikki productit tyyppiä productToProduce sellableksi!!
		setItemSellable(productToProduce.id);
	}

	public ArrayList<Item> searchSellables() {
		return sellables.getItemList();
	}

	public void addMoney(float addableMoney) {
		money.set(money.get() + addableMoney);
	}

	public boolean employeesNeeded() {
		return true;
	}

	public void addEmployee(Person employee) {
		employees.add(employee);
	}

	public void paySalaries() {
		for (Person p : employees) {
			money.set(money.get() - defaultSalary.get());
			p.addMoney(defaultSalary.get());
		}
	}

	public void buyProduct(ObservableList<Party> partyList) {
		// only manufacturer
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

	public Inventory getSellables() {
		return sellables;
	}

	public void setSellables(Inventory sellables) {
		this.sellables = sellables;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "money")
	public float getMoney() {
		return money.get();
	}

	public void setMoney(float money) {
		this.money.set(money);
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

	public String getProductToProduceName() {
		if(productToProduce!=null) {
		return productToProduce.getProductName();
		}else {
			return null;
		}
	}

	public StringProperty partyNameProperty() {
		return partyName;
	}

	public StringProperty productToProduceProperty() {
		if(productToProduce!=null) {
		return productToProduce.productNameProperty();
		}else {
			return null;
		}
		
	}

	public FloatProperty moneyProperty() {
		return money;
	}

	public FloatProperty effencyProperty() {
		return effency;
	}

	public FloatProperty qualityProperty() {
		return quality;
	}

	public int getProductToProduceId() {
		return productToProduceId;
	}

	public void setProductToProduceId(int productToProduceId) {
		this.productToProduceId = productToProduceId;
	}
	@Access(AccessType.PROPERTY)
	@Column(name = "effency")
	public float getEffency() {
		return effency.get();
	}

	public void setEffency(float effency) {
		this.effency.set(effency);
	}
	@Access(AccessType.PROPERTY)
	@Column(name = "quality")
	public float getQuality() {
		return quality.get();
	}

	public void setQuality(float quality) {
		this.quality.set(quality);;
	}

	public Item searchSellablesItem(int productId) {
		// HAE TIETTY ITEM Sellablesseista;
		return sellables.search(productId);
	}

}
