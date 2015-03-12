package csv_classes;

import java.util.Date;

public class Evaluation {
	private int id;
	private String comments;
	private int quality;
	private Date createdAt;
	private Date deletedAt;
	private int questionId;
	
	public Evaluation(int id, String comments, int quality, Date createdAt, Date deletedAt, int questionId) {
		this.id = id;
		this.comments = comments;
		this.quality = quality;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
		this.questionId = questionId;
	}
	
	public int getId() { return id; }
	public String getComments() { return comments; }
	public int getQuality() { return quality; }
	public Date getCreatedAt() { return createdAt; }
	public Date getDeletedAt() { return deletedAt; }
	public int getQuestionId() { return questionId; }
	
}
