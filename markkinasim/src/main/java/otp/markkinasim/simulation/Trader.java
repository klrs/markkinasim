package otp.markkinasim.simulation;

import java.util.ArrayList;
import java.util.Map;

import otp.markkinasim.controller.Secretary;

public abstract class Trader {
	private Secretary secretary;
	
	protected ArrayList<Item> inventory;
	protected ArrayList<Item> sellables;
	
	public Trader() {
		secretary = new Secretary();
	}
	public abstract void sell();
	public abstract void buy();
	//public abstract void evaluate();
}