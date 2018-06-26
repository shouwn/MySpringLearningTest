package article;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;



public interface ConnectionMaker {

	Connection makeConnection() throws SQLException, NamingException, ClassNotFoundException;
}
