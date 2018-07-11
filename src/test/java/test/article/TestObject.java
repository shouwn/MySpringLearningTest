package test.article;

import java.sql.Timestamp;

import article.Level;
import article.dto.Article;

public class TestObject {
	
	public static Article makeArticleTestObject(int id) {
		Article article = new Article();
		article.setId(id);
		article.setNo(12345);
		article.setTitle("TEST");
		article.setBody("BODY FOR TEST");
		article.setUserId(1);
		article.setBoardId(1);
		article.setNotice(true);
		article.setWriteTime(new Timestamp(System.currentTimeMillis()));
		article.setReadCount(0);
		article.setLevel(Level.NEW);
		article.setRecommend(0);
		return article;
	}
	
	public static Article makeArticleTestObject(int id, Level level, int readCount, int recommend) {
		Article article = new Article();
		article.setId(id);
		article.setNo(12345);
		article.setTitle("TEST");
		article.setBody("BODY FOR TEST");
		article.setUserId(1);
		article.setBoardId(1);
		article.setNotice(true);
		article.setWriteTime(new Timestamp(System.currentTimeMillis()));
		article.setReadCount(readCount);
		article.setLevel(level);
		article.setRecommend(recommend);
		return article;
	}
	
	public static Article makeArticleTestObject(int id, Level level, int readCount, int recommend, Timestamp writeTime) {
		Article article = new Article();
		article.setId(id);
		article.setNo(12345);
		article.setTitle("TEST");
		article.setBody("BODY FOR TEST");
		article.setUserId(1);
		article.setBoardId(1);
		article.setNotice(true);
		article.setWriteTime(writeTime);
		article.setReadCount(readCount);
		article.setLevel(level);
		article.setRecommend(recommend);
		return article;
	}
}
