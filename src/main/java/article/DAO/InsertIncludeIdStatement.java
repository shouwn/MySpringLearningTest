package article.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import article.dto.Article;

public class InsertIncludeIdStatement implements StatementStrategy{
	
	Article article;
	
	public InsertIncludeIdStatement(Article article) {
		this.article = article;
	}

	@Override
	public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
		String sql = "INSERT article (no, title, body, userId, boardId, notice, writeTime, id)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement statement = c.prepareStatement(sql);
		
		statement.setInt(1, article.getNo());
		statement.setString(2, article.getTitle());
		statement.setString(3, article.getBody());
		statement.setInt(4, article.getUserId());
		statement.setInt(5, article.getBoardId());
		statement.setBoolean(6, article.isNotice());
		statement.setTimestamp(7, (Timestamp) article.getWriteTime());
		statement.setInt(8, article.getId());
		
		return statement;
	}

}
