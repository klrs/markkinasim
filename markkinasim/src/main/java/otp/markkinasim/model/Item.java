package otp.markkinasim.model;

public class Item {
	//DATA TYPE FOR WRAPPING PRODUCT AND AMOUNT TOGETHER
	public Product product;
	public int amount;
	
	public Item(Product product, int amount) {
		this.product = product;
		this.amount = amount;
	}
}
