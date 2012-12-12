package alexandru.ciocea;

public class UserInformation {
	
	private String userId = "";
	private String username = "";
	
	private static UserInformation instance = null;
	
	//constructor
	
	private UserInformation(){
		
	}
	
	public static UserInformation getInstance(){
		
		if(instance == null){
			instance = new UserInformation();
		}
		return instance;
	}
	
	//getter and setter
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
