package csv_classes;

public class StaffAssignment {
	private int userId;
	private int courseId;
	
	public StaffAssignment(int userId, int courseId) {
		this.userId = userId;
		this.courseId = courseId;
	}
	
	public int getUserId() { return userId; }
	public int getCourseId() { return courseId; }
	
}
