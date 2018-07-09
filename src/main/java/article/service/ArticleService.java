package article.service;

import java.util.List;

import article.Level;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class ArticleService {
	private ArticleDAO articleDAO;

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}
	
	public void upgradeLevels() {
		List<Article> articles = articleDAO.findAll(1, 10, "1", "", "1");
		LocalDate 
		
		for(Article article : articles) {
			if(article.getLevel() == Level.NEW && )
		}
	}
	
}
