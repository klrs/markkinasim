package otp.markkinasim;

import otp.markkinasim.model.Core;
import otp.markkinasim.view.IView;
import otp.markkinasim.view.View;
import javafx.application.Application;

/**
 * Hello world!
 *
 */
public class App 
{
	private IView view;
    public static void main( String[] args )
    {
       Core core = Core.getInstance();

       Application.launch(View.class, args);
    }
    
    public void setView(View view) {
    	this.view = view;

        System.out.println(view);
       }
}