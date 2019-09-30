package otp.markkinasim.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Secretary {
	
	SessionFactory factory = null;
	final StandardServiceRegistry registry = null;
	
	public Secretary() {

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

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
	
	public boolean createPerson(Person person) {
		boolean done = false;
		Transaction transaktio = null;
		try (Session istunto = factory.openSession()) {
			transaktio = istunto.beginTransaction();
			Person result = istunto.get(Person.class, person.getId());
			if (result == null) {
				istunto.saveOrUpdate(person);
				transaktio.commit();
				done = true;
			}
		} catch (Exception e) {
			if (transaktio != null) transaktio.rollback();
			throw e;
		}
		return done;
	}

}
