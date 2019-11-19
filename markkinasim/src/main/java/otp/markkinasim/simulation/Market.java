package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.FloatProperty;
import javafx.collections.FXCollections;

public class Market {
	//Market sisältää kaikki myytävät itemit
	private List<Item> listedItems;
	
	public Market() {
		listedItems = FXCollections.observableArrayList();
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
	public Item findNextCheapestItem(List<Item> items){
		//TODO
		Item cheapestItem = items.get(0);
		for(Item i : items) {
			if(i.priceEach.get() < cheapestItem.priceEach.get()) {
				cheapestItem = i;
			}
		}
		
		return cheapestItem;
	}
	public void buy(Party p, Item itemToBuy, int amount) {
		
		//kokeile sisältääkö itemToBuy tarpeeksi tavaroita
		int possibleAmount = amount;
		if(itemToBuy.amount.get() < amount) {
			possibleAmount = itemToBuy.amount.get();
		}
		
		//kokeilee onko p:llä tarpeeksi rahaa
		if(p.money.get() >= possibleAmount * itemToBuy.priceEach.get()) {
			float cost = possibleAmount * itemToBuy.priceEach.get();
			
			p.money.add(-cost);
			itemToBuy.partyHolder.money.add(cost);
			p.neededItemInventory.addAmount(possibleAmount);
		}
	}
	public int cleanEmpty() {
		/**
		 * Poistaa listedItems-listasta ne oliot, joiden amount on 0.
		 */
		int removed = 0;
		for(int i = 0; i < listedItems.size(); i++) {
			if(listedItems.get(i).amount.get() <= 0) {
				listedItems.remove(i);
				removed++;
			}
		}
		return removed;
	}
	
//	public void buy(Party p, List<Item> itemsToBuy, int amount) {
//		int leftAmount = amount;
//		for(Item i : itemsToBuy) {
//			int currentAmount;
//			if(i.amount.get() < leftAmount) {
//				currentAmount = i.amount.get();
//			} else {
//				currentAmount = leftAmount;
//			}
//			
//			float totalPrice = i.priceEach.get() * currentAmount;
//			if(listedItems.contains(i)) {
//				if(p.money.get() >= currentAmount * i.priceEach.get()) {
//					try {
//						i.subtractAmount(currentAmount);
//					} catch (InvalidParameterException e) {
//						//TODO!
//					}
//					p.getNeededItemInventory().addAmount(currentAmount);
//					p.addMoney(-totalPrice);
//					i.partyHolder.addMoney(totalPrice);
//				}
//			}
//			leftAmount =- currentAmount;
//		}
//	}
}