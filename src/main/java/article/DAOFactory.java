package article;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import article.DAO.ArticleDAOJDBC;
import article.DAO.BoardDAO;
import article.DAO.UserDAO;

@Configuration
public class DAOFactory {

	@Bean
	public ArticleDAOJDBC articleDAO() {
		ArticleDAOJDBC articleDAO = new ArticleDAOJDBC();
		articleDAO.setDataSource(dataSource());
		return articleDAO;
	}
	
	@Bean
	public BoardDAO boardDAO() {
		BoardDAO boardDAO = new BoardDAO();
		boardDAO.setDataSource(dataSource());
		return boardDAO;
	}
	
	@Bean
	public UserDAO userDAO() {
		UserDAO userDAO = new UserDAO();
		userDAO.setDataSource(dataSource());
		return userDAO;
	}

	@Bean
	public DataSource dataSource() {
		return new TestDataSource();
	}
}
