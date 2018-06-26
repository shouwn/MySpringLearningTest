package test.article;

import java.sql.Timestamp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import article.DAOFactory;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class ArticleDAOTest {

	public static void main(String[] args) throws Exception {
		ApplicationContext context =
				new AnnotationConfigApplicationContext(DAOFactory.class);
		
		ArticleDAO articleDAO = context.getBean("testArticleDAO", ArticleDAO.class);
		Article article = articleTestObject();
		
		articleDAO.insertInId(article);
		
		Article other = articleDAO.findOne(article.getId());
		
		System.out.println("insert: " + (article.equals(other) ? "success" : "fail"));
		
		articleDAO.delete(article.getId());
		
		other = articleDAO.findOne(article.getId());
		
		System.out.println("delete: " + (other == null ? "success" : "fail"));
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
