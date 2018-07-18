package article.service;

import article.dto.Article;

public interface ArticleService {
	void add(Article article);
	void upgradeLevels();
}
