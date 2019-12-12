package otp.markkinasim;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import otp.markkinasim.controller.Secretary;
import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Producer;
import otp.markkinasim.simulation.Product;

public class SecretaryTest {
	
	private Secretary sec = new Secretary();
	
	/**
	 * Testaa meneekö lisätty Product-olio tietokannan läpi halutulla tavalla.
	 * @result Tarkistaa onko olio tietokanna kautta tulleen olion nimi sama.
	 */
	@Test
	void testAddProduct() {
		Product product = new Product("test product");
		sec.createProduct(product);
		ObservableList<Product> result = sec.getAllProductsFromDB();
		Product realproduct = result.get(0);
		assertEquals("test product",realproduct.getProductName(),"Expected name to be 'test product'.");
	}
	
	/**
	 * Luo Party-luokan aliluokan Manufacturer-olion ja vie sen tietokannan läpi.
	 * @result Tarkistaa onko olio tietokanna kautta tulleen olion nimi sama ja pystyykö olion castaamaan oikein.
	 */
	@Test
	void testAddManufacturer() {
		Party manufacturer = new Party();
		manufacturer.setPartyName("test manufacturer");
		manufacturer.setPartyType(1);
		sec.createParty(manufacturer);
		ObservableList<Party> result = sec.getAllPartysFromDB();
		Manufacturer realmanufacturer = (Manufacturer)result.get(0);
		assertEquals("test manufacturer",realmanufacturer.getPartyName(),"Expected name to be 'test manufacturer'.");
	}
	
	/**
	 * Luo Party-luokan aliluokan Producer-olion ja vie sen tietokannan läpi.
	 * @result Tarkistaa onko olio tietokanna kautta tulleen olion nimi sama ja pystyykö olion castaamaan oikein.
	 */
	@Test
	void testAddProducer() {
		Party producer = new Party();
		producer.setPartyName("test producer");
		producer.setPartyType(2);
		sec.createParty(producer);
		ObservableList<Party> result = sec.getAllPartysFromDB();
		Producer realproducer = (Producer)result.get(0);
		assertEquals("test producer",realproducer.getPartyName(),"Expected name to be 'test producer'.");
	}
	
	
	
	


}
