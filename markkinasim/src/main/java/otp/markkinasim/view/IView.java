package otp.markkinasim.view;

import javafx.collections.ObservableList;
import javafx.stage.Window;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;

public interface IView {

	public abstract void setScene(int i);

	public abstract Window getPrimaryStage();

	public abstract ObservableList<Party> getPartyData();

	public boolean showPartyEditDialog(Party party);

	public abstract ObservableList<Product> getRawmaterialData();

	public abstract ObservableList<Product> getProductData();

	public boolean showProductEditDialog(Product product);

	public boolean showRawmaterialEditDialog(Product product);

	public abstract ObservableList<Product> getAllProductData();

	public abstract void writeSimulationLog(String msg);

}
