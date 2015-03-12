package csv_classes;

import java.util.Date;

public class Course {
	private int id;
	private String courseName;
	private String term;
	private int status;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	
	public Course(int id, String courseName, String term, int status, Date createdAt, Date updatedAt, Date deletedAt) {
		this.id = id;
		this.courseName = courseName;
		this.term = term;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}
	
	public int getId() { return id; }
	public String getCourseName() { return courseName; }
	public String getTerm() { return term; }
	public int getStatus() { return status; }
	public Date getCreatedAt() { return createdAt; }
	public Date getUpdatedAt() { return updatedAt; }
	public Date getDeletedAt() { return deletedAt; }	
	
}
