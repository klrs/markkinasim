package otp.markkinasim.view;

import javafx.collections.ObservableList;
import javafx.stage.Window;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Person;
import otp.markkinasim.model.Product;

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

}
