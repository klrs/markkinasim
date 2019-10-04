package otp.markkinasim.model;

import java.util.ArrayList;

public class Inventory {
	//itemList ei voi sis�lt�� duplikaatteja produkteja!
	//t�m� on koko inventoryn idea! Muuten olisi voinut vaan pit�� ArrayListin�
	private ArrayList<Item> itemList;
	
	public Inventory() {
		itemList = new ArrayList<Item>();
	}
	public void add(Item item) {
		boolean itemNotFound = true;
		if(itemList.size() != 0) {
			
			//Tarkistaa yritet��nk� lis�t� itemListiss� toista samaa Productia
			//Jos lis�tt� Product l�ytyy jo listalta, sen amounttiin lis�t��n.
			for(Item i : itemList) {
				if(item.product.id == i.product.id) {
					i.amount.add(item.amount);
					itemNotFound = false;
					break;
				}
			}
		}
		else {
			//jos listassa ei ole mit��n
			itemList.add(item);
			itemNotFound = false;
		}
		
		if(itemNotFound == true) {
			//mik�li itemi� ei l�ytynyt
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
		//etsii itemin productin id:ll�
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