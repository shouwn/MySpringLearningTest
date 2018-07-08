package test.article;

import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import article.DAO.ArticleDAO;
import article.dto.Article;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/testApplicationContext.xml")
//하나의 어플리케이션 컨텍스트 만들기 + 스프링 확장 기능 사용
//@DirtiesContext 내부에서 applicationContext의 내용을 변경할 경우, 이게 끝나고 다른 테스트를 할 때 다시 context를 만들어줌
public class ArticleDAOTest {

	@Autowired
	private ArticleDAO articleDAO;

	private static Article article1;
	private static Article article2;
	private static Article article3;

	@BeforeAll
	public static void init() {
		article1 = articleTestObject(215);
		article2 = articleTestObject(216);
		article3 = articleTestObject(217);
	}

	public static Article articleTestObject(int id) {
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

	}
}
