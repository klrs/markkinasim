package otp.markkinasim.model;

import java.util.ArrayList;

public class Product {
	private String productName;
	private Product productNeeded;
	private int price;

	public Product(String productName) {
		this.productName = productName;
		this.productNeeded = null; //MIGHT CAUSE PROBLEMS?
	}
	public Product(String productName, ArrayList<Product> productsNeeded) {
		this.productName = productName;
		//this.productNeeded = productsNeeded;
	}
	public Product(String productName, Product productNeeded) {
		this.productName = productName;
		//productNeeded = new ArrayList<Product>();
		this.productNeeded = productNeeded;
	}
	public Product getProductNeeded() {
		return productNeeded;
	}
}
