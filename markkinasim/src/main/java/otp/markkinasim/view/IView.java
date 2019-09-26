package otp.markkinasim.view;

import javafx.collections.ObservableList;
import javafx.stage.Window;
import otp.markkinasim.model.Party;

public interface IView {

	public abstract void setScene(int i);

	public abstract Window getPrimaryStage();

	public abstract ObservableList<Party> getPartyData();

	public boolean showPartyEditDialog(Party party);

}
