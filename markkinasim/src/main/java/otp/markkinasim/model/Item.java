package otp.markkinasim.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
	
	public static int nextItemId = 0;
	
	public final IntegerProperty id;
	private StringProperty itemName;
	private IntegerProperty productId;
	private IntegerProperty rawmaterialId;
	
	public Item(String itemName, int productId, int rawmaterialId) {
		id = new SimpleIntegerProperty(nextItemId);
		nextItemId++;
		
		this.itemName = new SimpleStringProperty(itemName);
		this.rawmaterialId = new SimpleIntegerProperty(rawmaterialId);
		this.productId = new SimpleIntegerProperty(productId);
	}
}
