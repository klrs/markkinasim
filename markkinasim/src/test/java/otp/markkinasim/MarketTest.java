package otp.markkinasim;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import otp.markkinasim.simulation.Item;
import otp.markkinasim.simulation.Market;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Product;

class MarketTest {

	Market market = new Market();
	Product testiproduct1 = new Product("Testiproduct");
	Party testiproducer1 = new Party(0, "Testiproducer1", 100f, -1);
	Party testiproducer2 = new Party(1, "Testiproducer2", 100f, -1);
	
	void fillMarket() {
		market.vend(new Item(
				testiproduct1, 12f, 3, testiproducer1));
		market.vend(new Item(
				testiproduct1, 10f, 10, testiproducer2));
	}
	
	@Test
	void testCheckItems() {
		fillMarket();
		//System.out.println(market.checkItems(testiproduct1));
		assertEquals(testiproduct1, market.checkItems(testiproduct1).get(0).product);
	}
	
	@Test
	void testFindNextCheapestItem() {
		fillMarket();
		
		List<Item> items = market.checkItems(testiproduct1);
		assertEquals(testiproducer2, market.findNextCheapestItem(items).partyHolder);
	}

}
