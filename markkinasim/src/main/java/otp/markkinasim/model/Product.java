package otp.markkinasim.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
	
	public static int nextId = 0;
	
	public final IntegerProperty id;
	private StringProperty productName;
	private StringProperty rawmaterialName;
	private IntegerProperty rawmaterialId;
	private FloatProperty rawmaterialNeeded; //kuinka monta raaka-ainetta yhteen tuotteeseen tarvitaan esim, 10 tai 0.1
	
	public Product() {
		id = new SimpleIntegerProperty(nextId);
		nextId++;
		
		this.productName = new SimpleStringProperty(null);
		this.rawmaterialId = new SimpleIntegerProperty(0);
		this.rawmaterialNeeded = new SimpleFloatProperty(0);
		this.rawmaterialName = new SimpleStringProperty(null);
	}
	
	public Product(String productName, int rawmaterialId, String rawmaterialName, int rawmaterialNeeded) {
		id = new SimpleIntegerProperty(nextId);
		nextId++;
		
		this.productName = new SimpleStringProperty(productName);
		this.rawmaterialId = new SimpleIntegerProperty(rawmaterialId);
		this.rawmaterialNeeded = new SimpleFloatProperty(rawmaterialNeeded);
		this.rawmaterialName = new SimpleStringProperty(rawmaterialName);
	}
	
	//setterit ja getterit propertyille
	public String getRawmaterialName() {
		return rawmaterialName.get();
	}
	
	public void setRawmaterialName(String rawmaterialName) {
		this.rawmaterialName.set(rawmaterialName);
	}
	public String getProductName() {
		return productName.get();
	}

	public void setProductName(String productName) {
		this.productName.set(productName);
	}

	public int getRawmaterialId() {
		return rawmaterialId.get();
	}
	
	public void setRawmaterialId(int rawmaterialId) {
		this.rawmaterialId.set(rawmaterialId);
	}
	
	public float getRawmaterialNeeded() {
		return rawmaterialNeeded.get();
	}
	
	public void setRawmaterialNeeded(float rawmaterialNeeded) {
		this.rawmaterialNeeded.set(rawmaterialNeeded);
	}
	public int getId() {
		return id.get();
	}
	
	//property getterit
	public StringProperty productNameProperty() {
		return productName;
	}
	public FloatProperty rawmaterialNeededProperty() {
		return rawmaterialNeeded;		
	}
	public StringProperty rawmaterialNameProperty() {
		return rawmaterialName;		
	}
}
