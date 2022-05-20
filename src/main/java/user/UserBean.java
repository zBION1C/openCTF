package user;

public class UserBean {
	private String username;
	private String password;

	public UserBean() {}

	public void setUsername(String x) {
		username = x;
	}

	public void setPassword(String x) {
		password = x;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}