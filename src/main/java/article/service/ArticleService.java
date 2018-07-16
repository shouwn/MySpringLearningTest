package article.service;

import java.util.List;

import article.Level;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class ArticleService {

	private ArticleDAO articleDAO;
	private ArticleLevelUpgradePolicy levelUpgrader;

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	public void setLevelUpgrader(ArticleLevelUpgradePolicy levelUpgrader) {
		this.levelUpgrader = levelUpgrader;
	}

	public void upgradeLevels() {

		List<Article> articles = articleDAO.findAll(1, 100, "1", "", "1"); // 완벽하게 하려면 수정 필요

		for(Article article : articles) {
			if(this.levelUpgrader.canUpgradeLevel(article))
				this.levelUpgrader.upgradeLevel(article);
		}
	}
	
	public void add(Article article) {
		if(article.getLevel() == null) article.setLevel(Level.NEW);
		articleDAO.insertIncludeId(article);
	}


}
