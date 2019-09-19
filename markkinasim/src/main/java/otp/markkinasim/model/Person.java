package otp.markkinasim.model;

public class Person implements IPerson {

	private double money;
	private double salary;
	private String work;
	
	public Person() {
		this.money = 1000;
		this.salary = 0;
		this.work = null;
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
