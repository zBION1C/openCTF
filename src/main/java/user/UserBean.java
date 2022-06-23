package user;

public class UserBean {
	private String username;
	private String password;
	private String date;
	private Integer points;
	private Boolean admin;
	private Boolean banned;

	public UserBean() {}
	
	public void setUsername(String x) {
		username = x;
	}

	public void setPassword(String x) {
		password = x;
	}
	
	public void setDate(String x) {
		date = x;
	}
	
	public void setPoints(Integer x) {
		points = x;
	}
	
	public void setAdmin(Boolean x) {
		admin = x;
	}

	public void setBanned(Boolean x) {
		banned = x;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getDate() {
		return date;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public Boolean getAdmin() {
		return admin;
	}
	
	public Boolean getBanned() {
		return banned;
	}
}