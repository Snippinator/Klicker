package alexandru.ciocea;

public class Execution {

	private int id = 0;
	private int userId = 0;
	private int activityId = 0;
	private String timestamp = "";

	public Execution() {

	}

	public Execution(int id, int userId, int activityId, String timestamp) {

		this.id = id;
		this.userId = userId;
		this.activityId = activityId;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
