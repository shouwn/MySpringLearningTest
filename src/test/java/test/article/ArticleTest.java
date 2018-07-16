package test.article;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import article.Level;
import article.dto.Article;

public class ArticleTest {

	Article article;

	@BeforeEach
	public void setup() {
		this.article = new Article();
	}

	@Test
	public void upgradeLevel() {
		Level[] levels = Level.values();

		for(Level level : levels) {
			if(level.nextLevel() == null) continue;

			article.setLevel(level);
			article.upgradeLevel();

			Assertions.assertEquals(level.nextLevel(), article.getLevel());
		}
	}

	@Test
	public void cannotUpgradeLevel() {
		Level[] levels = Level.values();

		Assertions.assertThrows(IllegalStateException.class, () ->{
			for(Level level : levels) {
				if(level.nextLevel() != null) continue;

				article.setLevel(level);
				article.upgradeLevel();
			}
		});
	}
}
