package test.article;

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
import article.dto.Article;
import article.service.ArticleService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/testApplicationContext.xml")
public class ArticleServiceTest {
	@Autowired ArticleService articleService;

	List<Article> articles;

	@Test
	public void beanTest() {
		Assertions.assertNotNull(articleService);
	}

	@BeforeEach
	public void setUp() {
		articles = Arrays.asList(
				TestObject.makeArticleTestObject(250, Level.NEW, 50, 0),
				TestObject.makeArticleTestObject(251),
				TestObject.makeArticleTestObject(252),
				TestObject.makeArticleTestObject(253),
				TestObject.makeArticleTestObject(254)
				);
	}
}
