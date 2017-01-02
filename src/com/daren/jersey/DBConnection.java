package com.daren.jersey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
public class DBConnection {
    /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constants.dbClass);
            con = DriverManager.getConnection(Constants.dbUrl, Constants.dbUser, Constants.dbPwd);
            System.out.println("Inside createconn"+con);
        } catch (Exception e) {
        	System.out.println("EXCEPTION " + e.toString());
            throw e;
        } finally {
            return con;
        }
    }
    /**
     * Method to check whether uname and pwd combination are correct
     * 
     * @param uname
     * @param pwd
     * @return
     * @throws SQLException 
     * @throws Exception
     */
    public static boolean checkUserName(String uname) throws SQLException
    {
    	boolean userNameAvaliable = true;
    	
    	Connection dbConn = null;
    	
    	try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM user WHERE username = '" + uname + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                userNameAvaliable = false;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
    	return userNameAvaliable;
    }
    /**
     * Method to insert uname and pwd in DB
     * 
     * @param name
     * @param uname
     * @param pwd
     * @param reg_date 
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static boolean insertUser(String uname,String email, String pwd, String reg_date) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
         
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into user(username,email, hash_password,register_date) values('" + uname
            		+ "','" + email + "','" + pwd  +"','"+reg_date + "')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
    public static String[] getUserData(String email) throws SQLException
    {
    	String[] data = null;
    	Connection dbConn = null;
    	
    	try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            data = new String[4];
            String query = "SELECT userID,username,hash_password,register_date FROM user WHERE email = '" + email + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(2));
            	
            	data[0] = 	rs.getString(1);
            	data[1]	=	rs.getString(2);
            	data[2]	=	rs.getString(3);
            	data[3] =  	rs.getString(4);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
		return data;
    	
    }
	public static boolean checkEmail(String email) throws SQLException {
		boolean emailIsUsed = false;
		Connection dbConn = null;
    	
    	try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM user WHERE email = '" + email + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                emailIsUsed = true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return emailIsUsed;
		
	}
	public static boolean checkColor(String color, String gid) throws SQLException {
		boolean colorIsFree = true;
		Connection dbConn = null;
    	
    	try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM gameUser WHERE color = '" + color +" AND gameID = "+ gid +"'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                colorIsFree = false;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return colorIsFree;
		
	}
	 public static boolean createRoom(int adminID, String roomName, String password,int maxPlayers) throws SQLException, Exception {
	        boolean insertStatus = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DBConnection.createConnection();
	               // System.out.println("Create connection 0"+dbConn);
	            } catch (Exception e) {
	                
	                  e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            
	            String query = "INSERT into room(adminID,name,password,max_players) values('"
	            + adminID + "','"+ roomName+"','"+ password  + "','" + maxPlayers  + "')";
	            //System.out.println(query);
	            int records = stmt.executeUpdate(query);
	            //System.out.println(records);
	            //When record is successfully inserted
	            if (records > 0) {
	                insertStatus = true;
	            }
	        } catch (SQLException sqle) {
	            //sqle.printStackTrace();
	            throw sqle;
	        } catch (Exception e) {
	            //e.printStackTrace();
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return insertStatus;
	    }
	 public static boolean createGame(int roundAmount,int maxWord,int status) throws SQLException, Exception {
	        boolean insertStatus = false;
	        Connection dbConn = null;
	        try {
	            try {
	                dbConn = DBConnection.createConnection();
	                //System.out.println("Create connection 0"+dbConn);
	            } catch (Exception e) {
	                
	                  e.printStackTrace();
	            }
	         
	            Statement stmt = dbConn.createStatement();
	            String query = "INSERT into game(round_amount,max_word,status) values('"
	            + roundAmount + "','" + maxWord  + "','" + status + "')";
	            //System.out.println(query);
	            int records = stmt.executeUpdate(query);
	            //System.out.println(records);
	            //When record is successfully inserted
	            if (records > 0) {
	                insertStatus = true;
	            }
	        } catch (SQLException sqle) {
	            //sqle.printStackTrace();
	            throw sqle;
	        } catch (Exception e) {
	            //e.printStackTrace();
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return insertStatus;
	    }
	 public static String selectGameID() throws SQLException
	 {
		 String gameID ="";
			Connection dbConn = null;
	    	
	    	try {
	            try {
	                dbConn = DBConnection.createConnection();
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            Statement stmt = dbConn.createStatement();
	            String query = "SELECT max(gameID) FROM game";
	            //System.out.println(query);
	            ResultSet rs = stmt.executeQuery(query);
	            while (rs.next()) {
	                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
	                gameID = rs.getString(1);
	            }
	        } catch (SQLException sqle) {
	            throw sqle;
	        } catch (Exception e) {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	            throw e;
	        } finally {
	            if (dbConn != null) {
	                dbConn.close();
	            }
	        }
	        return gameID;
		 
	 }
	public static boolean insertGameUser(int userID, int gameID, int colorI) throws SQLException {
		boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
         
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into gameUser(gameID,userID,color) values('" + gameID
            		+ "','" + userID + "','" + colorI  + "')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
		
	}
	public static ArrayList<String> selectCurrentMatches() throws SQLException {
		ArrayList<String> roomData = null;
    	Connection dbConn = null;
    	
    	try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            Statement stmtinside = dbConn.createStatement();
            roomData = new ArrayList<String>();
            String roomAmount ="";
            String query = "SELECT count(*) FROM matches join room on matches.roomID = room.roomID WHERE matches.status = 1";
            ResultSet rs = stmt.executeQuery(query);
            		while (rs.next()) {
                        //System.out.println(rs.getString(2));
                    	roomAmount=rs.getString(1);
                    	roomData.add(roomAmount);
                    	System.out.println(roomAmount);
                    }
            
            query = "SELECT matches.gameID,room.name, room.max_players, room.password FROM matches join room on matches.roomID = room.roomID WHERE matches.status = 1";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(2));
            	
            	roomData.add(rs.getString(1));	
            	roomData.add(rs.getString(2));
            	roomData.add(rs.getString(3));
            	roomData.add(rs.getString(4));
            	   String query2 = "SELECT count(user.userID) FROM gameUser JOIN user on gameUser.userID = user.userID where gameUser.gameID ='"+ rs.getString(1)+"'";
               	ResultSet rsinside = stmtinside.executeQuery(query2);
                   while(rsinside.next())
                   {
                   	roomData.add(rsinside.getString(1));
                   }
            	            	
            }
         
            System.out.println(roomData);
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
		return roomData;
		
	}
	public static ArrayList<User> selectUserFromRoom(int gameID) throws SQLException {
		ArrayList<User> users = null;
    	Connection dbConn = null;
    	
    	try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            users = new ArrayList<User>();
            Statement stmt = dbConn.createStatement();
            String query = "SELECT user.userID,user.username,user.email FROM user join gameUser on gameUser.userID = user.userID where gameUser.gameID= '" + gameID + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(2));
            	User user = new User();
            	user.setUserID(rs.getInt(1));
            	user.setUserName(rs.getString(2));
            	user.setEmail(rs.getString(3));
            	users.add(user);
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
		
		return users;
	}
}