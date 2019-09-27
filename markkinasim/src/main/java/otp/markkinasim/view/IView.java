package otp.markkinasim.view;

import javafx.collections.ObservableList;
import javafx.stage.Window;
import otp.markkinasim.model.Party;
import otp.markkinasim.model.Product;
import otp.markkinasim.model.Rawmaterial;

public interface IView {

	public abstract void setScene(int i);

	public abstract Window getPrimaryStage();

	public abstract ObservableList<Party> getPartyData();

	public boolean showPartyEditDialog(Party party);

	public abstract ObservableList<Rawmaterial> getRawmaterialData();

	public abstract ObservableList<Product> getProductData();

	public boolean showProductEditDialog(Product product);

	public boolean showRawmaterialEditDialog(Rawmaterial rawmaterial);

}
