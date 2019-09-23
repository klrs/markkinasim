package otp.markkinasim.model;

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
	private int day = 0;
	
	public void init() {
		personList = new ArrayList<Person>();
		partyList = new ArrayList<Party>();
		
		int personAmount = 100;
		
		for(int i = 0; i < personAmount; i++) {
			personList.add(new Person());
		}
		
		partyList.add(new Party());
		
		//partyList.add(new Party());	//TODO LISÄÄ V1 MUKAINEN PARTY-JÄRJESTELMÄ
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
