package article;

import article.DAO.ArticleDAO;
import article.DAO.BoardDAO;
import article.DAO.UserDAO;

public class TestDAOFactory {
	
	public ArticleDAO articleDAO() {
		return new ArticleDAO(connectionMaker());
	}
	
	public BoardDAO boardDAO() {
		return new BoardDAO(connectionMaker());
	}
	
	public UserDAO userDAO() {
		return new UserDAO(connectionMaker());
	}
	
	public ConnectionMaker connectionMaker() {
		return new TestConnectionMaker();
	}
}
