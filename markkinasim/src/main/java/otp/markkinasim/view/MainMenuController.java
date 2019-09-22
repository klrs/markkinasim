package otp.markkinasim.view;

public class MainMenuController implements IMainMenuController{
	
	private IView view;
	
	public MainMenuController(View view2) {
		view = view2;
	}
	
	@Override
	public void startSimulation() {
		view.setScene(1);
	}
	
	@Override
	public void simulationOptions() {
		//TODO
		System.out.println("Simulaation asetukset ei valmis");
	}
	
	@Override
	public void exitButton() {
		System.exit(0);
	}
}
