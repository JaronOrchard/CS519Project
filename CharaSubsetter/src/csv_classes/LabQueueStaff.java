package csv_classes;

import java.util.Date;

public class LabQueueStaff {
	private int id;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	private int userId;
	private int labQueueId;
	
	public LabQueueStaff(int id, Date createdAt, Date updatedAt, Date deletedAt, int userId, int labQueueId) {
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.userId = userId;
		this.labQueueId = labQueueId;
	}
	
	public int getId() { return id; }
	public Date getCreatedAt() { return createdAt; }
	public Date getUpdatedAt() { return updatedAt; }
	public Date getDeletedAt() { return deletedAt; }
	public int getUserId() { return userId; }
	public int getLabQueueId() { return labQueueId; }
	
}
