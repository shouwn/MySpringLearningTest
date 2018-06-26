package article;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.mysql.jdbc.Connection;

public interface ConnectionMaker {

	Connection makeConnection() throws SQLException, NamingException;
}
