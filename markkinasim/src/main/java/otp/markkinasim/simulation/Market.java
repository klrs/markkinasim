package otp.markkinasim.simulation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.FloatProperty;
import javafx.collections.FXCollections;
import otp.markkinasim.controller.Controller;

public class Market {
	/**
	 * Market pitää sisällään listan kaikista myytävänä olevista tuotteista 
	 * ja sisältää toiminnalisuuden ostamiseen / myymiseen.
	 * @author Kalle Rissanen
	 * @version 1.0
	 */
	
	/**
	 * Lista Item-olioista
	 */
	private List<Item> listedItems;
	
	public Market() {
		listedItems = FXCollections.observableArrayList();
	}
	public void vend(Item item) {
		/**
		 * Lisää listaan tuotteen
		 * @param item, lisättävä Item-olio
		 */
		listedItems.add(item);
	}
	public List<Item> checkItems(Product p) {
		/**
		 * Tarkistaa onko myynnissä tuotetta p.
		 * @param p, tarkistettava tuote-olio
		 * @return itemsOfP, lista kaikista myytävistä p-tyyppisistä tuotteista.
		 */
		List<Item> itemsOfP = new ArrayList<Item>();
		for(Item i : listedItems) {
			if(p.equals(i.product)) {
				itemsOfP.add(i);
			}
		}
		
		return itemsOfP;
	}
	public Item findNextCheapestItem(List<Item> items){
		/**
		 * Hakee halvimman tuotteen items-listalta.
		 * @param items, lisättävä Item-olio
		 * @return cheapestItem, halvin tuote items-listalta.
		 */
		
		//TODO
		if(!items.isEmpty()) {
			Item cheapestItem = items.get(0);
			for(Item i : items) {
				if(i.priceEach.get() < cheapestItem.priceEach.get()) {
					cheapestItem = i;
				}
			}
			return cheapestItem;
		}
		else {
			return null;
		}
		
	}
	public boolean buy(Party p, Item itemToBuy, int amount) {
		/**
		 * Ostaa tuotteen.
		 * @param p, Taho, joka ostaa.
		 * @param itemToBuy, ostettava tuote. Tämän saa esim.
		 * findNextCheapestItem-metodilta.
		 * @param amount, ostettava määrä
		 * @return true/false, onnistuiko osto
		 */
		
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
			return true;
		}
		else {
			return false;
		}
	}
	public boolean buy(Person p, Item itemToBuy, int amount) {
		/**
		 * Yksilö ostaa tuotteen.
		 * @param p, Yksilö, joka ostaa.
		 * @param itemToBuy, ostettava tuote. Tämän saa esim.
		 * findNextCheapestItem-metodilta.
		 * @param amount, ostettava määrä
		 * @return true/false, onnistuiko osto
		 */
		
		if(itemToBuy != null) {
			//kokeile sisältääkö itemToBuy tarpeeksi tavaroita
			int possibleAmount = amount;
			if(itemToBuy.amount.get() < amount) {
				possibleAmount = itemToBuy.amount.get();
			}
			
			//kokeilee onko p:llä tarpeeksi rahaa
			if(p.getMoney() >= possibleAmount * itemToBuy.priceEach.get() &&
					possibleAmount > 0) {
				float cost = possibleAmount * itemToBuy.priceEach.get();
				System.out.println(cost);
				
				p.setMoney(p.getMoney() - cost);
				itemToBuy.partyHolder.money.add(cost);
				Controller.log("SOLD", possibleAmount,
						itemToBuy.partyHolder.getPartyName(),
						itemToBuy.product.getProductName());
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	
	public int cleanEmpty() {
		/**
		 * Poistaa listedItems-listasta ne oliot, joiden amount on 0.
		 * @return removed, poistettujen alkioiden määrä.
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
