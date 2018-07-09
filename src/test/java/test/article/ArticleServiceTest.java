package test.article;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import article.service.ArticleService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/testApplicationContext.xml")
public class ArticleServiceTest {
	@Autowired ArticleService articleService;
	
	@Test
	public void beanTest() {
		Assertions.assertNotNull(articleService);
	}
}
