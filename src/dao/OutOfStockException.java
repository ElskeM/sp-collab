package dao;

import javax.ejb.ApplicationException;

/**Exception
 * @author simon
 *
 */
@ApplicationException(rollback=true)
public class OutOfStockException extends Exception {

	public OutOfStockException(String s) {
		super(s);
	}
}
