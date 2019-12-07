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
 *	Ohjelman käynnistys luokka.
 *	
 *	@author Joonas Lapinlampi
 */
public class App 
{
    public static void main( String[] args )
    {
       	 Application.launch(View.class, args);
    }
}
