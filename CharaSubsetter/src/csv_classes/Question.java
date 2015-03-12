package csv_classes;

import java.util.Date;

public class Question {
	private int id;
	private int beingAnswered;
	private String topic;
	private Date createdAt;
	private Date deletedAt;
	private int askerId;
	private int answererId;
	private int labQueueId;
	private String location;
	
	public Question(int id, int beingAnswered, String topic, Date createdAt, Date deletedAt,
			int askerId, int answererId, int labQueueId, String location) {
		this.id = id;
		this.beingAnswered = beingAnswered;
		this.topic = topic;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
		this.askerId = askerId;
		this.answererId = answererId;
		this.labQueueId = labQueueId;
		this.location = location;
	}
	
	public int getId() { return id; }
	public int getBeingAnswered() { return beingAnswered; }
	public String getTopic() { return topic; }
	public Date getCreatedAt() { return createdAt; }
	public Date getDeletedAt() { return deletedAt; }
	public int getAskerId() { return askerId; }
	public int getAnswererId() { return answererId; }
	public int getLabQueueId() { return labQueueId; }
	public String getLocation() { return location; }
	
}
