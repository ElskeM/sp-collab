package service;

public class ReceiptSendingService {
	
	public static void sendReciept(String firstName, String lastName, String adress, String zipcode, String city)
	throws ServiceUnavailableException { 
		double rand = Math.random();
		if(rand < 0.5) {
			throw new ServiceUnavailableException();
		}
		
		
	}

}
