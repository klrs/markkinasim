package otp.markkinasim.simulation;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name="Person")

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
	
	public Person() {
		//this.money = new SimpleFloatProperty(100);
		this.status = "idle";
		this.money = 1000;
		this.salary = 0;
		this.work = null;
		this.employer = null;
		
		this.id=count++;
	}
	
	public String consume(ObservableList<Party> partyList) {
		String print = null;
		ArrayList<Item> buyables = searchConsumables(partyList);
		for(Item i : buyables) {
			if(i.amount.get() > 0) {
				i.partyHolder.addMoney(i.priceEach.get());
				money =- i.priceEach.get();
				try {
					i.subtractAmount(1);
					print = "Person " + id + " bought " + i.product.getProductName()+"\n";
				} catch (InvalidParameterException e) {
					// TODO Auto-generated catch block
					print = "Nothing to buy!";
				}
				break;
			}
			else {
				print += "Person " + id + " would like to buy " + i.product.getProductName()+"\n";
			}
		}
		return print;
	}
	public ArrayList<Item> searchConsumables(ObservableList<Party> partyList) {
		//TODO CHANGE CHANGE CHANGE THIS IS PAINFUL
		ArrayList<Item> buyables = new ArrayList<Item>();
		for(Party p : partyList) {
			buyables.addAll(p.searchSellables());
		}
		
		return buyables;
	}
	
	public String findWork(ObservableList<Party> partyList) {
		String print = null;
		for(Party p : partyList) {
			if(p.employeesNeeded()) {
				employer = p;
				p.addEmployee(this);
				print = "Person " + id + " now working for " + employer.getPartyName()+"\n";
				break;
			}
		}
		return print;
	}
	public void addMoney(float addableMoney) {
		this.money =+ addableMoney;
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
	
	
}
