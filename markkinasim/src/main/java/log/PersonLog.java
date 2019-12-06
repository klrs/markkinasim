package log;


import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;

/**
 * This class makes a person into a person log object for the Accountant
 */
public class PersonLog implements LogObject {
	
	private Accountant accountant;
	private int day;
	
	private int id;
	//static int round; //placeholder name!
	private String status;
	private double money;
	private Party employer;
	private double salary;
	private String work;
	
	public PersonLog(Accountant accountant, int day, Person person) {
		this.accountant = accountant;
		this.day = day;
		this.id = person.getId();
		this.status = person.getStatus();
		this.money = person.getMoney();
		this.employer = person.getEmployer();
		this.salary = person.getSalary();
		this.work = person.getWork();
		register();
	}

	@Override
	public int getDay() {
		return this.day;
	}

	/**
	 * registers this event to accountants log list
	 */
	@Override
	public void register() {
		accountant.registerPersonLog(this);
	}
	
	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public Party getEmployer() {
		return employer;
	}

	public double getMoney() {
		return money;
	}

	public double getSalary() {
		return salary;
	}

	public String getWork() {
		return work;
	}

}
