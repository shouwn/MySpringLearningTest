package article;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import article.DAO.ArticleDAO;
import article.DAO.BoardDAO;
import article.DAO.UserDAO;

@Configuration
public class DAOFactory {

	@Bean
	public ArticleDAO articleDAO() {
		ArticleDAO articleDAO = new ArticleDAO();
		articleDAO.setConnectionMaker(connectionMaker());
		return articleDAO;
	}
	
	@Bean
	public BoardDAO boardDAO() {
		BoardDAO boardDAO = new BoardDAO();
		boardDAO.setConnectionMaker(connectionMaker());
		return boardDAO;
	}
	
	@Bean
	public UserDAO userDAO() {
		UserDAO userDAO = new UserDAO();
		userDAO.setConnectionMaker(connectionMaker());
		return userDAO;
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://loacalhost/bbs2");
		
		return new TestConnectionMaker();
	}
}
