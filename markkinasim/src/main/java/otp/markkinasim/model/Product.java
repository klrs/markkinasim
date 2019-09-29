package otp.markkinasim.model;

import java.util.ArrayList;

public class Product {
	public static int nextId = 0;	//ID JÄRJESTELMÄ AINA OLIOTA ILMENNETTÄESSÄ. KTS. CONSTRUCTOR.
	public final int id;
	
	private String productName;
	private int productNeededId;
	private int price;

	public Product(String productName) {
		id = nextId;
		nextId++;
		
		this.productName = productName;
		this.productNeededId = -1; //SAATTAA AIHEUTTAA ONGELMIA!!
	}
	public Product(String productName, ArrayList<Product> productsNeeded) {
		id = nextId;
		nextId++;
		
		this.productName = productName;
		//this.productNeeded = productsNeeded;
	}
	public Product(String productName, int productNeededId) {
		id = nextId;
		nextId++;
		
		this.productName = productName;
		//productNeeded = new ArrayList<Product>();
		this.productNeededId = productNeededId;
	}
	public int getProductNeededId() {
		return productNeededId;
	}
	public String getProductName() {
		return productName;
	}
}
