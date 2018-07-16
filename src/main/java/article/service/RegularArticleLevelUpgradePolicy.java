package article.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import article.Level;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class RegularArticleLevelUpgradePolicy implements ArticleLevelUpgradePolicy{

	public static final Duration MIN_DURATION_FOR_COMMON = Duration.ofDays(7);
	public static final int MIN_RECOMMEND_FOR_POPULAR = 30;
	
	private ArticleDAO articleDAO;
	
	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	@Override
	public boolean canUpgradeLevel(Article article) {
		Level currentLevel = article.getLevel();

		switch(currentLevel) {
		
		case NEW: 
			return (Duration.between(
						article.getWriteTime().toInstant()
							.atZone(ZoneId.systemDefault())
							.toLocalDateTime(), LocalDateTime.now()
						).compareTo(MIN_DURATION_FOR_COMMON) >= 0
					);
			
		case COMMON: return (article.getRecommend() >= MIN_RECOMMEND_FOR_POPULAR);
		
		case POPULAR: return false;
		
		default: throw new IllegalArgumentException("Unknown Level: " +
				currentLevel);
		}
	}
	
	@Override
	public void upgradeLevel(Article article) {

		article.upgradeLevel();
		articleDAO.update(article);
	}
}
