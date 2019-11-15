package log;

public abstract class Loggable {
	private Accountant accountant;
	public abstract void register();
	public abstract void getDay();
	
	public Loggable() {
		accountant = Accountant.getInstance();
	}
}
