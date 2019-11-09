package otp.markkinasim.controller;

import javafx.collections.ObservableList;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;

public interface IController {
	void startSimulation(
			ObservableList<Party> parties,
			ObservableList<Product> products,
			ObservableList<Person> persons);
	void nextIteration();
	boolean addToDatabase(Object o);
	ObservableList<Party> getPartyFromDatabase();
	ObservableList<Product> getProductFromDatabase();
	boolean removeFromDatabase(Object o);
}
