package dao;

import java.util.*;
import java.io.*;
import java.sql.*;
import user.UserBean;
import ctf.CtfBean;
import hint.HintBean;
import comment.CommentBean;
import file.FileBean;

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
            user.setDate(result.getString(3));
        }

        getUser.close();
        connection.close();

        return state;
    }

    public static void register(String username, String password) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	
    	PreparedStatement addUser = connection.prepareStatement("INSERT INTO Utente (username, password, data) VALUES (?, ?, NOW())");

        addUser.setString(1, username);
        addUser.setString(2, password);
        
        addUser.executeUpdate();

        addUser.close();
        connection.close();
    }
    
    public static String getUserDate(String username) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
        PreparedStatement getUser = connection.prepareStatement("SELECT Utente.data FROM Utente WHERE username = ?");
    
        getUser.setString(1, username);

        ResultSet result = getUser.executeQuery();
        result.next();

        String date = result.getString(1);

        getUser.close();
        connection.close();
        
        return date;
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
    	
    	statement.close();
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
    
    public static void addHint(String testo, Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement res = connection.prepareStatement("INSERT INTO Indizi (testo, ctf) VALUES (?, ?)");

        res.setString(1, testo);
        res.setInt(2, id);
        
        res.executeUpdate();

        res.close();
        connection.close();
    }
    
    public static List<CommentBean> getComments(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement getComm = connection.prepareStatement("SELECT * FROM Commenti WHERE ctf = ?");
    	
    	getComm.setInt(1, id);
    	
    	ResultSet response = getComm.executeQuery();
    	
    	List<CommentBean> comms = new ArrayList<CommentBean>();
    	
    	while (response.next()) {
    		CommentBean comm = new CommentBean();
    		comm.setId(response.getInt(1));
    		comm.setTs(response.getString(2));
    		comm.setTesto(response.getString(3));
    		comm.setUtente(response.getString(4));
    		comm.setCtf(response.getInt(5));
    		
    		comms.add(comm);
    	}
    	
    	getComm.close();
    	connection.close();
    	
    	return comms;
    }
    
    public static List<FileBean> getFiles(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement getF = connection.prepareStatement("SELECT * FROM Files WHERE ctf = ?");
    	
    	getF.setInt(1, id);
    	
    	ResultSet response = getF.executeQuery();
    	
    	List<FileBean> files = new ArrayList<FileBean>();
    	
    	while (response.next()) {
    		FileBean f = new FileBean();
    		f.setName(response.getString(1));
    		f.setCtf(response.getInt(2));
    		
    		files.add(f);
    	}
    	
    	getF.close();
    	connection.close();
    	
    	return files;
    }
    
    public static void putComment(String testo, String utente, Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement put = connection.prepareStatement("INSERT INTO Commenti (ts, testo, utente, ctf) VALUES (NOW(), ?, ?, ?)");
    	
    	put.setString(1, testo);
    	put.setString(2, utente);
    	put.setInt(3, id);
    	
    	put.executeUpdate();
    	
    	connection.close();
    	put.close();
    }
    
    public static void resolved(String utente, Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement put = connection.prepareStatement("INSERT INTO Risolte (utente, ctf, ts) VALUES (?, ?, NOW())");
    	
    	put.setString(1, utente);
    	put.setInt(2, ctf);

    	put.executeUpdate();
    	
    	connection.close();
    	put.close();
    }
    
    public static Boolean alreadyResolved(String utente, Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement res = connection.prepareCall("{? = call AlreadyResolved(?, ?)}");
    	
    	res.registerOutParameter(1, Types.BOOLEAN);
    	
    	res.setInt(2, ctf);
    	res.setString(3, utente);
    	
    	res.execute();

    	Boolean b = res.getBoolean(1); 
    	
    	connection.close();
    	res.close();
    
    	return b;
    }
    
    public static Boolean alreadyAttempted(String utente, Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement res = connection.prepareCall("{? = call AlreadyAttempted(?, ?)}");
    	
    	res.registerOutParameter(1, Types.BOOLEAN);
    	
    	res.setInt(2, ctf);
    	res.setString(3, utente);
    	
    	res.execute();
    	
    	Boolean b = res.getBoolean(1); 
    	
    	connection.close();
    	res.close();
    
    	return b;
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
    
    public static int getUserAttempts(String username) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement attempts = connection.prepareCall("{? = call UserAttemptedCtf(?)}");
    	
    	attempts.registerOutParameter(1, Types.INTEGER);
    	
    	attempts.setString(2, username);
    	attempts.execute();
    	
    	int m = attempts.getInt(1);
    	
    	attempts.close();
    	connection.close();
    	
    	return m;
    }
    
    public static int getUserPoints(String username) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement points = connection.prepareCall("{? = call UserPoints(?)}");
    		
    	points.registerOutParameter(1, Types.INTEGER);
    	
    	points.setString(2, username);
    	points.execute();
    	
    	int m = points.getInt(1);
    	
    	points.close();
    	connection.close();
    	
    	return m;
    }
    
    public static int getUserPointsCat(String username, String cat) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement points = connection.prepareCall("{? = call UserPointsCat(?, ?)}");
    	
    	points.registerOutParameter(1, Types.INTEGER);
    	
    	points.setString(2, username);
    	points.setString(3, cat);
    	points.execute();
    	
    	int m = points.getInt(1);
    	
    	points.close();
    	connection.close();
    	
    	return m;
    }
    
    public static int getUserResolved(String username) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement points = connection.prepareCall("{? = call UserResolvedCtfs(?)}");
    	
    	points.registerOutParameter(1, Types.INTEGER);
    	
    	points.setString(2, username);
    	points.execute();
    	
    	int m = points.getInt(1);
    	
    	points.close();
    	connection.close();
    	
    	return m;
    }
    
    public static int getUserResolvedCat(String username, String cat) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement points = connection.prepareCall("{? = call UserResolvedCtfsCat(?, ?)}");
    	
    	points.registerOutParameter(1, Types.INTEGER);
    	
    	points.setString(2, username);
    	points.setString(3, cat);
    	points.execute();
    	
    	int m = points.getInt(1);
    	
    	points.close();
    	connection.close();
    	
    	return m;	
    }
    
    public static int getFirstBloods(String username) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement points = connection.prepareCall("{? = call NumberFirstResolver(?)}");
    	
    	points.registerOutParameter(1, Types.INTEGER);
    	
    	points.setString(2, username);
    	points.execute();
    	
    	int m = points.getInt(1);
    	
    	points.close();
    	connection.close();
    	
    	return m;	
    }
    
    public static int addCTF(String title, Integer diff, String descr, String flag, String user, String cat) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement put = connection.prepareStatement("INSERT INTO CTF (titolo, difficolta, data, descrizione, flag, utente, categoria) VALUES (?, ?, NOW(), ?, ?, ?, ?)");
    	
    	put.setString(1, title);
    	put.setInt(2, diff);
    	put.setString(3, descr);
    	put.setString(4, flag);
    	put.setString(5, user);
    	put.setString(6, cat);
    	
    	put.executeUpdate();
    	
    	Statement getId = connection.createStatement();
    	ResultSet response = getId.executeQuery("SELECT LAST_INSERT_ID();");
    	response.next();
    	int id = response.getInt(1);
        	
    	put.close();
    	getId.close();
    	connection.close();
    	
    	return id;
    }
    
    public static void addFile(String nome, Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement put = connection.prepareStatement("INSERT INTO Files VALUES (?, ?)");
    	
    	put.setString(1, nome);
    	put.setInt(2, ctf);
    	
    	put.executeUpdate();
    	
    	put.close();
    	connection.close();	
    }
    
    public static void updateAttempts(String user, Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement put = connection.prepareStatement("INSERT INTO Prova VALUES (?, ?)");
    	
    	put.setString(1, user);
    	put.setInt(2, ctf);
    	
    	put.executeUpdate();
    	
    	put.close();
    	connection.close();
    	
    }
}
