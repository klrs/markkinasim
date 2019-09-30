package otp.markkinasim.model;

import javafx.beans.property.*
import javax.persistence.*;

@Entity
@Table(name="Person")

public class Person implements IPerson {
	
	private FloatProperty money;
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
		this.money = new SimpleFloatProperty(100);
		this.status = "idle";
		this.money = 1000;
		this.salary = 0;
		this.work = null;
		
		this.id=count++;
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
	public void findWork() {
		// TODO Auto-generated method stub
		
	}
	
	
}
