package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.collections.ObservableList;
import otp.markkinasim.App;
import otp.markkinasim.view.IView;
import otp.markkinasim.view.View;
import javafx.collections.FXCollections;

public class Simulator {
	/**
	 * Simulator on facade-mallin mukainen pääjehu, joka pitää langat käsissään.
	 * Tarjoaa controllerille toiminnalisuuden pyörittää ja alustaa simulaatio.
	 * @author Kalle Rissanen
	 * @version 1.0
	 */
	
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
		/**
		 * Alustaa simulaation.
		 * @param parties, lista simulaatioon menevistä tahoista
		 * @param product, lista simulaatioon menevistä tuotteista
		 * @param persons, lista simulaatioon menevistä yksilöistä
		 */
		personList = persons;
		partyList = parties;
		productList = products;
		market = new Market();
		
		for(Party p : parties) {
			p.setMarket(market);
			p.setProducedItemInventory(new Item(p.getProductToProduce(), 0, p));
			p.setNeededItemInventory(new Item(p.getProductToProduce().getProductNeeded(), 0, p));
			p.producedItemInventory.priceEach.set(10);
		}
		
		for(Person p : persons) {
			p.setMarket(market);
		}
	}

	public void nextDay() {
		/**
		 * Menee simulaatiossa yhden iteraation (päivän) eteenpäin.
		 */
		day++;

		//WIP
		
		//personeille, jotta tietää mitä ostaa
		ArrayList<Product> productCandidates = new ArrayList<>();
		
		for(Party p : partyList) {
			//p.buyProduct(partyList);
			p.produce();
			p.evaluate(day);
			
			System.out.println(p.getPartyName() + " " + (p instanceof Manufacturer));
			
			if(day % 7 == 0) {
				p.paySalaries();
			}
			
			market.cleanEmpty();
		}
		
		for(Product p : productList) {
			if(p.getProductNeededId() != -1) {
				productCandidates.add(p);
			}
		}
		
		for(Person p : personList) {
			if(p.getEmployer() == null) {
				p.findWork(partyList);
			}
			
			Random rnd = new Random();
			
			if(!productCandidates.isEmpty()) {
				Product productToBuy = productCandidates.get(rnd.nextInt(
						productCandidates.size()));
				
				p.consume(productToBuy);
			}
		}
	}
}
