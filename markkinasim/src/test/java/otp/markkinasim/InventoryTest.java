package otp.markkinasim;

import static org.junit.jupiter.api.Assertions.*;
import otp.markkinasim.model.*;
import org.junit.jupiter.api.Test;

class InventoryTest {

	private Product product = new Product("product");
	private Product product2 = new Product("product2");
	private Item item = new Item(product, 10);
	private Item item2 = new Item(product, 5);
	private Item item3 = new Item(product2, 100);
	private Inventory inventory = new Inventory();
	
	@Test
	void testInventoryAdd1() {
		inventory.add(item);
		assertEquals(item, inventory.search(product.id), "Adding didn't work!");
	}
	
	@Test
	void testInventoryAdd2() {
		inventory.add(item);
		inventory.add(item2);
		assertEquals(15, inventory.search(product.id).amount, "Adding didn't work!");
	}
	
	@Test
	void testInventoryAdd3() {
		inventory.add(item);
		inventory.add(item2);
		inventory.add(item3);
		assertEquals(item3, inventory.search(product2.id), "Adding didn't work!");
	}
}
