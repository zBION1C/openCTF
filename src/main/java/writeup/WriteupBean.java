package writeup;

public class WriteupBean {
	private String name;
	private String user;
	private Integer ctf;
	private String ts;
	
	public WriteupBean() {}
	
	public void setName(String x) {
		name = x;
	}
	
	public void setUser(String x) {
		user = x;
	}
	
	public void setCtf(Integer x) {
		ctf = x;
	}
	
	public void setTs(String x) {
		ts = x;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUser() {
		return user;
	}
	
	public Integer getCtf() {
		return ctf;
	}
	
	public String getTs() {
		return ts;
	}

}
