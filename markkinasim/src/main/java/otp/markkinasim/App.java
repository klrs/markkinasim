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
    public static void main( String[] args )
    {
    	View view = new View();
    	view.init();
    	
    	Core core = Core.getInstance();
        core.init();
       // core.start();
        
       Application.launch(View.class, args);
       
        
    }
}