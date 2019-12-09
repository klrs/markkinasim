package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import otp.markkinasim.controller.Controller;
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
	/**
	 * Party edustaa kauppaa käyvää tahoa.
	 * Huom. Tämä luokka ei ole tarkoitettu ilmennettäväksi. Ilmennä tätä
	 * perivät aliluokat Producer ja Manufacturer.
	 * @author Kalle Rissanen
	 * @version 1.0
	 * @see Producer, Manufacturer
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;
	
	/**Inventaario tarvittaville tuotteille*/
	@Transient
	protected Item neededItemInventory;
	
	/**Inventaario tuotettaville tuotteille*/
	@Transient
	protected Item producedItemInventory;
	
	/**Jäljelle jäävä tuote. Tarvitaan koska tuotettu määrä on double.*/
	@Transient
	protected float productRemainder;
	@Transient
	protected StringProperty partyName;
	@Transient
	protected FloatProperty money;
	
	/**Lista Personeista, jotka ovat tällä taholla töissä*/
	@Transient
	protected ObservableList<Person> employees; // TODO TODO
	@Transient
	protected Product productToProduce; // TUOTETTAVA TUOTE. TÄLLÄ HETKELLÄ PARTYT TUOTTAVAT VAIN YHTÄ TUOTETTA
	@Column(name = "productToProduceId")
	protected int productToProduceId;
	@Transient
	protected FloatProperty defaultSalary; // daily salaary
	
	/**Teho, eli kuinka paljon taho saa aikaiseksi päivässä*/
	@Transient
	protected FloatProperty effency;
	
	/**Laatu, eli kuinka laadukkaita tuotetut tuotteet ovat*/
	@Transient
	protected FloatProperty quality;
	@Transient
	protected Market market;
	@Column(name = "PartyType")
	protected int partyType;

	public Party(int id, String partyName, float money, int productToProduceId) {
		employees = FXCollections.observableArrayList();
		this.id = id;
		this.neededItemInventory = null;
		this.producedItemInventory = null;
		this.partyName = new SimpleStringProperty(partyName);
		this.money = new SimpleFloatProperty(money);
		this.productToProduceId = productToProduceId;
		this.defaultSalary = new SimpleFloatProperty(5f);
		this.effency = new SimpleFloatProperty(1f);
		this.quality = new SimpleFloatProperty(1f);
		this.productRemainder = 0f;
		market = null;
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
		market = null;
	}

	public void init(Product neededProduct) {
		/**Teho, eli kuinka paljon taho saa aikaiseksi päivässä
		 * Tätä kutsutaan sieltä, missä partyt ilmennetään.
		 * @param neededProduct, tuote, jota party tarvitsee tuottaakseen
		 */
		
		if(neededProduct != null) {
			neededItemInventory = new Item(neededProduct, 0, this);
		}
		producedItemInventory = new Item(productToProduce, 0, this);
	}
	public void produce() {
		/**
		 * Metodi, joka tuottaa jotain. Producer ja Manufacturer tekevät oman
		 * implementaationsa tästä.
		 */
	}
	protected double checkProducedAmount() {
		/**
		 * Tarkistaa määrän, kuinka paljon party pystyy tuottamaan tuotetta.
		 * @return producedAmount, tuotettava määrä
		 */
		double producedAmount = employees.size() * effency.get();
		if(productRemainder >= 1) {
			producedAmount++;
			productRemainder =- 1;
		}
		
		return producedAmount;
	}
	protected int calculateRemainder(double producedAmount) {
		/**
		 * Tarkistaa onko jäännös yli 1. Jos on, palauttaa yhden lisää, muuten
		 * palauttaa 0.
		 * @return 0 tai 1, riippuen siitä onko remainder >=1
		 */
		
		if(producedAmount % 1 > 0) {
			productRemainder =+ (float)producedAmount % 1;
		}
		
		if(productRemainder <= 1) {
			productRemainder = productRemainder - 1;
			return 1;
		}
		else {
			return 0;
		}
	}
	public void evaluate(int day) {
		/**
		 * Metodi, jonka tarkistaa nykyisen tilanteen.
		 * Producer ja Manufacturer ylikirjoittaa tämän metodin.
		 * @param day, päivä, eli kuinka mones päivä on simulaatiossa menossa
		 */
	}
	protected void putForSale() {
		/**
		 * Laittaa kaikki tuotteet myyntiin.
		 */
		int amount = producedItemInventory.amount.get();
		market.vend(producedItemInventory);
		//producedItemInventory.amount.set(0);
		Controller.log("SET_SELL", amount, partyName.get(), producedItemInventory.product.getProductName());
	}
	protected void kickEmployees(int day) {
		/**
		 * Potkii työntekijöitä pihalle, mikäli partylla ei ole
		 * varaa maksaa sille palkkaa.
		 * @param day, päivä, eli kuinka mones päivä on simulaatiossa menossa
		 */
		if(day % 7 == 1) {
			while(employees.size() * defaultSalary.get() > money.get() &&
					!employees.isEmpty()) {
				Person p = employees.get(employees.size()-1);
				employees.remove(employees.size()-1);
				p.setEmployer(null);
				Controller.log("KICK_WORK", 0, partyName.get(), null);
			}
		}
	}
	
	protected void changePrice() {
		/**
		 * Muuttaa myytävän tuotteen hinnan yhdellä pienemmäksi.
		 */
		 if(employees.size() * defaultSalary.get() > money.get()) {
			 producedItemInventory.priceEach.set(producedItemInventory.priceEach.get() - 1);
		 }
	}

	public void addMoney(float addableMoney) {
		/**
		 * Lisää taholle rahaa.
		 * @param addableMoney, lisättävä rahamäärä
		 */
		money.set(money.get() + addableMoney);
	}

	public boolean employeesNeeded() {
		/**
		 * Tarkistaa tarvitseeko taho työntekijöitä.
		 * @param true/false, eli tarvitseeko taho työntekijöitä
		 */
		if(employees.size() * defaultSalary.get() < money.get()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addEmployee(Person employee) {
		/**
		 * Lisää taholle työntekijän
		 * @param employee, lisättävä employee-olio
		 */
		employees.add(employee);
	}

	public void paySalaries() {
		/**
		 * Maksaa kaikille työntekijöillä defaultSalary:n mukaisen palkan.
		 */
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

	public int getPartyType() {
		return partyType;
	}

	public void setPartyType(int partyType) {
		this.partyType = partyType;
	}
	
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}
}
