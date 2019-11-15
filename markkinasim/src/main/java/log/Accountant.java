package log;
import java.util.ArrayList;

public class Accountant {
	//public static final Accountant INSTANCE = new Accountant();
	
	//public Accountant() {}
	
	ArrayList<LogObject> log = new ArrayList<LogObject>();
	
	public void registerTrade(TradeLog trade) {
		log.add(trade);
	};
	
	public ArrayList<LogObject> getAllTrades() {
		return log;
	}
	
	/*
	public static Accountant getInstance() {
		return INSTANCE;
	}
	*/
	
	
	
}
