package otp.markkinasim.model;

public class test {
	
	public static void main(String[] args) {
		Person person = new Person();
		
		Secretary sec = new Secretary();
		
		boolean success = sec.createPerson(person);
		
		System.out.println(success);
	}

}
