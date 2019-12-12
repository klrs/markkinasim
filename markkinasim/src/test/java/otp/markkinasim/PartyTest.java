package otp.markkinasim;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Producer;
import otp.markkinasim.simulation.Product;

public class PartyTest {
	
	Product p;
	Party manu;
	Party prod;
	
	public void init() {
		p = new Product();
		p.setPoductName("Test");
		manu = new Manufacturer();
		prod = new Producer();
		manu.init(new Product());
		manu.setProductToProduce(p);
		manu.getNeededItemInventory().amount.set(10);
		manu.setMoney(1000);
	}
	
	@Test
	public void testEmployeesNeeded() {
		init();
		assertEquals(true, manu.employeesNeeded());
	}
}
