package log;
import java.util.ArrayList;

/**
 * Accountant keeps log of simulation events incl. trade history and person history
 * Accountant is done singleton pattern
 * 
 */
public class Accountant {
	
	
	private static final Accountant INSTANCE = new Accountant();
	
	private Accountant() {}
	
	ArrayList<LogObject> completeLog = new ArrayList<LogObject>();
	ArrayList<LogObject> tradeLog = new ArrayList<LogObject>();
	
	/**
	 * Get singleton instance
	 */
	public static Accountant getInstance() {
		return INSTANCE;
	}
	
	/**
	 * register new trade
	 */
	public void registerTrade(TradeLog trade) {
		completeLog.add(trade);
		tradeLog.add(trade);
	};
	
	/**
	 * register new person
	 */
	public void registerPersonLog(PersonLog personLog) {
		completeLog.add(personLog);
	}
	
	/**
	 * return log of all registered events
	 */
	public ArrayList<LogObject> getCompleteLog() {
		return completeLog;
	}
	
	/**
	 * returns log of all trade events
	 */
	public ArrayList<LogObject> getAllTrades() {
		return tradeLog;
	}
	
	/**
	 * returns log of all person events
	 */
	public ArrayList<LogObject> getPersonLog() {
		ArrayList<LogObject> personsLog = new ArrayList<LogObject>();
		for (LogObject object : completeLog) {
			if (object instanceof PersonLog) {
				personsLog.add(object);
			}
		}
		return personsLog;
	}
	
	/**
	 * returns all trade logs for desired day
	 */
	public ArrayList<LogObject> getTradesForDay(int day) {
		ArrayList<LogObject> logOneDay = new ArrayList<LogObject>();
		for (LogObject object : tradeLog) {
			if (object.getDay() == day) {
				logOneDay.add(object);
			}
		}
		return logOneDay;
	}
	
	/**
	 * returns all logs for desired day
	 */
	public ArrayList<LogObject> getCompleteLogForDay(int day) {
		ArrayList<LogObject> logOneDay = new ArrayList<LogObject>();
		for (LogObject object : completeLog) {
			if (object.getDay() == day) {
				logOneDay.add(object);
			}
		}
		return logOneDay;
	}
	
	
}
