package log;
import java.util.ArrayList;

public class Accountant {
	
	ArrayList<LogObject> completeLog = new ArrayList<LogObject>();
	ArrayList<LogObject> tradeLog = new ArrayList<LogObject>();
	
	public void registerTrade(TradeLog trade) {
		completeLog.add(trade);
		tradeLog.add(trade);
	};
	
	public void registerPersonLog(PersonLog personLog) {
		completeLog.add(personLog);
	}
	
	public ArrayList<LogObject> getCompleteLog() {
		return completeLog;
	}
	
	public ArrayList<LogObject> getAllTrades() {
		return tradeLog;
	}
	
	public ArrayList<LogObject> getPersonLog() {
		ArrayList<LogObject> personsLog = new ArrayList<LogObject>();
		for (LogObject object : completeLog) {
			if (object instanceof PersonLog) {
				personsLog.add(object);
			}
		}
		return personsLog;
	}
	
	public ArrayList<LogObject> getTradesForDay(int day) {
		ArrayList<LogObject> logOneDay = new ArrayList<LogObject>();
		for (LogObject object : tradeLog) {
			if (object.getDay() == day) {
				logOneDay.add(object);
			}
		}
		return logOneDay;
	}
	
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
