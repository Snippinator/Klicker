package alexandru.ciocea;

public class UserInformation {
	
	private int userId;
	private String username = "";
	private String email = "";
	
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
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
