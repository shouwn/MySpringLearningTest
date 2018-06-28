package test.article;

import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import article.TestConnectionMaker;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class ArticleDAOTest {

	@Test
	public void addAndGet() throws Exception {

		ApplicationContext context =
				//new AnnotationConfigApplicationContext(DAOFactory.class);
				new ClassPathXmlApplicationContext("applicationContext.xml", TestConnectionMaker.class);

		ArticleDAO articleDAO = context.getBean("articleDAO", ArticleDAO.class);
		Article article1 = articleTestObject(215);
		Article article2 = articleTestObject(216);
		
		int count = articleDAO.count();
		articleDAO.delete(article1.getId());
		articleDAO.delete(article2.getId());

		Assertions.assertEquals(count - 2, articleDAO.count());

		articleDAO.insertIncludeId(article1);
		articleDAO.insertIncludeId(article2);
		
		Assertions.assertEquals(count, articleDAO.count());
		Assertions.assertEquals(article1, articleDAO.findOne(article1.getId()));
		Assertions.assertEquals(article2, articleDAO.findOne(article2.getId()));
	}
	
	@Test
	public void getArticleFilure() throws Exception {

		ApplicationContext context =
				//new AnnotationConfigApplicationContext(DAOFactory.class);
				new ClassPathXmlApplicationContext("applicationContext.xml", TestConnectionMaker.class);
		
		ArticleDAO articleDAO = context.getBean("articleDAO", ArticleDAO.class);
		articleDAO.delete(1000000);
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> articleDAO.findOne(1000000));
	}
	
	@Test
	public void count() throws Exception {
		ApplicationContext context =
				//new AnnotationConfigApplicationContext(DAOFactory.class);
				new ClassPathXmlApplicationContext("applicationContext.xml", TestConnectionMaker.class);

		ArticleDAO articleDAO = context.getBean("articleDAO", ArticleDAO.class);
		
		int[] index = new int[] {0, 1, 2};
		Article[] articles = new Article[3];
		
		for(int i : index) {
			articles[i] = articleTestObject(220 + i);
			articleDAO.delete(articles[i].getId());
		}
		
		int count = articleDAO.count();

		for(int i : index) {
			articleDAO.insertIncludeId(articles[i]);
			Assertions.assertEquals(count + i + 1, articleDAO.count());
		}
	}

	public Article articleTestObject(int id) {
		Article article = new Article();
		article.setId(id);
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
