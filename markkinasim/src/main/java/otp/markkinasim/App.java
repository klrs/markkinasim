package otp.markkinasim;

import otp.markkinasim.model.Core;

/**
 * Hello world!
 *
 */
public class App 
{
	//test
    public static void main( String[] args )
    {
        Core core = Core.getInstance();
        core.init();
        core.start();
    }
}
