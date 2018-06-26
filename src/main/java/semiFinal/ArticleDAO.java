package semiFinal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class ArticleDAO {
	
    public static List<Article> findAll(int currentPage, int pageSize, String ss, String st, String od) 
    throws SQLException, NamingException 
    {
        String sql = "call article_findAll(?, ?, ?, ?, ?)";
        try (Connection connection = DB.getConnection("bbs2");
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

	public static int count(String ss, String st) throws Exception {
		String sql = "CALL article_count(?, ?)";
		try (Connection connection = DB.getConnection("bbs2");
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
	public static Article findOne(int id) throws Exception {
		String sql = "SELECT * FROM article WHERE id=?";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
                	Article article = new Article();
					article.setId(resultSet.getInt("id"));
					article.setTitle(resultSet.getString("title"));
					article.setBody(resultSet.getString("body"));
					article.setUserId(resultSet.getInt("userId"));
					article.setNotice(resultSet.getBoolean("notice"));
					return article;
				}
			}
			return null;
		}
	}

	public static void update(Article article) throws SQLException, NamingException {
		String sql = "UPDATE article SET " +
				"title=?, body=?, userId=?, notice=? " +
				"WHERE id = ? ";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, article.getTitle());
			statement.setString(2, article.getBody());
			statement.setInt(3, article.getUserId());
			statement.setBoolean(4, article.isNotice());
			statement.setInt(5, article.getId());
			statement.executeUpdate();
		}
	}

	public static void delete(int id) throws Exception {
		String sql = "DELETE FROM article WHERE id = ?";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}
	
	public static void insert(Article article) throws Exception {
		String sql = "INSERT article (no, title, body, userId, boardId, notice, writeTime)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DB.getConnection("bbs2");
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
}