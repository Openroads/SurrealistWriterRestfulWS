package com.daren.jersey;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


@Path("/room")
public class Room {
	@GET
    @Path("/docreateroom")  
    @Produces(MediaType.APPLICATION_JSON)
    public String doCreateRoom(@QueryParam("room_name") String name,@QueryParam("max_num_players") String numPlayers, @QueryParam("num_characters") String numCharacters ,@QueryParam("num_rounds") String numRounds,@QueryParam("password") String pwd,@QueryParam("user_id") String adminID){
        String response = "";
        
        int retCode = createRoomAndGame(adminID, name, pwd, numPlayers,numRounds,numCharacters);
        if(retCode == 0){
        	JSONObject obj = new JSONObject();
            try {
            	String gameID = DBConnection.selectGameID();
                obj.put("tag", "createRoom");
                obj.put("status", true);
                obj.put("gameId", gameID);
                response = obj.toString();
            } catch (JSONException e) {
            	System.out.println("JSNON creating"+toString());
            }catch(SQLException sqle){
                System.out.println("createRoomAndGame catch sqle");
                if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    response = Utility.constructJSON("createRoomAndGame",false, "Special Characters in passed data are not allowed ");
                }else{
                	response = Utility.constructJSON("createRoom",false, "Error occured");
                	System.out.println(sqle.toString());
                }
            };
            
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
	@GET
    @Path("/getroom")  
    @Produces(MediaType.APPLICATION_JSON)
	public String getCurrentRoom()
	{
		String response = "";
		JSONArray roomsJSON ;
		JSONObject  roomJSONObj = null;
		ArrayList<String> data =new ArrayList<String>();
		try {
			data = DBConnection.selectCurrentMatches();
			if(data.get(0) != null){
	            try {
	            	roomsJSON = new JSONArray();
	            	int roomAmount = Integer.parseInt(data.get(0));
	            	JSONObject firstobj = new JSONObject();
	            	firstobj.put("tag", "CurrentRoom");
	            	firstobj.put("status", true);
	            	firstobj.put("room_amount", roomAmount);
	            	for(int i =1;i<=roomAmount*5;i+=5){
					JSONObject obj = new JSONObject();
	            	
	            	
	            	int roomMode=1;
	            	if(data.get(i+3).isEmpty())
	            		roomMode = 0;
	            		obj.put("game_id", data.get(i));
	            	obj.put("room_name",data.get(i+1));
	            	obj.put("max_places",data.get(i+2));
	            	obj.put("occupied_places", data.get(i+4));
	            	obj.put("mode",roomMode);
	            	roomsJSON.put(obj);
	            	
	            	}
	            	
	            	roomJSONObj = new JSONObject();
	            	roomJSONObj.put("Info",firstobj);
		            roomJSONObj.put("RoomsArray", roomsJSON);
	            	 /* 
	            	//amount,gameid,roomname,maxplayers,password,occupiedplaces
	            	*/
	            	
	            } catch (JSONException e) {
	               //System.out.println(e.toString());
	            }
	            
	            
	            response = roomJSONObj.toString();
	            //System.out.println("eee"+response);
			}else{
				response = Utility.constructJSON("CurrentRoom", false,"Wrong data,any room exists");
			}
			
		} catch (SQLException e1) {
				e1.printStackTrace();
		}
		return response;
		
	}
	
	private int createRoomAndGame(String admin_ID,String roomName,String password, String max_players, String num_rounds, String num_characters)
	{
		int result=4;
		
		if(Utility.isNotNull(admin_ID) && Utility.isNotNull(roomName) && Utility.isNotNull(max_players) && Utility.isNotNull(num_rounds)&& Utility.isNotNull(num_characters)){
            try {
            	
            	int maxPlayers = Integer.parseInt(max_players);
            	int adminID = Integer.parseInt(admin_ID);
            	if(DBConnection.createRoom(adminID, roomName, password, maxPlayers) == false)
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
