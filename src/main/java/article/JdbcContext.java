package article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import article.DAO.StatementStrategy;

public class JdbcContext {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {

		try (Connection c = dataSource.getConnection();
				PreparedStatement statement = stmt.makePreparedStatement(c)) {

			statement.executeUpdate();
		}
	}
	
	public void executeSql(final String query) throws SQLException {
		workWithStatementStrategy((c) ->{
			return c.prepareStatement(query);
		});
	}
	
	public void executeSql(final String query, Object... elements) throws SQLException {
		workWithStatementStrategy((c) ->{
			PreparedStatement statement = c.prepareStatement(query);
			int i = 1;
			
			for(Object element : elements) 
				statement.setObject(i++, element);
			
			return statement;
		});
	}
}
