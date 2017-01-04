package com.daren.jersey;

import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/register

@Path("/register")
public class Register {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/register/doregister
    @Path("/doregister")  
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/register/doregister?name=pqrs&username=abc&password=xyz
    public String doRegister(@QueryParam("username") String uname,@QueryParam("email") String email, @QueryParam("password") String pwd,@QueryParam("register_date") String reg_date){
        String response = "";
       // System.out.println("Inside doLogin "+uname+"  "+pwd+" "+reg_date);
        int retCode = registerUser(uname, email,pwd,reg_date);
        if(retCode == 0){
            response = Utility.constructJSON("register",true);
        }else if(retCode == 1){
            response = Utility.constructJSON("register",false, "This username is already used");
        }else if(retCode == 2){
            response = Utility.constructJSON("register",false, "Special Characters are not allowed in Username and Password");
        }else if(retCode == 3){
        	response = Utility.constructJSON("register",false, "You are already registered on this email.");
        }else if(retCode == 4){
        	response = Utility.constructJSON("register",false, "Error occured");
        }
        return response;
 
    }
 
    private int registerUser(String uname,String email, String pwd, String reg_date){
        System.out.println("Inside registerUser");
        int result = 4;
        if(Utility.isNotNull(uname) && Utility.isNotNull(pwd) && Utility.isNotNull(reg_date)){
            try {
            	// when user is already exist - uname is used 
            	if(DBConnection.checkUserName(uname) == false)
            	{
            		result = 1;
            	}else if(DBConnection.checkEmail(email)==true)
            	{
            		result = 3;
            	}
            	else if(DBConnection.insertUser( uname, email, pwd, reg_date)){
                     result = 0;
                }
            } catch(SQLException sqle){
                System.out.println("RegisterUSer catch sqle");
              
                //When special characters are used in name,username or password
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
            System.out.println("Inside checkCredentials,some parameter is null");
            result = 4;
        }
 
        return result;
    }
 
}