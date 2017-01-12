package com.daren.jersey;

import java.sql.SQLException;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;



@Path("/game")
public class GamePlay {
	
	@GET
	@Path("/startgame")
	@Produces(MediaType.APPLICATION_JSON)
	public String startGame(@QueryParam("game_id") String gid)
	{
		System.out.println("request /game/startgame");
		String response="";
		if(Utility.isNotNull(gid)){
            try {
            	
            	int gameID = Integer.parseInt(gid);
            	if(DBConnection.changeGameStatus(gameID) == true)
            	{
            		response = Utility.constructJSON("startGame",true);
            	}else{
            		response = Utility.constructJSON("startGame",false, "Game is not exist");
            	}
            		
            } catch(SQLException sqle){
                System.out.println("startGame catch sqle");
              
                //When special characters are used 
                if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    response = Utility.constructJSON("startGame",false, "Special Characters are");
                }else{
                	System.out.println(sqle.toString());
                }
            }catch (Exception e) {
               	e.printStackTrace();
                System.out.println("Inside startGame catch e "+e.toString());
                response = Utility.constructJSON("startGame",false, "Error occured");
            }
        }else{
            System.out.println("Inside jointogame,some parameter is null");
            response = Utility.constructJSON("startGame",false, "Some parameter is empty");
        }
		return response;

	}
	//TODO BACK HETE
	@GET
	@Path("/whonextturn")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkNextUser(@QueryParam("user_id") String uid ,@QueryParam("game_id") String gid)
	{
		System.out.println("request /game/whonextturn");
		
		String response="";
		if(Utility.isNotNull(gid) && Utility.isNotNull(gid)){
            try {
            	
            	int gameID = Integer.parseInt(gid);
            	int userID = Integer.parseInt(uid);
            	
            	
  
	            	if(DBConnection.checkIfUserTurn(gameID,userID) == true)
	            	{
	            		
	            		Game game = DBConnection.selectGame(gameID);
	            		if(game != null){
	            			if(game.getStatus()==0)
	            			{
			            		JSONObject firstobj = new JSONObject();
			                	firstobj.put("tag", "whonextturn");
			                	firstobj.put("status", true);
			                	firstobj.put("kind",1);
			                	firstobj.put("last_words", game.getLastTwoWords());
			                	firstobj.put("current_round", game.getCurrentRound());
			                	firstobj.put("color", game.getLastColor());
			                	firstobj.put("max_round",game.getRoundAmount());
			                	System.out.println("IN WHONEXT"+game.getMax_word());
			                	firstobj.put("max_characters",game.getMax_word());
			                	response = firstobj.toString();
	            			}else if(game.getStatus()==1){
	            				response = Utility.constructJSON("whonextturn",false, "Game is not running xD");
	            			}else if(game.getStatus() == 2)
	            			{
	            				JSONObject firstobj = new JSONObject();
			                	firstobj.put("tag", "whonextturn");
			                	firstobj.put("status", true);
			                	firstobj.put("kind", 0);
			                	firstobj.put("curent_text", game.getCurrent_text());
			                	firstobj.put("color_text",game.getColor_text());
			                	//System.out.println("IN WHONEXT"+game.getMax_word());
			                	
			                	response = firstobj.toString();
	            			}
	            		}else
	            		{
	            			response = Utility.constructJSON("whonextturn",false, "wrong in object send game null");
	            		}
	            		
	            	}else{
	            		Game game = DBConnection.selectGame(gameID);
	            		if(game.getStatus() == 2)
            			{
            				JSONObject firstobj = new JSONObject();
		                	firstobj.put("tag", "whonextturn");
		                	firstobj.put("status", true);
		                	firstobj.put("kind", 0);
		                	firstobj.put("curent_text", game.getCurrent_text());
		                	firstobj.put("color_text",game.getColor_text());
		                	//System.out.println("IN WHONEXT"+game.getMax_word());
		                	
		                	response = firstobj.toString();
            			}
	            		
	            		response = Utility.constructJSON("whonextturn",false, "It is not your turn");
	            	}
            		
            } catch(SQLException sqle){
                System.out.println("whonextturn catch sqle");
              
                //When special characters are used 
                if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    response = Utility.constructJSON("whonextturn",false, "Special Characters used in data");
                }else{
                	System.out.println(sqle.toString());
                }
            }catch (Exception e) {
               	e.printStackTrace();
                System.out.println("Inside whonextturn catch e "+e.toString());
                response = Utility.constructJSON("whonextturn",false, "Error occured");
            }
        }else{
            System.out.println("Inside whonextturn,some parameter is null");
            response = Utility.constructJSON("whonextturn",false, "Some parameter is empty");
        }
		return response;

	}
	
	@GET
	@Path("/nextturn")
	@Produces(MediaType.APPLICATION_JSON)
	public String nextTurn(@QueryParam("user_id") String uid ,@QueryParam("game_id") String gid,@QueryParam("color") String col,@QueryParam("words") String words)
	{
		System.out.println("request /game/nextTurn");
		
		String response="";
		if(Utility.isNotNull(gid) && Utility.isNotNull(gid)&& Utility.isNotNull(col)){
            try {
            	
            	int gameID = Integer.parseInt(gid);
            	int userID = Integer.parseInt(uid);
            	int color =  Integer.parseInt(col);
            	if(DBConnection.updateGame(gameID,userID,color,words) == true)
            	{
            		response = Utility.constructJSON("nextTurn",true);
            	}else{
            		response = Utility.constructJSON("nextTurn",false, "Cannot update database");
            	}
            		
            } catch(SQLException sqle){
                System.out.println("nextTurn catch sqle");
              
                //When special characters are used 
                if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    response = Utility.constructJSON("nextTurn",false, "Special Characters used in data");
                }else{
                	System.out.println(sqle.toString());
                }
            }catch (Exception e) {
               	e.printStackTrace();
                System.out.println("Inside nextTurn catch e "+e.toString());
                response = Utility.constructJSON("nextTurn",false, "Error occured");
            }
        }else{
            System.out.println("Inside nextTurn,some parameter is null");
            response = Utility.constructJSON("nextTurn",false, "Some parameter is empty");
        }
		return response;

		
	}
	
	@GET
	@Path("/gamestatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String checkGameStatus(@QueryParam("game_id") String gid)
	{
		System.out.println("request /game/gamestatus");
		
		String response="";
		if(Utility.isNotNull(gid)){
            try {
            	
            	int gameID = Integer.parseInt(gid);
   
            	if(DBConnection.selectGameStatus(gameID) == true)
            	{
            		response = Utility.constructJSON("gamestatus",true);
            	}else{
            		response = Utility.constructJSON("gamestatus",false, "Game is not started !");
            	}
            		
            } catch(SQLException sqle){
                System.out.println("gamestatus catch sqle");
              
                //When special characters are used 
                if(sqle.getErrorCode() == 1064){
                    System.out.println(sqle.getErrorCode());
                    response = Utility.constructJSON("gamestatus",false, "Special Characters used in data");
                }else{
                	System.out.println(sqle.toString());
                }
            }catch (Exception e) {
               	e.printStackTrace();
                System.out.println("Inside gamestatus catch e "+e.toString());
                response = Utility.constructJSON("gamestatus",false, "Error occured");
            }
        }else{
            System.out.println("Inside gamestatus,some parameter is null");
            response = Utility.constructJSON("gamestatus",false, "Some parameter is empty");
        }
		return response;
		
	}
	
}
	

