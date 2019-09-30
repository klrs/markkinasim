package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Core {
	//SINGLETON PATTERN//////////////////
	//mahdollistaa vain yhden Core-olion olemassaolon!
	private static Core core;
	private Core() {}
	public static Core getInstance() {
		//kutsu tätä funktiota luodaksesi Core olion!
		if (core == null) {
			core = new Core();
		}
		return core;
	}
	////////////////////////////////////
	
	private ObservableList<Person> personList;
	private ObservableList<Party> partyList;
	private ObservableList<Product> productList;

	private int day = 0;
	
	public void init() {
		personList = FXCollections.observableArrayList();
		partyList = FXCollections.observableArrayList();
		productList = FXCollections.observableArrayList();
		
//		int personAmount = 100;
//		
//		for(int i = 0; i < personAmount; i++) {
//			personList.add(new Person());
//		}
		personList.add(new Person());
		
		productList.add(new Product("Cow"));
		productList.add(new Product("Beef patty", 0));	//INDEX????
		
		partyList.add(new Manufacturer("Jimbo's Beef", 1000.0f, productList.get(1)));
		partyList.add(new Party("Cowman", 100, productList.get(0)));
		
		try {
			partyList.get(0).addToInventory(productList.get(0), 3);
		} catch (InvalidParameterException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void start() {
		Scanner scan = new Scanner(System.in);
		while(true) {
			day++;
			System.out.println("Day: " + day);

			for(Party p : partyList) {
				p.produce();
				for(Item i : p.searchInventory()) {
					System.out.println(p.getPartyName() + " inventory: " + i.product.getProductName() + " " + i.amount);
				}
				scan.nextLine();
			}
			//partyList.get(0).produce();

			//System.out.println(partyList.get(0).searchInventoryItem(1).amount);
			
		}
	}
}
