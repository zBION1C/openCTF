package dao;

import java.util.*;
import java.io.*;
import java.sql.*;
import user.UserBean;
import ctf.CtfBean;
import hint.HintBean;

public class Dao {
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/openCTF", "nicholas", "Nicholas_Montana8");
        return connection;
    }

    public static boolean validate(UserBean user, String username, String password) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	
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

    public static void register(String username, String password) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	
    	PreparedStatement addUser = connection.prepareStatement("INSERT INTO Utente (username, password, data) VALUES (?, ?, NOW())");

        addUser.setString(1, username);
        addUser.setString(2, password);
        
        addUser.executeUpdate();

        addUser.close();
    }

    public static List<CtfBean> getCTF() throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	
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
    	
    	connection.close();
    	
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
    	
    	getCtf.close();
    	connection.close();	
    	
    	return ctf;
    }
    
    public static List<HintBean> getHints(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement getHints = connection.prepareStatement("SELECT * FROM Indizi WHERE ctf = ?");
    	
    	getHints.setInt(1, id);
    	
    	ResultSet response = getHints.executeQuery();
    	
    	List<HintBean> hints = new ArrayList<HintBean>();
    	
    	while (response.next()) {
    		HintBean hint = new HintBean();
    		hint.setId(response.getInt(1));
    		hint.setTesto(response.getString(2));
    		hint.setCtf(response.getInt(3));
    		
    		hints.add(hint);
    	}
    	
    	getHints.close();
    	connection.close();
    	
    	return hints;
    }
    
    public static int getResolvers(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement resolvers = connection.prepareCall("{? = call NumberResolver(?)}");
    	
    	resolvers.registerOutParameter(1, Types.INTEGER);
    	
    	
    	resolvers.setInt(2, id);
    	    	
    	resolvers.execute();
    	
    	int n = resolvers.getInt(1);
    	
    	resolvers.close();
    	connection.close();
    	
    	return n;
    }
    
    public static int getAttempts(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement attempts = connection.prepareCall("{? = call NumberAttempts(?)}");
    	
    	attempts.registerOutParameter(1, Types.INTEGER);
    
    	attempts.setInt(2, id);
    	    	
    	attempts.execute();
    
    	int m = attempts.getInt(1);
    	
    	attempts.close();
    	connection.close();
    	
    	return m;
    }
}









