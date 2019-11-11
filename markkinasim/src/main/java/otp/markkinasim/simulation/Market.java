package otp.markkinasim.simulation;

import java.util.ArrayList;
import java.util.List;

public class Market {
	//Market sisältää kaikki myytävät itemit
	private List<Item> listedItems;
	
	public Market() {
		listedItems = new ArrayList<Item>();
	}
	public void vend(Item item) {
		listedItems.add(item);
	}
	public List<Item> checkItems(Product p) {
		//palauttaa listan myytävistä itemeistä, jotka ovat tyyppiä p
		List<Item> itemsOfP = new ArrayList<Item>();
		for(Item i : listedItems) {
			if(p.equals(i.product)) {
				itemsOfP.add(i);
			}
		}
		
		return itemsOfP;
	}
	public void buy(Party p, List<Item> itemsToBuy, int amount) {
		for(Item i : itemsToBuy) {
			if(listedItems.contains(i)) {
				
			}
		}
	}
}
