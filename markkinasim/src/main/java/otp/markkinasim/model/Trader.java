package otp.markkinasim.model;

import java.util.ArrayList;
import java.util.Map;

public abstract class Trader {
	private Secretary secretary;
	
	protected Map<Product, Integer> inventory;
	protected ArrayList<Item> sellables;
	
	public Trader() {
		secretary = new Secretary();
	}
	public abstract void sell();
	public abstract void buy();
	//public abstract void evaluate();
}