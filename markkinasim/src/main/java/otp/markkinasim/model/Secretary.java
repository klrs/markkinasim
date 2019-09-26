package otp.markkinasim.model;

import org.hibernate.SessionFactory;
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

}
