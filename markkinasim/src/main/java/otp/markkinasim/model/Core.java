package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	private ArrayList<Person> personList;
	private ArrayList<Party> partyList;
	private ArrayList<Product> productList;

	private int day = 0;
	
	public void init() {
		personList = new ArrayList<Person>();
		partyList = new ArrayList<Party>();
		productList = new ArrayList<Product>();
		
//		int personAmount = 100;
//		
//		for(int i = 0; i < personAmount; i++) {
//			personList.add(new Person());
//		}
		personList.add(new Person());
		
		productList.add(new Product("Cow"));
		productList.add(new Product("Beef patty", 0));	//INDEX????
		
		partyList.add(new Party("Jimbo's Beef", 1000.0f, productList.get(1)));
		System.out.println(partyList.get(0));
		
		try {
			partyList.get(0).addToInventory(productList.get(0), 10);
		} catch (InvalidParameterException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void start() {
		Scanner scan = new Scanner(System.in);
		while(true) {
			day++;
			
			partyList.get(0).produce();
			
			
			scan.nextLine();
		}
	}
}
