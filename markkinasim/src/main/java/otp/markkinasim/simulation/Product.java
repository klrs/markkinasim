package otp.markkinasim.simulation;

import java.util.ArrayList;
import javafx.beans.property.*;
import javax.persistence.*;

@Entity
@Table(name="Product")
@Access(AccessType.FIELD)


public class Product {
	/**
	 * Tuote-luokka. Tarjoaa tahoille ja yksilöille luokan, joka kapsuloi tuotteen
	 * ominaisuudet.
	 * @author Kalle Rissanen
	 * @version 1.0
	 */
	
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
	private Product productNeeded;

	public Product() {
		this.productName = new SimpleStringProperty();
		this.productNeededId = new SimpleIntegerProperty(-1);
	}
	public Product(String productName) {
		
		this.productName = new SimpleStringProperty(productName);
		this.productNeededId = new SimpleIntegerProperty(-1); //SAATTAA AIHEUTTAA ONGELMIA!!
	}
	
	public Product(String productName, int productNeededId) {
		
		this.productName = new SimpleStringProperty(productName);
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
	public StringProperty productNameProperty() {

		return productName;
	}
	public Product getProductNeeded() {
		return productNeeded;
	}
	public void setProductNeeded(Product productNeeded) {
		this.productNeeded = productNeeded;
	}
}
