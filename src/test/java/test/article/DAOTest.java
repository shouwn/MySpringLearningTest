package test.article;

import java.sql.Timestamp;

import article.DAOFactory;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class DAOTest {

	public static void main(String[] args) {
		Article article = articleTestObject();
		ArticleDAO articleDAO = new DAOFactory().articleDAO();
		articleDAO
	}
	
	public static Article articleTestObject() {
		Article article = new Article();
		article.setId(215);
		article.setNo(12345);
		article.setTitle("TEST");
		article.setBody("BODY FOR TEST");
		article.setUserId(1);
		article.setBoardId(1);
		article.setNotice(true);
		article.setWriteTime(new Timestamp(System.currentTimeMillis()));
		return article;
	}
}
