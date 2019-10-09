package otp.markkinasim;

import java.security.InvalidParameterException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import otp.markkinasim.model.Core;
import otp.markkinasim.model.Manufacturer;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Person;
import otp.markkinasim.model.Product;
import otp.markkinasim.model.Secretary;
import otp.markkinasim.view.View;

/**
 * Hello world!
 *
 */
public class App 
{
	private static App app;
	private static ObservableList<Person> personList;
	private static ObservableList<Party> partyList;
	private static ObservableList<Product> productList;
    public static void main( String[] args )
    {
    	   	
    	personList = FXCollections.observableArrayList();
    	partyList = FXCollections.observableArrayList();
    	productList = FXCollections.observableArrayList();
		
    	//TULEVAISUUDEN RATKAISU
    	//Secretary secretary = new Secretary();
    	//partyList = secretary.getAllPartysFromDB();
    	//productList = secretary.getAllProductsFromDB();
    	//personList = secretary.getAllPersonsFromDB();
    	
		personList.add(new Person());
		personList.add(new Person());
		personList.add(new Person());
		
		productList.add(new Product("Cow"));
		productList.get(0).setId(0);
		productList.add(new Product("Beef patty", 0));	//INDEX????
		productList.get(1).setId(1);

		partyList.add(new Manufacturer("Jimbo's Beef", 1000.0f, productList.get(1)));
		partyList.get(0).setId(0);
		partyList.add(new Party("Cowman", 100, productList.get(0)));
		partyList.get(1).setId(1);
		
		try {
			partyList.get(0).addToInventory(productList.get(0), 10);
			partyList.get(1).addToInventory(productList.get(0), 100);
		} catch (InvalidParameterException e) {
			System.out.println(e.getMessage());
		}

    	 Application.launch(View.class, args);
    }
    
	public static App getInstance() {
		//kutsu tätä funktiota luodaksesi Core olion!
		if (app == null) {
			app = new App();
		}
		return app;
	}
	
	public ObservableList<Party> getPartyList(){
		return partyList;
	}
	
	public ObservableList<Product> getProductList(){
		return productList;
	}
	
	public ObservableList<Person> getPersonList(){
		return personList;
	}
}
