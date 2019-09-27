package otp.markkinasim.model;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<Item> itemList;
	
	public Inventory() {
		itemList = new ArrayList<Item>();
	}
	public void add(Item item) {
		for(Item i : itemList) {
			if(item.product.id == i.product.id) {
				i.amount++;
			}
			else {
				itemList.add(item);
			}
		}
	}
	public void add(Product product) {
		//add??
	}
	public Item search(Product productToSearch) {
		Item itemToReturn = null;
		for(Item i : itemList) {
			if(i.product.id == productToSearch.id) {
				itemToReturn = i;
			}
		}
		return itemToReturn;
	}
	public Item search(int productToSearchId) {
		Item itemToReturn = null;
		for(Item i : itemList) {
			if(i.product.id == productToSearchId) {
				itemToReturn = i;
			}
		}
		return itemToReturn;
	}
}
