package test.article;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import article.Level;
import article.DAO.ArticleDAO;
import article.dto.Article;
import article.service.ArticleService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/testApplicationContext.xml")
public class ArticleServiceTest {
	@Autowired ArticleService articleService;
	@Autowired ArticleDAO articleDAO;
	
	List<Article> articles;

	@Test
	public void beanTest() {
		Assertions.assertNotNull(articleService);
	}

	@BeforeEach
	public void setUp() {
		articles = Arrays.asList(
				TestObject.makeArticleTestObject(250, Level.NEW, 50, 0, Timestamp.valueOf(LocalDateTime.now().minusDays(6))),
				TestObject.makeArticleTestObject(251, Level.NEW, 50, 0, Timestamp.valueOf(LocalDateTime.now().minusDays(7))),
				TestObject.makeArticleTestObject(252, Level.COMMON, 50, 29),
				TestObject.makeArticleTestObject(253, Level.COMMON, 50, 30),
				TestObject.makeArticleTestObject(254, Level.POPULAR, 100, 100)
				);
	}
	
	@Test
	public void upgradeLevels() {
		// delete for test
		for(Article article : articles)
			articleDAO.delete(article.getId());
		
		// insert for test
		for(Article article : articles)
			articleDAO.insertIncludeId(article);
		
		articleService.upgradeLevels();
		
		checkLevel(Level.NEW, articles.get(0));
		checkLevel(Level.COMMON, articles.get(1));
		checkLevel(Level.COMMON, articles.get(2));
		checkLevel(Level.POPULAR, articles.get(3));
		checkLevel(Level.POPULAR, articles.get(4));
			
	}
	
	public void checkLevel(Level expected, Article article) {
		Assertions.assertEquals(expected, articleDAO.findOne(article.getId()).getLevel());
	}
}
