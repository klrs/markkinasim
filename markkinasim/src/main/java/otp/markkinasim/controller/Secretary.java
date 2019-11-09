package otp.markkinasim.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Product;



public class Secretary {
	
	SessionFactory factory = null;
	final StandardServiceRegistry registry = null;
	
	private ObservableList<Product> productData;
	private ObservableList<Party> partyData;
	
	public Secretary() {
		
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		productData = FXCollections.observableArrayList();
		partyData = FXCollections.observableArrayList();
		try{
			factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

		}
		catch(Exception e){
			System.out.println("Creating sessionfactory failed. Exiting program.");
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public int getNextIdProduct() {
		
	//TODODODODO
		return 0;
		
	}
	
	public boolean createProduct(Product product) {
		boolean done = false;
		Transaction transaktio = null;
		try (Session istunto = factory.openSession()) {
			transaktio = istunto.beginTransaction();
			Product result = istunto.get(Product.class, product.getId());
			if (result == null) {
				istunto.saveOrUpdate(product);
				transaktio.commit();
				done = true;
			}
		} catch (Exception e) {
			if (transaktio != null) transaktio.rollback();
			throw e;
		}
		return done;
	}
	
	public boolean createParty(Party party) {
		boolean done = false;
		Transaction transaktio = null;
		try (Session istunto = factory.openSession()) {
			transaktio = istunto.beginTransaction();
			Party result = istunto.get(Party.class, party.getId());
			if (result == null) {
				istunto.saveOrUpdate(party);
				transaktio.commit();
				done = true;
			}
		} catch (Exception e) {
			if (transaktio != null) transaktio.rollback();
			throw e;
		}
		return done;
	}
	
	@SuppressWarnings("unchecked")
	public ObservableList<Product> getAllProductsFromDB() {
		Transaction transaktio = null;
		List<Product> result = new ArrayList<Product>();
				
		try (Session istunto = factory.openSession()) {
			transaktio = istunto.beginTransaction();
			result = istunto.createQuery("from Product").getResultList();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null) transaktio.rollback();
			throw e;
		}
		
		productData.clear();
		productData.addAll(result);
		
		return productData;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public ObservableList<Party> getAllPartysFromDB() {
		Transaction transaktio = null;
		List<Party> result = new ArrayList<Party>();
				
		try (Session istunto = factory.openSession()) {
			transaktio = istunto.beginTransaction();
			result = istunto.createQuery("from Party").getResultList();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null) transaktio.rollback();
			throw e;
		}
		partyData.clear();
		partyData.addAll(result);
		updatePartyProductToProduce();
		return partyData;
	}
	
	private void updatePartyProductToProduce() {
		for(Party p:partyData) {
			if(p.getProductToProduce()==null) {
				for(Product P:productData) {
					if(p.getProductToProduceId()==P.getId()) {
						p.setProductToProduce(P);
					}
				}
			}
		}
		
	}

	public boolean createNewObject(Object o) {
		boolean done = false;
		if(o instanceof Party) {
			done = createParty((Party) o);
		}else if(o instanceof Product) {
			done = createProduct((Product) o);
		}
		return done;
	}
	
	public boolean removeProduct(Product product) {
		boolean done = false;
		Transaction transaktio = null;
		try (Session istunto = factory.openSession()) {
			transaktio = istunto.beginTransaction();
			Product result = istunto.get(Product.class, product.getId());
			if (result != null) {
				istunto.delete(result);
				transaktio.commit();
				done = true;
			}
		} catch (Exception e) {
			if (transaktio == null) transaktio.rollback();
			throw e;
		}
		return done;
	}
	
	public boolean removeParty(Party party) {
		boolean done = false;
		Transaction transaktio = null;
		try (Session istunto = factory.openSession()) {
			transaktio = istunto.beginTransaction();
			Party result = istunto.get(Party.class, party.getId());
			if (result != null) {
				istunto.delete(result);
				transaktio.commit();
				done = true;
			}
		} catch (Exception e) {
			if (transaktio == null) transaktio.rollback();
			throw e;
		}
		return done;
	}
	
	public boolean removeObject(Object o) {
		boolean done = false;
		if(o instanceof Party) {
			done = removeParty((Party) o);
		}else if(o instanceof Product) {
			done = removeProduct((Product) o);
		}
		return done;
	}

}
