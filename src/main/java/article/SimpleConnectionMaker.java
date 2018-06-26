package article;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Connection;

public class SimpleConnectionMaker implements ConnectionMaker{

	@Override
	public Connection makeConnection() throws SQLException, NamingException {
        if (dataSource == null) {
            InitialContext context = new InitialContext();
            dataSource = (DataSource)context.lookup("java:comp/env/jdbc/" + databaseName);
        }
        return dataSource.getConnection();
	}

}
