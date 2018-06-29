package test.article;

import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import article.DAO.ArticleDAO;
import article.dto.Article;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
// 하나의 어플리케이션 컨텍스트 만들기 + 스프링 확장 기능 사용
public class ArticleDAOTest {
	
	private ArticleDAO articleDAO;
	
	@Autowired
	private ApplicationContext context;
	
	/*
	@BeforeAll
	public static void init() {
		context =
				//new AnnotationConfigApplicationContext(DAOFactory.class);
				//new ClassPathXmlApplicationContext("applicationContext.xml", TestConnectionMaker.class); 예전 버전
				new GenericXmlApplicationContext("applicationContext.xml");
				// 클래스 패스의 시작은 src/main/java
	}
	*/
	
	@BeforeEach
	public void setUp() {
		/*
		ApplicationContext context =
				//new AnnotationConfigApplicationContext(DAOFactory.class);
				new ClassPathXmlApplicationContext("applicationContext.xml", TestConnectionMaker.class);
		*/
		this.articleDAO = context.getBean("articleDAO", ArticleDAO.class);
	}

	@Test
	public void addAndGet() throws Exception {

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
		
		articleDAO.delete(1000000);
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> articleDAO.findOne(1000000));
	}
	
	@Test
	public void count() throws Exception {
		
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
