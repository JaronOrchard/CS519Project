package csv_classes;

import java.util.Date;

public class QueueEntry {
	private int questionId;
	private String topic;
	private Date questionCreatedAt;
	private Date questionDeletedAt;
	private int askerId;
	private int answererId;
	private int labQueueId;
	private String location;
	private Date evaluationCreatedAt;
	private Date evaluationDeletedAt;
	
	public QueueEntry(int questionId, String topic, Date questionCreatedAt, Date questionDeletedAt,
			int askerId, int answererId, int labQueueId, String location,
			Date evaluationCreatedAt, Date evaluationDeletedAt) {
		this.questionId = questionId;
		this.topic = topic;
		this.questionCreatedAt = questionCreatedAt;
		this.questionDeletedAt = questionDeletedAt;
		this.askerId = askerId;
		this.answererId = answererId;
		this.labQueueId = labQueueId;
		this.location = location;
		this.evaluationCreatedAt = evaluationCreatedAt;
		this.evaluationDeletedAt = evaluationDeletedAt;
	}
	
	public int getQuestionId() { return questionId; }
	public String getTopic() { return topic; }
	public Date getQuestionCreatedAt() { return questionCreatedAt; }
	public Date getQuestionDeletedAt() { return questionDeletedAt; }
	public int getAskerId() { return askerId; }
	public int getAnswererId() { return answererId; }
	public int getLabQueueId() { return labQueueId; }
	public String getLocation() { return location; }
	public Date getEvaluationCreatedAt() { return evaluationCreatedAt; }
	public Date getEvaluationDeletedAt() { return evaluationDeletedAt; }
	
}
