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
   /* // Query parameters are parameters: http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
    public String doLogin(@QueryParam("username") String uname, @QueryParam("password") String pwd){
        String response = "";
        if(checkCredentials(uname, pwd)){
            response = Utility.constructJSON("login",true);
        }else{
            response = Utility.constructJSON("login", false, "Incorrect Email or Password");
        }
    return response;        
    }
    @GET
    @Path("/getreg_date")
    @Produces(MediaType.APPLICATION_JSON) */
    public String getLogDate(@QueryParam("email") String email)
    {
    	String response="";
    	    	
    	if(Utility.isNotNull(email)){
    		String[] data = null;
			try {
				data = DBConnection.getUserData(email);
				if(data != null){
					JSONObject obj = new JSONObject();
		            try {
		            	obj.put("tag", "Login/getData");
		            	obj.put("status",true);
		            	obj.put("username",data[0]);
		            	obj.put("hash_pwd", data[1]);
		                obj.put("email",email);
		            	obj.put("register_date",data[2]);
		            	
		            } catch (JSONException e) {
		               //System.out.println(e.toString());
		            }
		            
		            response = obj.toString();
		            //System.out.println("eee"+response);
				}else{
					response = Utility.constructJSON("Login/getData", false," Incorrect email. Correct email address or create new account if you don't have yet.");
				}
				
			} catch (SQLException e1) {
					e1.printStackTrace();
			}
    		
           
    	}else{
    		response = Utility.constructJSON("Login/getData", false,"Empty email! ");
    	}
		return response;	
    	
    }
 
    /**
     * Method to check whether the entered credential is valid
     * 
     * @param uname
     * @param pwd
     * @return
     */
    private boolean checkCredentials(String uname, String pwd){
        //System.out.println("Inside checkCredentials");
        boolean result = false;
        if(Utility.isNotNull(uname) && Utility.isNotNull(pwd)){
            try {
                result = DBConnection.checkLogin(uname, pwd);
                //System.out.println("Inside checkCredentials try "+result);
            } catch (Exception e) {
               //System.out.println("Inside checkCredentials catch");
                result = false;
            }
        }else{
            //System.out.println("Inside checkCredentials else");
            result = false;
        }
 
        return result;
    }
 
}