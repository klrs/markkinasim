package otp.markkinasim.model;

import java.util.ArrayList;
import javafx.beans.property.*;

public class Product {
	public static int nextId = 0;	//ID JÄRJESTELMÄ AINA OLIOTA ILMENNETTÄESSÄ. KTS. CONSTRUCTOR.
	public final int id;
	
	private StringProperty productName;
	private IntegerProperty productNeededId;
	private IntegerProperty price;

	public Product(String productName) {
		id = nextId;
		nextId++;
		
		this.productName = new SimpleStringProperty(productName);
		this.productNeededId = new SimpleIntegerProperty(-1); //SAATTAA AIHEUTTAA ONGELMIA!!
	}
	public Product(String productName, ArrayList<Product> productsNeeded) {
		id = nextId;
		nextId++;
		
		this.productName = new SimpleStringProperty(productName);
		//this.productNeeded = productsNeeded;
	}
	public Product(String productName, int productNeededId) {
		id = nextId;
		nextId++;
		
		this.productName = new SimpleStringProperty(productName);
		//productNeeded = new ArrayList<Product>();
		this.productNeededId = new SimpleIntegerProperty(productNeededId);
	}
	public int getProductNeededId() {
		return productNeededId.get();
	}
	public String getProductName() {
		return productName.get();
	}
}
