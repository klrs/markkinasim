package otp.markkinasim.simulation;

import java.util.ArrayList;

/**
 * Inventaario-luokka. Ei käytetty.
 * @author Kalle Rissanen
 * @version 0.9
 */

@Deprecated
public class Inventory {
	//itemList ei voi sisältää duplikaatteja produkteja!
	//tämä on koko inventoryn idea! Muuten olisi voinut vaan pitää ArrayListinä
	
	///////OBSOLETE ATM!!!///////
	
	private ArrayList<Item> itemList;
	
	public Inventory() {
		itemList = new ArrayList<Item>();
	}
	public void add(Item item) {
		boolean itemNotFound = true;
		if(itemList.size() != 0) {
			//Tarkistaa yritetäänkö lisätä itemListissä toista samaa Productia
			//Jos lisättä Product löytyy jo listalta, sen amounttiin lisätään.
			for(Item i : itemList) {
				if(item.product.id == i.product.id) {
					i.amount.set(i.amount.get() + item.amount.get());
					itemNotFound = false;
					break;
				}
			}
		}
		else {
			//jos listassa ei ole mitään
			itemList.add(item);
			itemNotFound = false;
		}
		
		if(itemNotFound == true) {
			//mikäli itemiä ei löytynyt
			itemList.add(item);
		}
	}
	public void add(Product product) {
		//add??
	}
	public Item search(Product productToSearch) {
		//etsii itemin product-oliolla
		Item itemToReturn = null;
		for(Item i : itemList) {
			if(i.product.id == productToSearch.id) {
				itemToReturn = i;
			}
		}
		return itemToReturn;
	}
	public Item search(int productToSearchId) {
		//etsii itemin productin id:llä
		Item itemToReturn = null;
		for(Item i : itemList) {
			if(i.product.id == productToSearchId) {
				itemToReturn = i;
			}
		}
		return itemToReturn;
	}
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	public void deleteItem(int productId) {
		//poista item
		Item itemToDelete = search(productId);
		if(itemToDelete != null) {
			itemList.remove(itemToDelete);
		}
	}
	public void deleteItem(Item itemToDelete) {
		//poista item
		if(itemToDelete != null) {
			itemList.remove(itemToDelete);
		}
	}
}
