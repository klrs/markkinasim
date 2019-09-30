package otp.markkinasim.modeltest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;

class ProductTest {

	@Test
	void testProductId() {
		ArrayList<Product> products = new ArrayList<Product>();
		for(int i = 0; i < 100; i++) {
			products.add(Product.nextId, new Product("Product" + i));
		}
		
		for(int i = 0; products.size() > i; i++) {
			assertEquals(i, products.get(i).id, "Id doesn't match");
		}
	}
	
	@Test
	void testProductId2() {
		ArrayList<Product> products = new ArrayList<Product>();
		for(int i = 0; i < 100; i++) {
			products.add(new Product("Product" + i));
		}
		
		System.out.println(products.get(0).id + "   " + products.size());
		
		for(int i = 0; products.size() > i; i++) {
			assertEquals(i, products.get(i).id, "Id doesn't match");
		}
	}
}