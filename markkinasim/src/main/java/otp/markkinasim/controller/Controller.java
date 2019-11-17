package otp.markkinasim.controller;
/**
*
* @author Joonas Lapinlampi, Kalle Rissanen
*/
import java.security.InvalidParameterException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;
import otp.markkinasim.simulation.Simulator;
import otp.markkinasim.view.IView;

public class Controller implements IController{
	private IView view;
	private Simulator simulator;
	private Secretary secretary;
	//private IRegister register;
	
	public Controller(IView view) {
		this.view = view;
		secretary = new Secretary();
//		//register = new
	}
	@Override
	public void startSimulation(ObservableList<Party> parties, ObservableList<Product> products,
			ObservableList<Person> persons) {
		simulator = Simulator.getInstance();
		setProductNeededs(products);
		simulator.init(parties, products, persons);
	}
	@Override
	public void nextIteration() {
		simulator.nextDay();
		
	}
	@Override
	public boolean addToDatabase(Object o) {
		boolean done = secretary.createNewObject(o);
		return done;
	}
	@Override
	public ObservableList<Party> getPartyFromDatabase() {
		return secretary.getAllPartysFromDB();
	}
	@Override
	public ObservableList<Product> getProductFromDatabase() {
		return secretary.getAllProductsFromDB();
	}
	@Override
	public boolean removeFromDatabase(Object o) {
		boolean done = secretary.removeObject(o);
		return done;
	}
	private void setProductNeededs(ObservableList<Product> productList) {
		//ILMENTÄÄ PRODUCTIEN productNeeded-FIELDIN productNeededId:N MUKAAN
		for(Product p : productList) {
			System.out.println(productList.indexOf(p));
			for(int i = 0; productList.size() > i; i++) {
				if(p.getProductNeededId() == productList.get(i).getId()) {
					p.setProductNeeded(productList.get(i));
					break;
				}
			}
		}
	}
}
