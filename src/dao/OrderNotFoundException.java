package dao;

import javax.ejb.ApplicationException;

/**Exception for when searching for a order that does not exist
 * @author simon
 *
 */
@ApplicationException(rollback=true)
public class OrderNotFoundException extends Exception {

}
