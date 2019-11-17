package otp.markkinasim;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Product;

public class PartyTest {

	//TODO TESTAA INVENTORY OMANA MODUULINAAN!!!!!
	
	Product productNeeded = new Product("productneeded");
	//Product productToProduce = new Product("testproduct", productNeeded.id);
	//Party party = new Party("Testparty", 100, new Product("Testproduct"));
	
//	@Ignore
//	@Test
//	public void testInventoryAdd() {
//		assertThrows(InvalidParameterException.class, () -> {
//			party.addToInventory(productNeeded, 0);
//		});
//	}
//	
//	@Ignore
//	@Test
//	public void testInventoryAdd2() {
//		assertThrows(InvalidParameterException.class, () -> {
//			party.addToInventory(productNeeded, -100);
//		});
//	}
}
