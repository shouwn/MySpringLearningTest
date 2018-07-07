package article.DAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import article.dto.Article;

public class ArticleDAO {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Article> findAll(int currentPage, int pageSize, String ss, String st, String od) 
	{
		String sql = "call article_findAll(?, ?, ?, ?, ?)";
		
		currentPage = Math.max(currentPage, 1);
		return this.jdbcTemplate.query(
				sql,
				new Object[] {
						(currentPage - 1) * pageSize,
						pageSize,
						ss,
						st,
						od
				},
				(resultSet, rowNum) -> {
					Article article = new Article();
					article.setId(resultSet.getInt("id"));
					article.setNo(resultSet.getInt("no"));
					article.setUserName(resultSet.getString("name"));
					article.setBoardName(resultSet.getString("boardName"));
					article.setWriteTime(resultSet.getTimestamp("writeTime"));
					article.setTitle(resultSet.getString("title"));
				
					return article;
				});
	}

	public int count(String ss, String st){
		String sql = "CALL article_count(?, ?)";

		return this.jdbcTemplate.queryForObject(
				sql, 
				new Object[] {ss, st},
				Integer.class
				);
	}

	public int count(){
		String sql = "SELECT COUNT(*) FROM article";

		return this.jdbcTemplate.queryForObject(sql, Integer.class);
	}

	public Article findOne(int id) {
		String sql = "SELECT * FROM article WHERE id=?";
		
		return this.jdbcTemplate.queryForObject(
				sql, 
				new Object[] {id},
				(resultSet, rowNum) -> {
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
				});
	}

	public void update(Article article) throws SQLException, NamingException, ClassNotFoundException {
		
		String sql = "UPDATE article SET " +
				"title=?, body=?, userId=?, notice=? " +
				"WHERE id = ? ";
		
		this.jdbcTemplate.update(
				sql, 
				new Object[] {
					article.getTitle(),
					article.getBody(),
					article.getUserId(),
					article.isNotice(),
					article.getId()
				});
	}

	public void delete(int id) {

		String sql = "DELETE FROM article WHERE id = ?";

		this.jdbcTemplate.update(sql, id);
	}

	public void insert(Article article) {

		String sql = "INSERT article (no, title, body, userId, boardId, notice, writeTime)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?)";

		this.jdbcTemplate.update(sql, 
				article.getNo(),
				article.getTitle(),
				article.getBody(),
				article.getUserId(),
				article.getBoardId(),
				article.isNotice(),
				new Timestamp(System.currentTimeMillis())
				);
	}

	public void insertIncludeId(final Article article) {

		String sql = "INSERT article (no, title, body, userId, boardId, notice, writeTime, id)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		this.jdbcTemplate.update(sql, 
				article.getNo(),
				article.getTitle(),
				article.getBody(),
				article.getUserId(),
				article.getBoardId(),
				article.isNotice(),
				article.getWriteTime(),
				article.getId()
				);
	}


}