package dao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class OutOfStockException extends Exception {

}
