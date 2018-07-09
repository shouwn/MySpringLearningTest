package article.dto;

import java.util.Date;

import article.Level;

public class Article {
	private int id;
	private int boardId;
	private String boardName;
	private int userId;
	private int no;
	private Date writeTime;
	private String title;
	private String body;
	private boolean notice;
	private String userName;
	private Level level;
	private int read;
	private int recommend;
	
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public Date getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isNotice() {
		return notice;
	}
	public void setNotice(boolean notice) {
		this.notice = notice;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + boardId;
		result = prime * result + ((boardName == null) ? 0 : boardName.hashCode());
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + id;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + no;
		result = prime * result + (notice ? 1231 : 1237);
		result = prime * result + read;
		result = prime * result + recommend;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((writeTime == null) ? 0 : writeTime.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (boardId != other.boardId)
			return false;
		if (boardName == null) {
			if (other.boardName != null)
				return false;
		} else if (!boardName.equals(other.boardName))
			return false;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (id != other.id)
			return false;
		if (level != other.level)
			return false;
		if (no != other.no)
			return false;
		if (notice != other.notice)
			return false;
		if (read != other.read)
			return false;
		if (recommend != other.recommend)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (writeTime == null) {
			if (other.writeTime != null)
				return false;
		} else if (!writeTime.equals(other.writeTime))
			return false;
		return true;
	}
}
