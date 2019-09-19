package otp.markkinasim.model;

public interface IPerson {
	
	//setters and getters
	public double getMoney();

	public void setMoney(double money);

	public double getSalary();
	
	public void setSalary(double salary);

	public String getWork();
	
	public void setWork(String work);

	//advanced functions
	public void findWork();
}