package test.article;

import java.sql.Timestamp;

import article.TestDAOFactory;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class ArticleDAOTest {

	public static void main(String[] args) throws Exception {
		Article article = articleTestObject();
		ArticleDAO articleDAO = new TestDAOFactory().articleDAO();
		
		articleDAO.insertInId(article);
		
		Article other = articleDAO.findOne(article.getId());
		
		System.out.println(article.equals(other));
		
		articleDAO.delete(article.getId());
		
		other = articleDAO.findOne(article.getId());
		
		System.out.println(other == null);
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
