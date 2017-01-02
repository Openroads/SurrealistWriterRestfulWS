package com.daren.jersey;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
@Path("/color")
public class Color {
	  @GET
	  @Path("/checkcolor")
	  public String checkColor(@QueryParam("color") String color,@QueryParam("game_id") String gid)
	  {
		  String response="";
	    	
	    	if(Utility.isNotNull(color) && Utility.isNotNull(gid)){
	    		
				try {
					if(DBConnection.checkColor(color,gid)){
						JSONObject obj = new JSONObject();
			            try {
			            	obj.put("tag", "CheckColor");
			            	obj.put("status",true);
			            				            	
			            } catch (JSONException e) {
			               //System.out.println(e.toString());
			            }
			            
			            response = obj.toString();
			            //System.out.println("eee"+response);
					}else{
						response = Utility.constructJSON("CheckColor", false,"Color is busy.");
					}
					
				} catch (SQLException e1) {
						e1.printStackTrace();
				}
	    		
	           
	    	}else{
	    		response = Utility.constructJSON("CheckColor", false,"Empty color or game id! ");
	    	}
			return response;
		  
	  }
}
