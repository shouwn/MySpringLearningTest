package article;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import article.DAO.ArticleDAO;
import article.DAO.BoardDAO;
import article.DAO.UserDAO;

@Configuration
public class DAOFactory {

	@Bean
	public ArticleDAO articleDAO() {
		return new ArticleDAO(connectionMaker());
	}
	
	@Bean
	public BoardDAO boardDAO() {
		return new BoardDAO(connectionMaker());
	}
	
	@Bean
	public UserDAO userDAO() {
		return new UserDAO(connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		//return new SimpleConnectionMaker();
		return new TestConnectionMaker();
	}
}
