package hint;

public class HintBean {
	private Integer id;
	private String testo;
	private Integer ctf;

	public HintBean() {}

	public void setId(Integer x) {
		id = x;
	}

	public void setTesto(String x) {
		testo = x;
	}
	
	public void setCtf(Integer x) {
		ctf = x;
	}

	public Integer getId() {
		return id;
	}
	
	public String getTesto() {
		return testo;
	}

	public Integer getCtf() {
		return ctf;
	}
}