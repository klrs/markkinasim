package otp.markkinasim.view;
/**
*
* @author Joonas Lapinlampi
*/
import javafx.collections.ObservableList;
import javafx.stage.Window;
import otp.markkinasim.simulation.Party;
import otp.markkinasim.simulation.Person;
import otp.markkinasim.simulation.Product;

public interface IView {

	public abstract ObservableList<Person> getPersonData();

	public abstract void setScene(int i);

	public abstract Window getPrimaryStage();

	public abstract ObservableList<Party> getPartyData();

	public boolean showPartyEditDialog(Party party);

	public boolean showProductEditDialog(Product product);

	public boolean showRawmaterialEditDialog(Product product);

	public abstract ObservableList<Product> getAllProductData();

	public abstract void writeSimulationLog(String msg);

	public abstract void createNewObject(Object o);

	public abstract void removeObject(Object o);

	public abstract ObservableList<Party> getSimulationPartyData();

	public abstract ObservableList<Product> getSimulationProductData();

	public abstract void startSimulation();

}
