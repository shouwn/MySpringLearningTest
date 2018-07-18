package test.article;

import static article.service.ArticleServiceImpl.MIN_DURATION_FOR_COMMON;
import static article.service.ArticleServiceImpl.MIN_RECOMMEND_FOR_POPULAR;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import article.Level;
import article.DAO.ArticleDAO;
import article.dto.Article;
import article.service.ArticleService;
import article.service.ArticleServiceImpl;
import article.service.ArticleServiceTx;
import article.service.MockMailSender;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/testApplicationContext.xml")
public class ArticleServiceTest {
	
	static class TestArticleService extends ArticleServiceImpl{
		private int id;

		public TestArticleService(int id) {
			this.id = id;
		}
		
		@Override
		protected void upgradeLevel(Article article) {
			if(article.getId() == this.id) throw new TestArticleServiceException();
			super.upgradeLevel(article);
		}
	}
	
	static class TestArticleServiceException extends RuntimeException{
	}
	
	@Autowired ArticleService articleService;
	@Autowired ArticleServiceImpl articleServiceImpl;
	@Autowired ArticleDAO articleDAO;
	@Autowired PlatformTransactionManager transactionManager;
	@Autowired MailSender mailSender;

	List<Article> articles;

	@Test
	public void beanTest() {
		Assertions.assertNotNull(articleService);
	}

	@BeforeEach
	public void setUp() {
		articles = Arrays.asList(
				TestObject.makeArticleTestObject(
						250, Level.NEW, 50, 0, 
						Timestamp.valueOf(LocalDateTime.now().minus(MIN_DURATION_FOR_COMMON.minusDays(1)))
				),
				TestObject.makeArticleTestObject(
						251, Level.NEW, 50, 0,
						Timestamp.valueOf(LocalDateTime.now().minus(MIN_DURATION_FOR_COMMON))
				),
				TestObject.makeArticleTestObject(252, Level.COMMON, 50, MIN_RECOMMEND_FOR_POPULAR - 1),
				TestObject.makeArticleTestObject(253, Level.COMMON, 50, MIN_RECOMMEND_FOR_POPULAR),
				TestObject.makeArticleTestObject(254, Level.POPULAR, 100, Integer.MAX_VALUE)
				);
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		ArticleServiceImpl testArticleService = new TestArticleService(articles.get(3).getId());
		testArticleService.setArticleDAO(this.articleDAO);
		testArticleService.setMailSender(mailSender);
		
		ArticleServiceTx txArticleService = new ArticleServiceTx();
		txArticleService.setTransactionManager(transactionManager);
		txArticleService.setArticleService(testArticleService);

		// delete for test
		for(Article article : articles)
			articleDAO.delete(article.getId());
		
		// insert for test
		for(Article article : articles)
			articleDAO.insertIncludeId(article);
		
		try {
			txArticleService.upgradeLevels();
			Assertions.fail("TestUserServiceException expected");
		}
		catch(TestArticleServiceException e) {
			
		}
		
		checkLevelUpgraded(articles.get(1), false);
	}

	@Test
	@DirtiesContext
	public void upgradeLevels() throws Exception {
		// delete for test
		for(Article article : articles)
			articleDAO.delete(article.getId());

		// insert for test
		for(Article article : articles)
			articleDAO.insertIncludeId(article);
		
		MockMailSender mockMailSender = new MockMailSender();
		articleServiceImpl.setMailSender(mockMailSender);

		articleService.upgradeLevels();

		checkLevelUpgraded(articles.get(0), false);
		checkLevelUpgraded(articles.get(1), true);
		checkLevelUpgraded(articles.get(2), false);
		checkLevelUpgraded(articles.get(3), true);
		checkLevelUpgraded(articles.get(4), false);
		
		List<String> request = mockMailSender.getRequests();
		Assertions.assertEquals(2, request.size());
	}

	@Test
	public void add() {

		// delete for test
		for(Article article : articles)
			articleDAO.delete(article.getId());

		Article articleWithLevel = articles.get(4);
		Article articleWithoutLevel = articles.get(1);
		articleWithoutLevel.setLevel(null);

		articleService.add(articleWithLevel);
		articleService.add(articleWithoutLevel);

		checkLevel(Level.NEW, articleWithoutLevel);
		checkLevel(Level.POPULAR, articleWithLevel);
	}

	public void checkLevelUpgraded(Article article, boolean upgraded) {
		Article articleUpdate = articleDAO.findOne(article.getId());
		if(upgraded)
			Assertions.assertEquals(
					article.getLevel().nextLevel(), 
					articleUpdate.getLevel()
					);
		else
			Assertions.assertEquals(article.getLevel(), articleUpdate.getLevel());
	}

	public void checkLevel(Level expected, Article article) {
		Assertions.assertEquals(expected, articleDAO.findOne(article.getId()).getLevel());
	}
}
