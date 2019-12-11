package otp.markkinasim.simulation;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import otp.markkinasim.controller.Controller;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

@Entity
@Table(name="Person")

/**
 * Person edustaa simulaatiossa yksilöä. Yksilö kuluttaa ja käy tahoilla
 * töissä.
 * @author Kalle Rissanen
 * @version 1.0
 */
public class Person {
	
	//private FloatProperty money;
	private static int count;
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="round")
	static int round; //placeholder name!
	
	@Column(name="status")
	private String status;
	
	@Column(name="money")
	private double money;
	private Party employer;
	
	@Column(name="salary")
	private double salary;
	
	@Column(name="work")
	private String work;
	
	@Transient
	private Market market;

	public Person() {
		//this.money = new SimpleFloatProperty(100);
		this.status = "idle";
		this.money = 1000;
		this.salary = 0;
		this.work = null;
		this.employer = null;
		
		this.id=count++;
	}
	
	/**
	 * Kuluttaa tuotetta, ostaen sen ensiksi.
	 * @param productToBuy, ostettava tuote
	 */
	public void consume(Product productToBuy) {
		List<Item> items = market.checkItems(productToBuy);
		Item itemToBuy = market.findNextCheapestItem(items);
		
		int amount = 1;	//TODO?
		if(market.buy(this, itemToBuy, amount)) {
			Controller.log("CONSUME", amount, (this.id + ""), productToBuy.getProductName());
		}
	}
	
	/**
	 * Etsii töitä käymällä läpi kaikki tahot.
	 * @param partyList, lista kaikista tahoista.
	 */
	public void findWork(ObservableList<Party> partyList) {
		Random rnd = new Random();
		for(Party p : partyList) {
			if(rnd.nextBoolean()) {
				if(p.employeesNeeded()) {
					employer = p;
					p.addEmployee(this);
					Controller.log("FOUND_WORK", 0, p.getPartyName(), (this.id + ""));
					break;
				}
			}
		}
	}
	public void addMoney(float addableMoney) {
		this.money = this.money + addableMoney;
	}
	
	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Party getEmployer() {
		return employer;
	}
	public void setEmployer(Party employer) {
		this.employer = employer;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}
	
	
}
