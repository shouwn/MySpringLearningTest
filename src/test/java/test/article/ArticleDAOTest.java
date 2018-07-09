package test.article;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import article.Level;
import article.DAO.ArticleDAO;
import article.dto.Article;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/testApplicationContext.xml")
//하나의 어플리케이션 컨텍스트 만들기 + 스프링 확장 기능 사용
//@DirtiesContext 내부에서 applicationContext의 내용을 변경할 경우, 이게 끝나고 다른 테스트를 할 때 다시 context를 만들어줌
public class ArticleDAOTest {

	
	@Autowired private ArticleDAO articleDAO;
	@Autowired private DataSource dataSource;

	private static Article article1;
	private static Article article2;
	private static Article article3;

	@BeforeEach
	public void init() {
		article1 = TestObject.makeArticleTestObject(215, Level.COMMON, 52, 2);
		article2 = TestObject.makeArticleTestObject(216, Level.POPULAR, 126, 54);
		article3 = TestObject.makeArticleTestObject(217, Level.NEW, 2, 1);
	}

	/*
	private ApplicationContext context; 

	@BeforeAll
	public static void init() {
		/*
		context =
				//new AnnotationConfigApplicationContext(DAOFactory.class);
				//new ClassPathXmlApplicationContext("applicationContext.xml", TestConnectionMaker.class); 예전 버전
				new GenericXmlApplicationContext("applicationContext.xml");
				// 클래스 패스의 시작은 src/main/java

		articleDAO = new ArticleDAO();
		articleDAO.setDataSource(new TestDataSource());
	}

	@BeforeEach
	public void setUp() {

		ApplicationContext context =
				//new AnnotationConfigApplicationContext(DAOFactory.class);
				new ClassPathXmlApplicationContext("applicationContext.xml", TestConnectionMaker.class);

		this.articleDAO = context.getBean("articleDAO", ArticleDAO.class);
	}
	 */

	@Test
	public void findAll(){

		articleDAO.delete(article1.getId());
		articleDAO.delete(article2.getId());

		articleDAO.insertIncludeId(article1);
		articleDAO.insertIncludeId(article2);

		Assertions.assertNotEquals(0, articleDAO.findAll(1, 10, "1", "", "1").size());
	}

	@Test
	public void addAndGet() {

		articleDAO.delete(article1.getId());
		articleDAO.delete(article2.getId());
		int count = articleDAO.count();

		articleDAO.insertIncludeId(article1);
		articleDAO.insertIncludeId(article2);

		Assertions.assertEquals(count + 2, articleDAO.count());
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

		articleDAO.delete(article1.getId());
		articleDAO.delete(article2.getId());
		articleDAO.delete(article3.getId());

		int count = articleDAO.count();

		articleDAO.insertIncludeId(article1);
		articleDAO.insertIncludeId(article2);
		articleDAO.insertIncludeId(article3);
		
		Assertions.assertEquals(count + 3, articleDAO.count());
	}

	@Test
	public void duplicateKey() {
		articleDAO.delete(article1.getId());
		
		Assertions.assertThrows(DuplicateKeyException.class, () -> {

			articleDAO.insertIncludeId(article1);
			articleDAO.insertIncludeId(article1);
		});
	}
	
	@Test
	public void sqlExceptionTranslate() {
		articleDAO.delete(article1.getId());
		
		try {
			articleDAO.insertIncludeId(article1);
			articleDAO.insertIncludeId(article1);
		
		}
		catch(DuplicateKeyException ex) {
			SQLException sqlEx = (SQLException)ex.getRootCause();
			SQLExceptionTranslator set =
					new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
			
			Assertions.assertEquals(DuplicateKeyException.class, 
					set.translate(null, null, sqlEx).getClass());
		}
	}
	
	@Test
	public void update() {
		articleDAO.delete(article1.getId());
		articleDAO.delete(article2.getId());
		
		articleDAO.insertIncludeId(article1);
		articleDAO.insertIncludeId(article2);
		
		article1.setBoardId(1);
		article1.setLevel(Level.NEW);
		article1.setReadCount(5123);
		article1.setRecommend(100);
		articleDAO.update(article1);
		
		Assertions.assertEquals(article1, articleDAO.findOne(article1.getId()));
		Assertions.assertEquals(article2, articleDAO.findOne(article2.getId()));
	}
}
