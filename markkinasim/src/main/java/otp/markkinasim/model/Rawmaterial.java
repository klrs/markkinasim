package otp.markkinasim.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rawmaterial {
	public static int nextRawmaterialId = 0;
	public final IntegerProperty id;
	
	private StringProperty rawmaterialName;
	private StringProperty rawmaterialSource;
	private FloatProperty rawmaterialSourcePool;
	
	public Rawmaterial() {
		id = new SimpleIntegerProperty(nextRawmaterialId);
		nextRawmaterialId++;
		
		this.rawmaterialName = new SimpleStringProperty(null);
		this.rawmaterialSource = new SimpleStringProperty(null);
		this.rawmaterialSourcePool = new SimpleFloatProperty(0);
	}
	public Rawmaterial(String rawmaterialName, String rawmaterialSource, float rawmaterialSourcePool) {
		id = new SimpleIntegerProperty(nextRawmaterialId);
		nextRawmaterialId++;
		
		this.rawmaterialName = new SimpleStringProperty(rawmaterialName);
		this.rawmaterialSource = new SimpleStringProperty(rawmaterialSource);
		this.rawmaterialSourcePool = new SimpleFloatProperty(rawmaterialSourcePool);
	}
	
	public FloatProperty rawmaterialSourcePoolProperty() {
		return rawmaterialSourcePool;
	}
	
	public void setRawmaterialSourcePool(float pool) {
		rawmaterialSourcePool.set(pool);
	}
	
	public float getrawmaterialSourcePool() {
		return rawmaterialSourcePool.get();
	}
	public IntegerProperty idProperty() {
		return id;
	}
	

	public int getId() {
		return id.get();
	}

	public StringProperty rawmaterialNameProperty() {
		return rawmaterialName;
	}
	

	public String getRawmaterialName() {
		return rawmaterialName.get();
	}
	

	public void setRawmaterialName(String rawmaterialName) {
		this.rawmaterialName.set(rawmaterialName);
	}
	
	public StringProperty rawmaterialSourceProperty() {
		return rawmaterialSource;
	}
	

	public String getRawmaterialSource() {
		return rawmaterialSource.get();
	}
	

	public void setRawmaterialSource(String rawmaterialSource) {
		this.rawmaterialSource.set(rawmaterialSource);
	}
	
}
