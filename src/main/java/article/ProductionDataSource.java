package article;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductionDataSource implements DataSource{

	private DataSource realDataSource;
	
	public ProductionDataSource() throws NamingException {
		InitialContext context = new InitialContext();
		this.realDataSource = (DataSource)context.lookup("java:comp/env/jdbc/bbs2");
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return realDataSource.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return realDataSource.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return realDataSource.getParentLogger();
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		realDataSource.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		realDataSource.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return realDataSource.isWrapperFor(arg0);
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return realDataSource.unwrap(arg0);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return realDataSource.getConnection();
	}

	@Override
	public Connection getConnection(String arg0, String arg1) throws SQLException {
		return realDataSource.getConnection(arg0, arg1);
	}
}
