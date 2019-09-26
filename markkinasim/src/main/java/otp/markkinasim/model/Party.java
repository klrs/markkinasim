package otp.markkinasim.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Party {
	
	private StringProperty partyType;
	private StringProperty partyName;
	private StringProperty product;
	private StringProperty resource;

	private FloatProperty money;
	private IntegerProperty actionPoints;
	private IntegerProperty expenses;
	private IntegerProperty workForce;
	private IntegerProperty primaryResources;
	
	private int locationX;
	private int locationY;
	
	public Party() {
		this(null,null,null,null);
	}
	public Party(String partyType, String partyName, String product, String resource) {
		this.partyType = new SimpleStringProperty("partyType");
		this.partyName = new SimpleStringProperty("partyName");
		this.product = new SimpleStringProperty("product");
		this.resource = new SimpleStringProperty("resource");
		
		this.money = new SimpleFloatProperty(50000);
		this.actionPoints = new SimpleIntegerProperty(10);
		this.actionPoints = new SimpleIntegerProperty(10);
		this.expenses = new SimpleIntegerProperty(2);
		this.workForce = new SimpleIntegerProperty(1);
	}

	public String getPartyName() {
		return partyName.get();
	}
	
	public String getPartyType() {
		return partyType.get();
	}
	
	public String getResource() {
		return resource.get();
	}
	public void setResource(String resource) {
		this.resource.set(resource);
	}
	
	public void setPartyType(String partyType) {
		this.partyType.set(partyType);
	}
	
	public void setPartyName(String partyName) {
		this.partyName.set(partyName);
	}

	public float getMoney() {
		return money.get();
	}

	public void setMoney(float money) {
		this.money.set(money);
	}

	public int getActionPoints() {
		return actionPoints.get();
	}

	public void setActionPoints(int actionPoints) {
		this.actionPoints.set(actionPoints);
	}

	public int getExpenses() {
		return expenses.get();
	}

	public void setExpenses(int expenses) {
		this.expenses.set(expenses);
	}

	public int getWorkForce() {
		return workForce.get();
	}

	public void setWorkForce(int workForce) {
		this.workForce.set(workForce);
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public String getProduct() {
		return product.get();
	}

	public void setProduct(String product) {
		this.product.set(product);
	}

	public int getPrimaryResources() {
		return primaryResources.get();
	}

	public void setPrimaryResources(int primaryResources) {
		this.primaryResources.set(primaryResources);
	}
	
	public StringProperty partyTypeProperty() {
		return partyType;
	}
	
	public StringProperty partyNameProperty() {
		return partyName;
	}
	
	public StringProperty partyProductProperty() {
		return product;
	}
	public StringProperty partyResourceProperty() {
		return resource;
	}
	public FloatProperty partyMoneyProperty() {
		return money;
	}
	public IntegerProperty partyWorkForceProperty() {
		return workForce;
	}
}
