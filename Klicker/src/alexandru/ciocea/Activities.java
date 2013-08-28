package alexandru.ciocea;

public class Activities {
	
	private int id = 0;
	private int userId = 0;
	private String definition = "";
	private int duration = 0;
	private int subunits = 0;

	public Activities(){
		
	}
	
	public Activities(int id, int userId, String definition, int duration, int subunits){
		
		this.id = id;
		this.userId = userId;
		this.definition = definition;
		this.duration = duration;
		this.subunits = subunits;
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

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getSubunits() {
		return subunits;
	}

	public void setSubunits(int subunits) {
		this.subunits = subunits;
	}
	
	
	
}
