package ctf;
import java.sql.SQLException;

import dao.Dao;
import user.UserBean;

public class CtfBean {
	Integer id;
	String title;
	Integer difficulty;
	String date;
	String description;
	String flag;
	String creator;
	String category;
	
	public CtfBean() {}
	
	public Boolean validateFlag(String flag, UserBean utente) throws SQLException, ClassNotFoundException {
		String username = utente.getUsername();
		
		if (!Dao.alreadyAttempted(username, this.id)) {
			Dao.updateAttempts(username, this.id);
		}
		if (this.flag.equals(flag)) {
			Dao.resolved(username, this.id);
			return true;
		} else {
			return false;
		}
	}
	
	public void setId(Integer x) {
		id = x;
	}
	public void setTitle(String x) {
		title = x;
	}
	public void setDifficulty(Integer x) {
		difficulty = x;
	}
	public void setDate(String x) {
		date = x;
	}
	public void setDescription(String x) {
		description = x;
	}
	public void setFlag(String x) {
		flag= x;
	}
	public void setCreator(String x) {
		creator = x;
	}
	public void setCategory(String x) {
		category = x;
	}
	
	public Integer getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public String getDate() {
		return date;
	}
	public String getDescription() {
		return description;
	}
	public String getFlag() {
		return flag;
	}
	public String getCreator() {
		return creator;
	}
	public String getCategory() {
		return category;
	}
}