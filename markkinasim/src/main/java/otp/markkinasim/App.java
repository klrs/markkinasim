package otp.markkinasim;

import java.security.InvalidParameterException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import otp.markkinasim.controller.Secretary;
import otp.markkinasim.simulation.Simulator;
import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;
import otp.markkinasim.view.View;

/**
 * Hello world!
 *
 */
public class App 
{
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
    	

    	 Application.launch(View.class, args);
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
