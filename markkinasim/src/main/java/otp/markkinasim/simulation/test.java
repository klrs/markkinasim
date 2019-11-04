package otp.markkinasim.simulation;

import javafx.collections.ObservableList;
import otp.markkinasim.controller.Secretary;

public class test {
	
	public static void main(String[] args) {
		Product product = new Product("PAskaa");
		
		Party party = new Party("kakka paikka", 5069, product);
		
		Secretary sec = new Secretary();
		
		boolean successpro = sec.createProduct(product);
		boolean successpar = sec.createParty(party);
		
		
	
		
		ObservableList<Party> lista1 = sec.getAllPartysFromDB();
		
		System.out.println(lista1.isEmpty());
		
		
	}

}
