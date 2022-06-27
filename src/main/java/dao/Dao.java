package dao;

import java.util.*;
import java.io.*;
import java.sql.*;
import user.UserBean;
import ctf.CtfBean;
import hint.HintBean;
import comment.CommentBean;
import file.FileBean;
import writeup.WriteupBean;

public class Dao {
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/openCTF", "user", "");
        return connection;
    }

    /*===========================================================================================================================================================*/
    
    // Validate: used to authenticate a user on login
    public static boolean validate(UserBean user, String username, String password) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	
    	Boolean state = false;
        PreparedStatement getUser = connection.prepareStatement("SELECT * FROM Utente WHERE username = ? AND password = ? AND banned = false");
    
        getUser.setString(1, username);
        getUser.setString(2, password);

        ResultSet result = getUser.executeQuery();
        state = result.next();

        if (state) {
            user.setUsername(result.getString(1));
            user.setPassword(result.getString(2));
            user.setAdmin(result.getBoolean(3));
            user.setDate(result.getString(5));
        }

        getUser.close();
        connection.close();

        return state;
    }
    
    //Ban: used to permanently ban a user from the application
    public static void ban(String username) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement rem = connection.prepareStatement("UPDATE Utente SET username = ?, banned = true WHERE username=? AND banned = false;");
    	
    	rem.setString(1, username+" (Banned)");
    	rem.setString(2, username);
    	
    	rem.executeUpdate();
    	rem.close();
    	connection.close();
    }

    //Register: used when registering a new user
    public static void register(String username, String password) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	
    	PreparedStatement addUser = connection.prepareStatement("INSERT INTO Utente (username, password, admin, banned, data) VALUES (?, ?, ?, ?, NOW())");

        addUser.setString(1, username);
        addUser.setString(2, password);
        addUser.setBoolean(3, false);
        addUser.setBoolean(4, false);
        
        addUser.executeUpdate();

        addUser.close();
        connection.close();
    }
    
    //getUser: when called, return the UserBean associated with the username passed as argument
    public static UserBean getUser(String name) throws SQLException, ClassNotFoundException { 
    	Connection connection = Dao.connect();
    	PreparedStatement getU = connection.prepareStatement("SELECT username, data, admin, banned FROM Utente WHERE username = ?");
    	
    	getU.setString(1, name);
    	
    	ResultSet response = getU.executeQuery();
    	
    	UserBean user = new UserBean();
    	
    	while (response.next()){
    		user.setUsername(response.getString(1));
    		user.setDate(response.getString(2));
    		user.setAdmin(response.getBoolean(3));
    		user.setBanned(response.getBoolean(4));
    	}
    	
    	getU.close();
    	connection.close();
    	
    	return user;
    }
    
    public static List<UserBean> getUsers(String name) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement getS = connection.prepareStatement("SELECT username FROM Utente WHERE username LIKE ?");
    	
    	getS.setString(1, name + "%");
    	
    	ResultSet response = getS.executeQuery();
    	
    	List<UserBean> sc = new ArrayList<UserBean>();
    	
    	while (response.next()) {
    		UserBean u = new UserBean();
    		u.setUsername(response.getString(1));
    		
    		sc.add(u);
    	}
    	
    	getS.close();
    	connection.close();
    	
    	return sc;
    }

    //getUserAttempts: return the number of total attempts for a given user
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
    
  //getUserPoints: return the number of total points that a user has
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
    
    //getUserPointsCat: return the number of points that a user has for a given category
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
    
    //getUserResolved: return the number of CTF resolved by a given user
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
    
    //getUserResolvedCat: return the number of CTF of a given category resolved by a given user
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
    
    //getFirstBloods: return the number of 'firstBloods' a user has
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
    
    /*===========================================================================================================================================================*/
    
    //addCTF: when called, add a CTF to the database
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
    
    //removeCTF: when called, remove a CTF from the database
    public static void removeCTF(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement rem = connection.prepareStatement("DELETE FROM CTF WHERE id=?;");
    	
    	rem.setInt(1, id);
    	
    	rem.executeUpdate();
    	rem.close();
    	connection.close();
    }
    
    //getCTF: when called return the list of all CTF in the database ordered as specified in the argument 'orderby', match on at least the
    // username of the creator or the title of the CTF with the argument 'search', or match with the filter 'filterDif' and 'filterCat' specified.
    public static List<CtfBean> getCTF(String orderby, String search, String filterDif, String filterCat) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	
    	PreparedStatement getCtf;
    	
    	if (orderby == null) {
    		orderby = "data";
    	}
    	
    	if (filterDif == null) {
    		filterDif = "null";
    	}
    	
    	if (filterCat == null) {
    		filterCat = "null";
    	}
    	
    	if (search != null) {
    		getCtf = connection.prepareStatement("SELECT * FROM CTF WHERE titolo LIKE ? OR utente LIKE ?");
    		getCtf.setString(1, search + "%");
    		getCtf.setString(2, search + "%");
    	} else if (!filterDif.equals("null") && !filterCat.equals("null")) {
    		getCtf = connection.prepareStatement("SELECT * FROM CTF WHERE difficolta = ? AND categoria = ?");
    		getCtf.setString(1, filterDif);
    		getCtf.setString(2, filterCat);
    	} else if (!filterDif.equals("null")) {
    		getCtf = connection.prepareStatement("SELECT * FROM CTF WHERE difficolta = ?");
    		getCtf.setString(1, filterDif);
    	} else if (!filterCat.equals("null")) {
    		getCtf = connection.prepareStatement("SELECT * FROM CTF WHERE categoria = ?");
    		getCtf.setString(1, filterCat);
    	} else {
    		getCtf = connection.prepareStatement("SELECT * FROM CTF ORDER BY " + orderby);
    	}
    	
    	ResultSet response = getCtf.executeQuery();
    	
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
    	
    	getCtf.close();
    	connection.close();
    	
    	return ctfs;
    }
    
    //getCtfById: when called return the CtfBean associated with a CTF id.
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
    
    //addHint: add a hint of a CTF in the database
    public static void addHint(String testo, Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement res = connection.prepareStatement("INSERT INTO Indizi (testo, ctf) VALUES (?, ?)");

        res.setString(1, testo);
        res.setInt(2, id);
        
        res.executeUpdate();

        res.close();
        connection.close();
    }
    
    //getHints: return the list of all the hints associated with a CTF
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
    
    //puComment: add a comment of a user for a given CTF
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
    
    //getComments: return the list of all comments associated with a CTF 
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
    
    //addFile: when called, add a file for a given CTF to the database
    public static void addFile(String nome, Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement put = connection.prepareStatement("INSERT INTO Files VALUES (?, ?)");
    	
    	put.setString(1, nome);
    	put.setInt(2, ctf);
    	
    	put.executeUpdate();
    	
    	put.close();
    	connection.close();	
    }
    
    //getFiles: return the list of all the files associated with a CTF
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
    
    //addWriteup: add a writeup into the database
    public static void addWriteup(String name, String user, Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement addW = connection.prepareStatement("INSERT INTO Writeup (nome, utente, ctf, ts) VALUES (?, ?, ?, NOW())");
    	
    	addW.setString(1, name);
    	addW.setString(2, user);
    	addW.setInt(3, id);
    	
    	addW.executeUpdate();
    	
    	addW.close();
    	connection.close();
    }
    
    //removeWriteup: when called, delete a writeup from the database associated to a ctf.
    public static void removeWriteup(Integer ctf, String username) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement rem = connection.prepareStatement("DELETE FROM Writeup WHERE ctf=? AND utente=? ;");
    	
    	rem.setInt(1, ctf);
    	rem.setString(2, username);
    	
    	rem.executeUpdate();
    	rem.close();
    	connection.close();
    }
    
    //getWriteups: return the list of all the writeups associated with a CTF
    public static List<WriteupBean> getWriteups(Integer id) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement getW = connection.prepareStatement("SELECT * FROM Writeup WHERE ctf = ?");
    	
    	getW.setInt(1, id);
    	
    	ResultSet response = getW.executeQuery();
    	
    	List<WriteupBean> ws = new ArrayList<WriteupBean>();
    	
    	while (response.next()) {
    		WriteupBean w = new WriteupBean();
    		w.setName(response.getString(1));
    		w.setUser(response.getString(2));
    		w.setCtf(response.getInt(3));
    		w.setTs(response.getString(4));
    		
    		ws.add(w);
    	}
    	
    	getW.close();
    	connection.close();
    	
    	return ws;
    }
    
    public static List<UserBean> getScoreboard() throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement getS = connection.prepareStatement("SELECT username, UserPoints(Utente.username) FROM Utente ORDER BY UserPoints(Utente.username) DESC");
    	
    	ResultSet response = getS.executeQuery();
    	
    	List<UserBean> sc = new ArrayList<UserBean>();
    	
    	while (response.next()) {
    		UserBean u = new UserBean();
    		u.setUsername(response.getString(1));
    		u.setPoints(response.getInt(2));
    		
    		sc.add(u);
    	}
    	
    	getS.close();
    	connection.close();
    	
    	return sc;
    }
    
    //resolved: update the database inserting that a user resolved a CTF 
    public static void resolved(String utente, Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	PreparedStatement put = connection.prepareStatement("INSERT INTO Risolte (utente, ctf, ts) VALUES (?, ?, NOW())");
    	
    	put.setString(1, utente);
    	put.setInt(2, ctf);

    	put.executeUpdate();
    	
    	connection.close();
    	put.close();
    }
    
    //alreadyResolved: return if a given user has already resolved a given CTF
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
    
    //alreadyAttempted: return if a given user has already attempted a given CTF
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
    
    //getResolvers: return the number of users that resolved a given CTF
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
    
    //getAttempts: return the number of attempts for a given CTF
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
    
    //getFirstBlood: return the username of the 'firstBlood' for a given CTF
    public static String getFirstBlood(Integer ctf) throws SQLException, ClassNotFoundException {
    	Connection connection = Dao.connect();
    	CallableStatement fst = connection.prepareCall("{? = call FirstResolver(?)}");
    	
    	fst.registerOutParameter(1, Types.VARCHAR);
    	
    	fst.setInt(2, ctf);
    	fst.execute();
    	
    	String u = fst.getString(1);
    	
    	fst.close();
    	connection.close();
    	
    	return u;
    }
    
    //updateAttempts: when called, update the attempts of a given user for a given CTF
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
