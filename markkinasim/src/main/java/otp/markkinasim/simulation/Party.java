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
	//ÄLÄ ILMENNÄ TÄTÄ
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;
	@Transient
	protected Item neededItemInventory;
	@Transient
	protected Item producedItemInventory;
	@Transient
	protected float productRemainder;
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
	@Transient
	protected Market market;

	public Party(String partyName, float money, Product productToProduce) {
		//TURHA?
		employees = FXCollections.observableArrayList();
		this.neededItemInventory = null;
		this.producedItemInventory = null;
		this.partyName = new SimpleStringProperty(partyName);
		this.money = new SimpleFloatProperty(money);
		this.productToProduce = productToProduce;
		this.defaultSalary = new SimpleFloatProperty(5f);
		this.effency = new SimpleFloatProperty(1f);
		this.quality = new SimpleFloatProperty(1f);
		this.productRemainder = 0f;
		market = new Market();
	}

	public Party() {
		employees = FXCollections.observableArrayList();
		this.neededItemInventory = null;
		this.producedItemInventory = null;
		this.partyName = new SimpleStringProperty();
		this.money = new SimpleFloatProperty();
		this.productToProduce = null;
		this.defaultSalary = new SimpleFloatProperty(5f);
		this.effency = new SimpleFloatProperty(1f);
		this.quality = new SimpleFloatProperty(1f);
		this.productRemainder = 0f;
		market = new Market();
	}

	public void init(Product neededProduct) {
		//TÄTÄ KUULUU KUTSUA MISSÄ PARTYT ILMENNETÄÄN!
		
		if(neededProduct != null) {
			neededItemInventory = new Item(neededProduct, 0, this);
		}
		producedItemInventory = new Item(productToProduce, 0, this);
	}
	public void produce() {/*YLIKIRJOITA*/}
	protected double checkProducedAmount() {
		double producedAmount = employees.size() * effency.get();
		if(productRemainder >= 1) {
			producedAmount++;
			productRemainder =- 1;
		}
		
		return producedAmount;
	}
	protected void calculateRemainder(double producedAmount) {
		if(producedAmount % 1 > 0) {
			productRemainder =+ (float)producedAmount % 1;
		}
	}
	public void evaluate() {/*YLIKIRJOITA*/}
	protected void putForSale() {
		//laittaa kaikki vaan myyntiin
		market.vend(producedItemInventory);
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
	
	//											
	// GETTERS & SETTERS
	// JA MUUT VIEW/DB-METODIT ALLA !!
	//
	
	public void buyProduct(ObservableList<Party> partyList) {
		// only manufacturer
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "party_name")
	public String getPartyName() {
		return partyName.get();
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

	public Item getNeededItemInventory() {
		return neededItemInventory;
	}

	public void setNeededItemInventory(Item neededItemInventory) {
		this.neededItemInventory = neededItemInventory;
	}

	public Item getProducedItemInventory() {
		return producedItemInventory;
	}

	public void setProducedItemInventory(Item producedItemInventory) {
		this.producedItemInventory = producedItemInventory;
	}
	public float getProductRemainder() {
		return productRemainder;
	}

	public void setProductRemainder(float productRemainder) {
		this.productRemainder = productRemainder;
	}
}
