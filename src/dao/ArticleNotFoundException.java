package dao;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ArticleNotFoundException extends Exception {

}
