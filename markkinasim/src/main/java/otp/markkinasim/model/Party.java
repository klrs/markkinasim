package otp.markkinasim.model;

import java.util.ArrayList;

public class Party implements ITrader {
	
	private ArrayList<Person> employees = new ArrayList<Person>();
	private Item productToUse = new Item(new Product("Cow", 0, 10), 500);
	private Item productToSell = new Item(new Product("Beef patty", 1, 20), 0);
	
	private String partyType;
	private float money;
	private float expenses;
	private int actionPoints;
	private int resources;
	//private int productToSell;
	
	//private int locationX;
	//private int locationY;
	
	//private String productName;
	
	public Party() {
		setPartyType("Jimbo's Beef");
		setMoney(50000);
		//setProductName("Example product");
		//setActionPoints(10);
		//setExpenses(2);
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

	public float getExpenses() {
		return expenses;
	}

	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}

	/*
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	*/

	public int getResources() {
		return resources;
	}

	public void setPrimaryResources(int resources) {
		this.resources = resources;
	}

	public ArrayList<Person> getEmployees() {
		return employees;
	}

	public void addEmployee(Person employee) {
		employees.add(employee);
	}
	
	public void removeEmployee(int i) {
		employees.remove(i);
	}
	
	public void produce() {
		productToUse.amount--;
		productToSell.amount += 10;
		System.out.println(productToUse.amount + " " + productToSell.amount);
	}

	public void Buy() {
		// TODO Auto-generated method stub
		
	}

	public void Sell() {
		// TODO Auto-generated method stub
		
	}

}
