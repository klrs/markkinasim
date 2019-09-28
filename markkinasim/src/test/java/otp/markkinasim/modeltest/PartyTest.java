package otp.markkinasim.modeltest;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;

class PartyTest {

	//TODO TESTAA INVENTORY OMANA MODUULINAAN!!!!!
	
	Product productNeeded = new Product("productneeded");
	Product productToProduce = new Product("testproduct", productNeeded.id);
	Party party = new Party("Testparty", 100, new Product("Testproduct"));
	
	@Test
	public void testInventoryAdd() {
		assertThrows(InvalidParameterException.class, () -> {
			party.addToInventory(productNeeded, 0);
		});
	}
	
	@Test
	public void testInventoryAdd2() {
		assertThrows(InvalidParameterException.class, () -> {
			party.addToInventory(productNeeded, -100);
		});
	}
	
	@Test
	public void testInventoryAddSuccess() {
		assertEquals(InvalidParameterException.class, () -> {
			party.addToInventory(productNeeded, -100);
		});
	}
}
