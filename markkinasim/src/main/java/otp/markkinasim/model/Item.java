package otp.markkinasim.model;

public class Item {
	//DATA TYPE FOR WRAPPING PRODUCT AND AMOUNT TOGETHER
	public Product product;
	public float priceEach;
	public int amount;
	
	public Item(Product product, float priceEach, int amount) {
		this.product = product;
		this.priceEach = priceEach;
		this.amount = amount;
	}
}
