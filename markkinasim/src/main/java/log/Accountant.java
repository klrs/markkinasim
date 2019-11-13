package log;
import java.util.ArrayList;

public class Accountant {
	//public static final Accountant INSTANCE = new Accountant();
	
	//public Accountant() {}
	
	ArrayList<HistoryObject> log = new ArrayList<HistoryObject>();
	
	public void registerTrade(TradeObject trade) {
		log.add(trade);
	};
	
	public ArrayList<HistoryObject> getAllTrades() {
		return log;
	}
	
	/*
	public static Accountant getInstance() {
		return INSTANCE;
	}
	*/
	
	
	
}
