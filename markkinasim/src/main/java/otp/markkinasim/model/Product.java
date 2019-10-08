package otp.markkinasim.model;

import java.util.ArrayList;
import javafx.beans.property.*;
import javax.persistence.*;

@Entity
@Table(name="Product")
@Access(AccessType.FIELD)


public class Product {
	public static int nextId = 0;	//ID JÄRJESTELMÄ AINA OLIOTA ILMENNETTÄESSÄ. KTS. CONSTRUCTOR.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", updatable = false, nullable = false)
	protected int id;
	@Transient
	private StringProperty productName;
	@Transient
	private IntegerProperty productNeededId;
	@Transient
	private IntegerProperty price;
	
	

	public Product() {
		this.productName = new SimpleStringProperty();
		this.productNeededId = new SimpleIntegerProperty();
	}
	public Product(String productName) {
		
		this.productName = new SimpleStringProperty(productName);
		this.productNeededId = new SimpleIntegerProperty(-1); //SAATTAA AIHEUTTAA ONGELMIA!!
	}
	public Product(String productName, ArrayList<Product> productsNeeded) {
		
		this.productName = new SimpleStringProperty(productName);
		//this.productNeeded = productsNeeded;
	}
	public Product(String productName, int productNeededId) {
		
		this.productName = new SimpleStringProperty(productName);
		//productNeeded = new ArrayList<Product>();
		this.productNeededId = new SimpleIntegerProperty(productNeededId);
	}
	
	public void setPoductName(String value ) {
		productName.set(value);
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name="product_needed_id")
	public int getProductNeededId() {
		return productNeededId.get();
	}
	@Access(AccessType.PROPERTY)
	@Column(name="product_name")
	public String getProductName() {
		return productName.get();
	}
	public IntegerProperty getPrice() {
		return price;
	}
	public void setPrice(IntegerProperty price) {
		this.price = price;
	}
	public void setProductName(String productName) {
		this.productName.set(productName);
	}
	public void setProductNeededId(int productNeededId) {
		this.productNeededId.set(productNeededId);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

	

	
	
	
}
