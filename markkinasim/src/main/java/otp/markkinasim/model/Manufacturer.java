package otp.markkinasim.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class Manufacturer extends Party {
	//jalostaja-taho. Tuottaa producteja, mutta vaatii raaka-aineita(product) toimiakseen.
	
	public Manufacturer(String partyName, float money, Product productToProduce) {
		super(partyName, money, productToProduce);
	}
	
	@Override
	public void produce() {
		if(employees.size() > 0) {
			if(checkProducable(productToProduce)) {
				Item item = inventory.search(productToProduce.getProductNeededId());
				try {
					//Poistaa yhden raaka-aineen ja lisää yhden tuotettavan tuotteen inventoryyn.
					item.subtractAmount(employees.size());
					
					if(item.amount.get() == 0) {
						inventory.deleteItem(item);
					}
					inventory.add(new Item(productToProduce, employees.size(), this));
				}
				catch(InvalidParameterException e) {
					System.out.println(e.getMessage());
				}
			}
			else {
				System.out.println("Too few resources to produce!");
			}
		}
		else {
			System.out.println("Company is shit");
		}
	}
	private boolean checkProducable(Product productToProduce) {
		//Testaa onko Product tuotettavissa vertaamalla sitä inventoryssä oleviin itemeihin.
		//HUOM. TOIMII VAIN YHDELLÄ PRODUCTILLA. EI LISTOILLA. MUUTA?
		if(inventory.search(productToProduce.getProductNeededId()) != null) { return true; } else { return false; }
	}
	@Override
	public void buyProduct(ObservableList<Party> partyList) {
		ArrayList<Item> buyables = new ArrayList<Item>();
		if(inventory.search(productToProduce.getProductNeededId()) != null &&
				inventory.search(productToProduce.getProductNeededId()).amount.get() > 0) {
			if(employees.size() > inventory.search(productToProduce.getProductNeededId()).amount.get()) {
				for(Party p : partyList) {
					buyables.addAll(p.searchSellables());
				}
				for(Item i : buyables) {
					if(i.product.id == productToProduce.getProductNeededId()) {
						i.subtractAmount(1);
						inventory.add(new Item(i.product, 1, this));
						money.set(money.get() - i.priceEach.get());
						i.partyHolder.addMoney(i.priceEach.get());
						break;
					}
				}
			}
		}
	}
}
