package otp.markkinasim.model;

public class Party {
	
	private String partyType;
	private float money;
	private int actionPoints;
	private int expenses;
	private int workForce;
	
	private int locationX;
	private int locationY;
	
	private String product;
	
	private int primaryResources;
	
	public Party() {
		setPartyType("Example Party");
		setProduct("Example product");
		setMoney(50000);
		setActionPoints(10);
		setExpenses(2);
		setWorkForce(1);
		
		setLocationX(0);
		setLocationY(0);
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
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
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getPrimaryResources() {
		return primaryResources;
	}

	public void setPrimaryResources(int primaryResources) {
		this.primaryResources = primaryResources;
	}

}
