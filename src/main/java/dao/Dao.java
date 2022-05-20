package dao;

import java.util.*;
import java.io.*;
import java.sql.*;
import user.UserBean;
import ctf.CtfBean;

public class Dao {
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/openCTF", "nicholas", "Nicholas_Montana8");
        return connection;
    }

    public static boolean validate(Connection connection, UserBean user, String username, String password) throws SQLException, ClassNotFoundException {
        Boolean state = false;
        PreparedStatement getUser = connection.prepareStatement("SELECT * FROM Utente WHERE username = ? AND password = ?");
    
        getUser.setString(1, username);
        getUser.setString(2, password);

        ResultSet result = getUser.executeQuery();
        state = result.next();

        if (state) {
            user.setUsername(result.getString(1));
            user.setPassword(result.getString(2));
        }

        getUser.close();

        return state;
    }

    public static void register(Connection connection, String username, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement addUser = connection.prepareStatement("INSERT INTO Utente (username, password, data) VALUES (?, ?, NOW())");

        addUser.setString(1, username);
        addUser.setString(2, password);
        
        addUser.executeUpdate();

        addUser.close();
    }

    public static List<CtfBean> getCTF(Connection connection) throws SQLException, ClassNotFoundException {
    	Statement statement = connection.createStatement();
    	String getCTF = "SELECT * from CTF";
    	ResultSet response = statement.executeQuery(getCTF);
    	
    	List<CtfBean> ctfs = new ArrayList<CtfBean>();
    	
    	while(response.next()) {
    		CtfBean ctf = new CtfBean();
    		ctf.setId(response.getInt(1));
    		ctf.setTitle(response.getString(2));
    		ctf.setDifficulty(response.getInt(3));
    		ctf.setDate(response.getString(4));
    		ctf.setDescription(response.getString(5));
    		ctf.setFlag(response.getString(6));
    		ctf.setCreator(response.getString(7));
    		ctf.setCategory(response.getString(8));
    		
    		ctfs.add(ctf);
    	}
    	
    	return ctfs;
    }
    
    public static CtfBean getCtfById(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement getCtf = connection.prepareStatement("SELECT * FROM CTF WHERE id = ?");
    	
    	getCtf.setInt(1, id);
    	
    	ResultSet response = getCtf.executeQuery();
    	
    	CtfBean ctf = new CtfBean();
    	
    	while (response.next()) {
    		ctf.setId(response.getInt(1));
    		ctf.setTitle(response.getString(2));
    		ctf.setDifficulty(response.getInt(3));
    		ctf.setDate(response.getString(4));
    		ctf.setDescription(response.getString(5));
    		ctf.setFlag(response.getString(6));
    		ctf.setCreator(response.getString(7));
    		ctf.setCategory(response.getString(8));
    	}
    	
    	return ctf;
    }
    


}