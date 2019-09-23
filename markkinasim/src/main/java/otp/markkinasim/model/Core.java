package otp.markkinasim.model;

import java.util.ArrayList;

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
	
	private ArrayList<IPerson> personList;
	private ArrayList<IParty> partyList;
	
	public void init() {
		personList = new ArrayList<IPerson>();
		partyList = new ArrayList<IParty>();
		
		int personAmount = 100;
		
		for(int i = 0; i < personAmount; i++) {
			personList.add(new Person());
		}
		
		//partyList.add(new Party());	//TODO LISÄÄ V1 MUKAINEN PARTY-JÄRJESTELMÄ
	}
	
	public void start() {
		
	}
}
