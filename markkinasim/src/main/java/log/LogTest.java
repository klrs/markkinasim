package log;
import otp.markkinasim.simulation.*;
import java.util.ArrayList;

public class LogTest {
	
	
	public static void main(String[] args) {
		Accountant accountant = new Accountant();
		TradeLog test = new TradeLog(accountant, 1, new Party(), new Party(), new Product(), 2, 3);
		ArrayList<LogObject> log = accountant.getAllTrades();
		for (LogObject object: log) {
		    System.out.println(object.getDay());
		}
	}
}
