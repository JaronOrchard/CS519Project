package csv_classes;

import java.util.Date;

public class LabQueue {
	private int id;
	private String name;
	private Date createdAt;
	private Date deletedAt;
	private int courseId;
	
	public LabQueue(int id, String name, Date createdAt, Date deletedAt, int courseId) {
		this.id = id;
		this.name = name;
		this.createdAt = createdAt;
		this.deletedAt = deletedAt;
		this.courseId = courseId;
	}
	
	public int getId() { return id; }
	public String getName() { return name; }
	public Date getCreatedAt() { return createdAt; }
	public Date getDeletedAt() { return deletedAt; }
	public int getCourseId() { return courseId; }
	
}
