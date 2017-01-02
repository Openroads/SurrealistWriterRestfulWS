package com.daren.jersey;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/createroom")
public class Room {
	@GET
    @Path("/docreateroom")  
    @Produces(MediaType.APPLICATION_JSON)
    public String doCreateRoom(@QueryParam("room_name") String name,@QueryParam("max_num_players") String numPlayers, @QueryParam("num_characters") String numCharacters ,@QueryParam("num_rounds") String numRounds,@QueryParam("password") String pwd,@QueryParam("user_id") String adminID){
        String response = "";
        
        int retCode = createRoomAndGame(adminID, name, pwd, numPlayers,numRounds,numCharacters);
        if(retCode == 0){
            response = Utility.constructJSON("createRoom",true);
        }else if(retCode == 1){
            response = Utility.constructJSON("createRoom",false, "Problem with creating new room");
        }else if(retCode == 2){
            response = Utility.constructJSON("createRoomAndGame",false, "Special Characters in passed data are not allowed ");
        }else if(retCode == 3){
        	response = Utility.constructJSON("createGame",false, "Problem with creating new room. ");
        }else if(retCode == 4){
        	response = Utility.constructJSON("createRoom",false, "Error occured");
        }else if(retCode ==5){
        	response = Utility.constructJSON("createRoomAndGame", false, "Some parameter is empty");
        }
        
        return response;
	}
	private int createRoomAndGame(String admin_ID,String roomName,String password, String max_players, String num_rounds, String num_characters)
	{
		int result=4;
		
		if(Utility.isNotNull(admin_ID) && Utility.isNotNull(roomName) && Utility.isNotNull(max_players) && Utility.isNotNull(num_rounds)&& Utility.isNotNull(num_characters)){
            try {
            	int roomStatus = 1;
            	int maxPlayers = Integer.parseInt(max_players);
            	int adminID = Integer.parseInt(admin_ID);
            	if(DBConnection.createRoom(adminID, roomName, password, maxPlayers,roomStatus) == false)
            	{
            		result = 0;
            	}else{
            		result = 1;
            	}
            		
            	int gameStatus =1;
            	int	maxWord = Integer.parseInt(num_characters);
            	int numRounds = Integer.parseInt(num_rounds);
   
            	if(DBConnection.createGame(numRounds,maxWord,gameStatus)==true)
            	{
            		result = 0;
            	}else{
            		result = 3;
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
