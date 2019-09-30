package otp.markkinasim.model;

import javafx.beans.property.*;

public class Person {

	private FloatProperty money;
	
	public Person() {
		this.money = new SimpleFloatProperty(100);
	}
}
