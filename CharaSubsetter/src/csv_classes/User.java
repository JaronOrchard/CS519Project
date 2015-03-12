package csv_classes;

public class User {
	private int id;
	private String netId;
	
	public User(int id, String netId) {
		this.id = id;
		this.netId = netId;
	}
	
	public int getId() { return id; }
	public String getNetId() { return netId; }
	
}
