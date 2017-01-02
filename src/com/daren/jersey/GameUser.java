package com.daren.jersey;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/gameuser")
public class GameUser {
	@GET
    @Path("/creategameuser")  
    @Produces(MediaType.APPLICATION_JSON)
	public  String createGameUser(@QueryParam("user_id") String uid,@QueryParam("game_id") String gid,@QueryParam("color") String color)
	{
		String response = "";
	       
	        int retCode = insertGameUser(uid,gid,color);
	        if(retCode == 0){
	            response = Utility.constructJSON("createGameUser",true);
	        }else if(retCode == 2){
	            response = Utility.constructJSON("createGameUser",false, "Special Characters are not allowed in Username and Password");
	        }else if(retCode == 4){
	        	response = Utility.constructJSON("createGameUser",false, "Error occured");
	        }else if(retCode == 5){
	        	response = Utility.constructJSON("createGameUser",false, "Some parameter is empty");
	        }
	        return response;
	}

	private int insertGameUser(String uid, String gid, String color) {
		int result=4;
		if(Utility.isNotNull(uid) && Utility.isNotNull(gid)&& Utility.isNotNull(color))
		{
				try {
            	
            	int userID = Integer.parseInt(uid);
            	int gameID = Integer.parseInt(gid);
            	int colorI = Integer.parseInt(color);
            	if(DBConnection.insertGameUser(userID,gameID,colorI) == true)
            	{
            		result = 0;
            	}
            	
               	
            } catch(SQLException sqle){
                System.out.println("createRoomAndGame catch sqle");
              
                //When special characters are used 
                if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    result = 2;
                }else{
                	System.out.println(sqle.toString());
                }
            }catch (Exception e) {
               	e.printStackTrace();
                System.out.println("Inside checkCredentials catch e "+e.toString());
                result = 4;
            }
			
		}else{
            System.out.println("Inside createRoomAndGame,some parameter is null");
            result = 5;
        }
		return result;
	}

}
