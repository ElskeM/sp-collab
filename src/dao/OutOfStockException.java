package dao;

import javax.ejb.ApplicationException;

/**Exception for when an article is out of stock when trying to place an order 
 * @author simon
 *
 */
@ApplicationException(rollback=true)
public class OutOfStockException extends Exception {

	public OutOfStockException(String s) {
		super(s);
	}
}
