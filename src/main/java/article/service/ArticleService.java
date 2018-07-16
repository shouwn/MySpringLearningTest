package article.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

		List<Article> articles = articleDAO.findAll(1, 100, "1", "", "1"); // 완벽하게 하려면 수정 필요

		for(Article article : articles) {
			if(canUpgradeLevel(article))
				upgradeLevel(article);

			/*
			Boolean changed = null;
			if(article.getLevel() == Level.NEW
					&& Duration.between(
							article.getWriteTime().toInstant()
								.atZone(ZoneId.systemDefault())
								.toLocalDateTime(), now
					).compareTo(week) >= 0) {
				article.setLevel(Level.COMMON);
				changed = true;
			}
			else if(article.getLevel() == Level.COMMON && article.getRecommend() >= 30) {
				article.setLevel(Level.POPULAR);
				changed = true;
			}
			else changed = false;

			if(changed) articleDAO.update(article);
			 */
		}
	}
	
	public void upgradeLevel(Article article) {
		if(article.getLevel() == Level.NEW) article.setLevel(Level.COMMON);
		else if(article.getLevel() == Level.COMMON) article.setLevel(Level.POPULAR);
		articleDAO.update(article);
	}

	public boolean canUpgradeLevel(Article article) {
		Level currentLevel = article.getLevel();

		switch(currentLevel) {
		
		case NEW: 
			return (Duration.between(
						article.getWriteTime().toInstant()
							.atZone(ZoneId.systemDefault())
							.toLocalDateTime(), LocalDateTime.now()
						).compareTo(Duration.ofDays(7)) >= 0
					);
			
		case COMMON: return (article.getRecommend() >= 30);
		
		case POPULAR: return false;
		
		default: throw new IllegalArgumentException("Unknown Level: " +
				currentLevel);
		}
	}

	public void add(Article article) {
		if(article.getLevel() == null) article.setLevel(Level.NEW);
		articleDAO.insertIncludeId(article);
	}


}
