package article.DAO;

import java.util.List;

import article.dto.Article;

public interface ArticleDAO {
	
	
	int count();
	int count(String ss, String st);
	Article findOne(int id);
	List<Article> findAll(int currentPage, int pageSize, String ss, String st, String od);
	void update(Article article);
	void delete(int id);
	void insert(Article article);
	void insertIncludeId(final Article article);
}
