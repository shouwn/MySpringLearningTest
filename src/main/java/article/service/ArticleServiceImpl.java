package article.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import article.Level;
import article.DAO.ArticleDAO;
import article.dto.Article;

public class ArticleServiceImpl implements ArticleService{

	public static final Duration MIN_DURATION_FOR_COMMON = Duration.ofDays(7);
	public static final int MIN_RECOMMEND_FOR_POPULAR = 30;

	private ArticleDAO articleDAO;
	private MailSender mailSender;

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void upgradeLevels(){
			List<Article> articles = articleDAO.findAll(1, 100, "1", "", "1"); // 완벽하게 하려면 수정 필요

			for(Article article : articles) {
				if(canUpgradeLevel(article))
					upgradeLevel(article);

			}

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

	protected void upgradeLevel(Article article) {
		article.upgradeLevel();
		articleDAO.update(article);
		sendUpgradeEMail(article);
	}

	private void sendUpgradeEMail(Article article) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("sjonghyon@gmail.com");
		mailMessage.setFrom("sjonghyon@gmail.com");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("게시글 등급이 " + article.getLevel().name());
		
		this.mailSender.send(mailMessage);
	}

	private boolean canUpgradeLevel(Article article) {
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
	public void add(Article article) {
		if(article.getLevel() == null) article.setLevel(Level.NEW);
		articleDAO.insertIncludeId(article);
	}


}
