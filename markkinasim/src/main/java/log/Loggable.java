package log;

/**
 * Abstract class to use with classes that want to be registered to Accountants log
 */

public abstract class Loggable {
	private Accountant accountant;
	public abstract void register();
	public abstract void getDay();
	
	/**
	 * singleton instance of account for the class extending Loggable superclass
	 */
	public Loggable() {
		accountant = Accountant.getInstance();
	}
}
