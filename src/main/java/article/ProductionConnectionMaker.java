package article;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductionConnectionMaker implements ConnectionMaker{

	@Override
	public Connection makeConnection() throws SQLException, NamingException {
		InitialContext context = new InitialContext();
		DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/bbs2");
		return dataSource.getConnection();
	}
}
