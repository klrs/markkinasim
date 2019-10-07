package otp.markkinasim.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

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
		
		this.id=count++;
	}
	public void consume(ObservableList<Party> partyList) {
		searchConsumables(partyList);
	}
	public ArrayList<Item> searchConsumables(ObservableList<Party> partyList) {
		//TODO CHANGE CHANGE CHANGE THIS IS PAINFUL
		ArrayList<Item> buyables = new ArrayList<Item>();
		for(Party p : partyList) {
			p.searchSellables();
		}
		
		return buyables;
	}
}
