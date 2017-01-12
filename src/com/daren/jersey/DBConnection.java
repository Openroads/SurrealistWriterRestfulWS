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
            System.out.println("Connection with mysql: createconnection"+con);
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
               
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM gameUser WHERE color = '" + color+ "'"+" AND  gameID = " + "'" + gid + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
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
	
	public static boolean changeGameStatus(int gameID) throws SQLException {
		boolean gameChangeStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
            
            Statement stmt = dbConn.createStatement();
            
            String query = "SELECT gameUser.UserID FROM gameUser WHERE gameUser.gameID = '" + gameID + "'";
            int startUser = 0;
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                //System.out.println(rs.getString(2));
            	startUser = rs.getInt(1);
            }else
            {
            	gameChangeStatus = false;
            }
            
            
            
            query = "UPDATE game set game.status = 0, game.nextUserID = '"+  startUser +"' WHERE game.gameID = '" + gameID +  "'";
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                gameChangeStatus = true;
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
        return gameChangeStatus;
	}
	
	public static boolean updateGame(int gameID, int userID, int color, String words) throws SQLException {
		boolean gameUpdateStatus = false;
		String current_text="";
		String color_text = "";
		int  current_round = 0;
		int round_amount = 0;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
         
            Statement stmt = dbConn.createStatement();
            String query = "SELECT game.current_text,game.color_text,game.current_round,game.round_amount FROM game where game.gameID= '" + gameID + "'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(2));
            	current_text	= rs.getString(1);
            	color_text 		= rs.getString(2);
            	current_round 	= rs.getInt(3);
            	round_amount 	= rs.getInt(4);
            	
                }
            System.out.println("Pierdy "+current_round +"" +current_text+""+ color_text);
            current_text = current_text + "@" + words;
            color_text = color_text +"@"+ color;
            
            System.out.println("Pierdy "+current_round +"" +current_text+""+ color_text);
            int records =0;
           /*
            System.out.println(query);
            int records = stmt.executeUpdate(query);
            */
            /** set new user in turn  **/
            ArrayList<Integer> users = new ArrayList<Integer>();
            query = "SELECT gameUser.userID FROM gameUser WHERE gameUser.gameID = '" + gameID + "'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(2));
            	users.add(rs.getInt(1));
            	
            }
            int uNumber = users.size();
            int nextUser = 0;
            for(int i =0; i< uNumber ;i++)
            {
            	if(users.get(i) == userID && i != uNumber-1)
            	{
            		nextUser = users.get(i+1);
            	}
            	else
            	{
            		nextUser = users.get(0);
            		current_round = current_round+1;
            		//TODO nie jestem pewny
            		if(round_amount + 1 == current_round)
            		{
            			query = "UPDATE game set game.status = 2 WHERE game.gameID = '" + gameID +  "'";
                        System.out.println(query);
                        records = stmt.executeUpdate(query);
                        query  = "UPDATE matches set matches.status = 0 WHERE matches.gameID = '" + gameID +  "'";
                        records = stmt.executeUpdate(query);
            		}
            		
            	}
            	System.out.println("Next user:"+nextUser);
            }
            query = "UPDATE game set game.current_text = '"+ current_text  +"',game.color_text = '"+ color_text +"',game.current_round = '"+ current_round +"',game.nextUserID = '"+ nextUser +"' WHERE game.gameID = '" + gameID +  "'";
            System.out.println(query);
            records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
            	gameUpdateStatus = true;
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
        return gameUpdateStatus;
	}
	
	public static boolean selectGameStatus(int gameID) throws SQLException {
		boolean checkGameStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
            
            Statement stmt = dbConn.createStatement();
            
            String query = "SELECT game.status FROM game WHERE game.gameID = '" + gameID + "'";
            
            ResultSet rs = stmt.executeQuery(query);
            
            int gameStatus =-1;
            if (rs.next()) {
                //System.out.println(rs.getString(2));
            	gameStatus = rs.getInt(1);
            }
            if(gameStatus == 0)
            {
            	checkGameStatus = true;
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
        return checkGameStatus;
	}
	public static Game selectGame(int gameID) throws SQLException
	{
		Game game;
		String lastWords="";
		int color=0;
		String lastColors="";
		Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
            game = new Game();
            Statement stmt = dbConn.createStatement();
            
            String query = "SELECT * FROM game WHERE game.gameID = '" + gameID + "'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            
            String current_text="";
            if (rs.next()) {
                //System.out.println(rs.getString(2));
            	game.setGameID(rs.getInt(1));
            	
            	current_text = rs.getString(3);
            	game.setCurrent_text(current_text);
            	lastColors = rs.getString(4);
            	game.setColor_text(lastColors);
            	game.setCurrentRound(rs.getInt(5));
            	game.setRoundAmount(rs.getInt(6));
            	game.setMax_word(rs.getInt(7));
            	game.setStatus(rs.getInt(8));
            	System.out.println("wyjebujemy"+game.getGameID());
            }
           
            
            //TODO do sprawdzenia
            System.out.println("MAX WORD IN CONNECTION " + game.getMax_word());
            if(current_text == null || current_text.isEmpty())
            {
            	lastWords = "";
            }else
            {
            	 String[] s2 = current_text.split("@");
            	 String[] s3 = s2[s2.length-1].split(" ");
                 String word1;
                 String word2;
                 if(s3.length == 1){
                   word1 = "";
                   word2 = s3[s3.length-1];
                 }
                 else{
                   word1 = s3[s3.length-2];
                   word2 = s3[s3.length-1];
                 }
                 System.out.println(word1+" "+word2);
                 lastWords =word1 +" " + word2;
		                          	
		        }
		        if(lastColors == null || lastColors.isEmpty())
		        {
		        	color=0;
		        }else
		        {
		            String[] col = lastColors.split("@");
		            String colo  = col[col.length-1];
		            System.out.println(colo);
		            color = Integer.parseInt(colo);
		            game.setLastColor(color);
		            game.setLastTwoWords(lastWords);
		        	
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
		return game;
	}
	public static boolean checkIfUserTurn(int gameID, int userID) throws SQLException {
		boolean checkMyTurn = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
            
            Statement stmt = dbConn.createStatement();
            
            String query = "SELECT game.nextUserID FROM game WHERE game.gameID = '" + gameID + "'";
            
            ResultSet rs = stmt.executeQuery(query);
            
            int nextUser =-1;
            if (rs.next()) {
                //System.out.println(rs.getString(2));
            	nextUser = rs.getInt(1);
            }
            System.out.println(nextUser);
            if(nextUser == userID)
            {
            	checkMyTurn = true;
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
        return checkMyTurn;
	}
	
	//TODO BACK TO THIS
	public static boolean changeRoomAndGameStatus(int gameID) throws SQLException {
		boolean changingStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
                //System.out.println("Create connection 0"+dbConn);
            } catch (Exception e) {
                
                  e.printStackTrace();
            }
            
            Statement stmt = dbConn.createStatement();
         
                     
            String query = "UPDATE game set game.status = 2 WHERE game.gameID = '" + gameID +  "'";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            query  = "UPDATE matches set matches.status = 0 WHERE matches.gameID = '" + gameID +  "'";
            records = stmt.executeUpdate(query);
            if (records > 0) {
            	changingStatus = true;
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
        return changingStatus;
		
	}
}