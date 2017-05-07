package edu.ecut.entity;

import java.sql.Timestamp;

public class Explain {

	private int id;
	private String content;
	private Timestamp explainTime;
	private String explainIp;

	private User user; // user_id
	private Topic topic; // topic_id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getExplainTime() {
		return explainTime;
	}

	public void setExplainTime(Timestamp explainTime) {
		this.explainTime = explainTime;
	}

	public String getExplainIp() {
		return explainIp;
	}

	public void setExplainIp(String explainIp) {
		this.explainIp = explainIp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
