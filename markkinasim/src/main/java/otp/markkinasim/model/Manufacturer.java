package otp.markkinasim.model;

import java.security.InvalidParameterException;

public class Manufacturer extends Party {
	//jalostaja-taho. Tuottaa producteja, mutta vaatii raaka-aineita(product) toimiakseen.
	
	public Manufacturer(String partyName, float money, Product productToProduce) {
		super(partyName, money, productToProduce);
	}
	
	@Override
	public void produce() {
		if(checkProducable(productToProduce)) {
			Item item = inventory.search(productToProduce.getProductNeededId());
			try {
				//Poistaa yhden raaka-aineen ja lisää yhden tuotettavan tuotteen inventoryyn.
				item.subtractAmount(1);
				
				if(item.amount == 0) {
					inventory.deleteItem(item);
				}
				
				inventory.add(new Item(productToProduce, 1));
			}
			catch(InvalidParameterException e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			System.out.println("Too few resources to produce!");
		}
	}
	private boolean checkProducable(Product productToProduce) {
		//Testaa onko Product tuotettavissa vertaamalla sitä inventoryssä oleviin itemeihin.
		//HUOM. TOIMII VAIN YHDELLÄ PRODUCTILLA. EI LISTOILLA. MUUTA?
		if(inventory.search(productToProduce.getProductNeededId()) != null) { return true; } else { return false; }
	}
}
