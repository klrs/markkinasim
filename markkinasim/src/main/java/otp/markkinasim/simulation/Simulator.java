package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.ObservableList;
import otp.markkinasim.App;
import otp.markkinasim.view.IView;
import otp.markkinasim.view.View;
import javafx.collections.FXCollections;

public class Simulator {
	//SINGLETON PATTERN//////////////////
	//mahdollistaa vain yhden Simulator-olion olemassaolon!
	private static Simulator simulator;
	private Simulator() {}
	public static Simulator getInstance() {
		//kutsu tätä funktiota luodaksesi Core olion!
		if (simulator == null) {
			simulator = new Simulator();
		}
		return simulator;
	}
	////////////////////////////////////

	private ObservableList<Person> personList;
	private ObservableList<Party> partyList;
	private ObservableList<Product> productList;
	private Market market;

	private int day = 0; 	//ITERATION COUNT

	public void init(ObservableList<Party> parties, ObservableList<Product> products,
			ObservableList<Person> persons) {
		personList = persons;
		partyList = parties;
		productList = products;
		market = new Market();
	}

	public void nextDay() {
		day++;

		//WIP
		for(Party p : partyList) {
			//p.buyProduct(partyList);
			p.produce();
			p.evaluate();
			
			if(day % 7 == 0) {
				p.paySalaries();
			}
			
			
			market.cleanEmpty();
		}
		
		
	}
}
