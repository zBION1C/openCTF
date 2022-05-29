package comment;

public class CommentBean {
	Integer id;
	String ts;
	String testo;
	String utente;
	Integer ctf;
	
	public void setId(Integer x) {
		id = x;
	}
	
	public void setTs(String x) {
		ts = x;
	}
	
	public void setTesto(String x) {
		testo = x;
	}
	
	public void setUtente(String x) {
		utente = x;
	}
	
	public void setCtf(Integer x) {
		ctf = x;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getTs() {
		return ts;
	}
	
	public String getTesto() {
		return testo;
	}
	
	public String getUtente() {
		return utente;
	}
	
	public Integer getCtf() {
		return ctf;
	}
	
	
}