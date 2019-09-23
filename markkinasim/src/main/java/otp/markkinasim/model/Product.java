package otp.markkinasim.model;

public class Product {
	private String productName;
	private int category; //ENUM???? 0 = ei kuluttjaille, 1 = kuluttajille... MORE TO COME
	private int price;

	public Product(String productName, int category, int price) {}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
