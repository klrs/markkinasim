package otp.markkinasim;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Test;

import otp.markkinasim.simulation.Item;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Product;

class ItemTest {
	
	@Test
	void testItemCreate() {
		assertThrows(InvalidParameterException.class, () -> {
			new Item(new Product(), -1, new Party());
		});
	}
	
	@Test
	void testSubtract() {
		Item i = new Item(new Product(), 10, new Party());
		assertThrows(InvalidParameterException.class, () -> {
			i.subtractAmount(11);
		});
	}
	
	@Test
	void testSubtract2() {
		Item i = new Item(new Product(), 10, new Party());
		i.subtractAmount(5);
		assertEquals(5, i.amount.get());
	}
}
