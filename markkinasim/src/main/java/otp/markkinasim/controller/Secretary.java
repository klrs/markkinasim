package otp.markkinasim.controller;
/**
*
* @author Joonas Lapinlampi, Ville Järviranta
*/
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
import otp.markkinasim.simulation.Item;
import otp.markkinasim.simulation.Manufacturer;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Producer;
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
	
	/**
	 * Vie tietokantaan yhden Product-olion tiedot.
	 * 
	 * @param  product  tietokantaan vietävä Product-olio 
	 * @return palauttaa True, jos tiedon vieminen tietokantaan onnistui. False, jos epäonnistui.
	 */
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
	/**
	 * Vie tietokantaan yhden Party-olion tiedot.
	 * @param  party  tietokantaan vietävä Party-olio
 	 * @return palauttaa True, jos tiedon vieminen tietokantaan onnistui. False, jos epäonnistui.
	 */
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
	/**
	 * Tuo kaikki Product-oliot ObservableList-muodossa käyttöliittymää varten.
	 * @return palauttaa ObservableList-olion
	 */
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
	/**
	 * Tuo kaikki Party-oliot ObservableList-muodossa käyttöliittymää varten. Muuttaa Party-oliot tietokannasta joko Manufacturer- tai Producer-olioksi.
	 * @return palauttaa ObservableList-olion
	 */
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
		
		List<Party> templist = new ArrayList<Party>();
		for (Party p : partyData) {
			if (p.getPartyType() == 1) {
				Manufacturer manu = new Manufacturer(p);
				templist.add(manu);
			} else { //Tämän pitäisi olla else if (p.getPartyType() == 2), mutta jätetty nyt pelkäksi elseksi.
				Producer prod = new Producer(p);
				templist.add(prod);
			}
		}
		
		partyData.clear();
		partyData.addAll(templist);
		
		return partyData;
	}
	/**
	 * Antaa Party-oliolle viitteen Product-oliolle, kun ne tuodaan tietokannasta aplikaatioon.
	 */
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
	/**
	 * Yleinen metodi ohjelman view-osiolle, joka vie tietoa tietokantaan. Tätä metodia voi käyttää kaikenlaisten olioiden tietojen viemiseen tietokantaan.
	 * @param o sisältää joko Party- tai Product-olion 
	 * @return palauttaa True jos lisäys tietokantaan onnistui, False jos ei onnistunut tai Object oli jotain muuta kuin aiemmin sanotut.
	 */
	public boolean createNewObject(Object o) {
		boolean done = false;
		if(o instanceof Party) {
			done = createParty((Party) o);
		}else if(o instanceof Product) {
			done = createProduct((Product) o);
		}
		return done;
	}
	
	/**
	 * Poistaa tietokannasta yhden Product-olio tiedot. Poisto tapahtuu Product-olion id-muuttujaa käyttäen.
	 * @param product ilmentymä Product-oliosta joka poistetaa
	 * @return Palauttaa True jos poisto onnistui. False jos poisto epäonnistui
	 */
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
	/**
	 * Poistaa tietokannasta yhden Party-olio tiedot. Poisto tapahtuu Party-olion id-muuttujaa käyttäen.
	 * @param party ilmentymä Product-oliosta joka poistetaa
	 * @return Palauttaa True jos poisto onnistui. False jos poisto epäonnistui
	 */
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
	/**
	 * Yleinen metodi ohjelman view-osiolle, joka poistaa tietoa tietokannasta.. Tätä metodia voi käyttää kaikenlaisten olioiden tietojen poistamiseen tietokannasta.
	 * @param o sisältää joko Party- tai Product-olion 
	 * @return palauttaa True jos lisäys tietokantaan onnistui, False jos ei onnistunut tai Object oli jotain muuta kuin aiemmin sanotut.
	 */
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
