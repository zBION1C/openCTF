package file;

public class FileBean {
	private String name;
	private Integer ctf;

	public FileBean() {}

	public void setName(String x) {
		name = x;
	}

	public void setCtf(Integer x) {
		ctf = x;
	}
	
	public String getName() {
		return name;
	}

	public Integer getCtf() {
		return ctf;
	}
}