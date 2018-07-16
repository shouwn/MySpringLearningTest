package article.service;

import article.dto.Article;

public interface ArticleLevelUpgradePolicy {

	boolean canUpgradeLevel(Article article);
	void upgradeLevel(Article article);
}
