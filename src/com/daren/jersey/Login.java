package com.daren.jersey;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
//Path: http://localhost/<appln-folder-name>/login
@Path("/login")
public class Login {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/login/dologin
    @Path("/dologin")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    public String getLogDate(@QueryParam("email") String email)
    {
    	System.out.println("request /login/dologin");
    	String response="";
    	    	
    	if(Utility.isNotNull(email)){
    		String[] data = null;
			try {
				data = DBConnection.getUserData(email);
				if(data[0] != null){
					JSONObject obj = new JSONObject();
		            try {
		            	obj.put("tag", "Login/getData");
		            	obj.put("status",true);
		            	obj.put("username",data[1]);
		            	obj.put("hash_pwd", data[2]);
		                obj.put("email",email);
		                obj.put("userid", data[0]);
		            	obj.put("register_date",data[3]);
		            	
		            } catch (JSONException e) {
		               //System.out.println(e.toString());
		            }
		            
		            response = obj.toString();
		            //System.out.println("eee"+response);
				}else{
					response = Utility.constructJSON("Login/getData", false,"Wrong email address or password.\nCorrect data or create a new account if you don't have.");
				}
				
			} catch (SQLException e1) {
					e1.printStackTrace();
			}
    		
           
    	}else{
    		response = Utility.constructJSON("Login/getData", false,"Empty email! ");
    	}
		return response;	
    	
    }
 
}