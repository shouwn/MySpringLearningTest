package article.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import article.ConnectionMaker;
import article.dto.Article;

public class ArticleDAO {
	
	private ConnectionMaker connectionMaker;
	
    public ArticleDAO(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public List<Article> findAll(int currentPage, int pageSize, String ss, String st, String od) 
    throws SQLException, NamingException, ClassNotFoundException 
    {
    	
        String sql = "call article_findAll(?, ?, ?, ?, ?)";
        try (Connection connection = connectionMaker.makeConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (currentPage - 1) * pageSize); // firstRecordIndex
            statement.setInt(2, pageSize);                     // pageSize
            statement.setString(3, ss);                        // 조회 방법
            if("2".equals(ss))
            	statement.setString(4, "%" + st + "%");
            else
            	statement.setString(4, st + "%");
            statement.setString(5, od);                        // 정렬 순서
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<Article> list = new ArrayList<Article>();
                while (resultSet.next()) {
                	Article article = new Article();
					article.setId(resultSet.getInt("id"));
					article.setNo(resultSet.getInt("no"));
					article.setUserName(resultSet.getString("name"));
					article.setBoardName(resultSet.getString("boardName"));
					article.setWriteTime(resultSet.getTimestamp("writeTime"));
					article.setTitle(resultSet.getString("title"));
                	list.add(article);
                }
                return list;
            }
        }
    }

	public int count(String ss, String st) throws Exception {
		String sql = "CALL article_count(?, ?)";
		try (Connection connection = connectionMaker.makeConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, ss);
            if("2".equals(ss))
            	statement.setString(2, "%" + st + "%");
            else
            	statement.setString(2, st + "%");
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next())
					return resultSet.getInt(1);
			}
		}
		return 0;
	}
	public Article findOne(int id) throws Exception {
		String sql = "SELECT * FROM article WHERE id=?";
		try (Connection connection = connectionMaker.makeConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
                	Article article = new Article();
					article.setId(resultSet.getInt("id"));
					article.setTitle(resultSet.getString("title"));
					article.setBody(resultSet.getString("body"));
					article.setUserId(resultSet.getInt("userId"));
					article.setBoardId(resultSet.getInt("boardId"));
					article.setNotice(resultSet.getBoolean("notice"));
					article.setWriteTime(resultSet.getTimestamp("writeTime"));
					article.setNo(resultSet.getInt("no"));
					return article;
				}
			}
			return null;
		}
	}

	public void update(Article article) throws SQLException, NamingException, ClassNotFoundException {
		String sql = "UPDATE article SET " +
				"title=?, body=?, userId=?, notice=? " +
				"WHERE id = ? ";
		try (Connection connection = connectionMaker.makeConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, article.getTitle());
			statement.setString(2, article.getBody());
			statement.setInt(3, article.getUserId());
			statement.setBoolean(4, article.isNotice());
			statement.setInt(5, article.getId());
			statement.executeUpdate();
		}
	}

	public void delete(int id) throws Exception {
		String sql = "DELETE FROM article WHERE id = ?";
		try (Connection connection = connectionMaker.makeConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}
	
	public void insert(Article article) throws Exception {
		String sql = "INSERT article (no, title, body, userId, boardId, notice, writeTime)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = connectionMaker.makeConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, article.getNo());
			statement.setString(2, article.getTitle());
			statement.setString(3, article.getBody());
			statement.setInt(4, article.getUserId());
			statement.setInt(5, article.getBoardId());
			statement.setBoolean(6, article.isNotice());
			statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			statement.executeUpdate();
		}
	}
	
	public void insertInId(Article article) throws Exception {
		String sql = "INSERT article (no, title, body, userId, boardId, notice, writeTime, id)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = connectionMaker.makeConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, article.getNo());
			statement.setString(2, article.getTitle());
			statement.setString(3, article.getBody());
			statement.setInt(4, article.getUserId());
			statement.setInt(5, article.getBoardId());
			statement.setBoolean(6, article.isNotice());
			statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			statement.setInt(8, article.getId());
			statement.executeUpdate();
		}
	}
}