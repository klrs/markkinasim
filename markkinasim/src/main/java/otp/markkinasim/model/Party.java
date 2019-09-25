package otp.markkinasim.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Party {
	
	private StringProperty partyType;
	private StringProperty partyName;
	private StringProperty product;
	private StringProperty resource;

	private float money;
	private int actionPoints;
	private int expenses;
	private int workForce;
	
	private int locationX;
	private int locationY;
	
	
	
	private int primaryResources;
	
	public Party() {
		this(null,null,null,null);
	}
	public Party(String partyType, String partyName, String product, String resource) {
		this.partyType = new SimpleStringProperty("partyType");
		this.partyName = new SimpleStringProperty("partyName");
		this.product = new SimpleStringProperty("product");
		this.resource = new SimpleStringProperty("resource");
		
		setMoney(50000);
		setActionPoints(10);
		setExpenses(2);
		setWorkForce(1);
		
		setLocationX(0);
		setLocationY(0);
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
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getActionPoints() {
		return actionPoints;
	}

	public void setActionPoints(int actionPoints) {
		this.actionPoints = actionPoints;
	}

	public int getExpenses() {
		return expenses;
	}

	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}

	public int getWorkForce() {
		return workForce;
	}

	public void setWorkForce(int workForce) {
		this.workForce = workForce;
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
		return primaryResources;
	}

	public void setPrimaryResources(int primaryResources) {
		this.primaryResources = primaryResources;
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

}
