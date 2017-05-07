package edu.ecut.entity;

import java.sql.Timestamp;
import java.util.List;

public class Topic {

	private int id;
	private String title;
	private String content;
	private Timestamp publishTime;
	private String publishIp;

	private User user; // user_id
	
	private List<Explain> explains ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishIp() {
		return publishIp;
	}

	public void setPublishIp(String publishIp) {
		this.publishIp = publishIp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Explain> getExplains() {
		return explains;
	}

	public void setExplains(List<Explain> explains) {
		this.explains = explains;
	}

}
