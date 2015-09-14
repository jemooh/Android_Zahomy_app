package com.zmy.zahomy_app.util;
		 
		import java.util.ArrayList;
import java.util.List;
		 
		
		


		import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
		 
		
		
		//import com.zmy.zahomy_app.src.LoginActivity;
		


import android.content.Context;
		 
		public class ServerInteractions {
		 
		    private JSONParser jsonParser;
		   
		    private static String loginURL = "https://www.ratibar.com/app/appRegLogin.php";
		    private static String transURL = "http://dev.ratibar.com/app/appRegLogin.php?";
		    private static String feedbackURL = "http://dev.ratibar.com/app/appRegLogin.php?";
		    private static String login_tag = "login";
		    private static String feedback_tag = "feedback";
		    private static String trans_tag = "login";
		   
		    
		    // constructor
		    public ServerInteractions(){
		        jsonParser = new JSONParser();
		    }
		 
		    /**
		     * function make Login Request
		     * @param email
		     * @param password
		     * */
		    public JSONObject loginUser(String email, String password){
		        // Building Parameters
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("tag", login_tag));
		        params.add(new BasicNameValuePair("email", email));
		        params.add(new BasicNameValuePair("password", password));
		        //Log.i("Details--:"+email+ " "+password);
		        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		        
		        return json;
		    }
		    
		    
		    
		    /**
		     * function make transaction save Request
		     * @param comments
		     * */
		    public JSONObject postTransactionDetails(String transDetails, String date){
		        // Building Parameters
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("tag", trans_tag));
		        params.add(new BasicNameValuePair("transDetails", transDetails));
		        params.add(new BasicNameValuePair("date", date));
		        
		        // getting JSON Object
		        JSONObject json = jsonParser.getJSONFromUrl(transURL, params);
		        // return json
		        return json;
		    }
		    
		    /**
		     * function make comment save Request
		     * @param comments
		     * */
		    public JSONObject postFeedback(String feedback, String email){
		        // Building Parameters
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("tag", feedback_tag));
		        params.add(new BasicNameValuePair("feedback", feedback));
		        params.add(new BasicNameValuePair("email", email));
		        
		        // getting JSON Object
		        JSONObject json = jsonParser.getJSONFromUrl(feedbackURL, params);
		        // return json
		        return json;
		    }
		 
		 
		    /**
		     * Function get Login status
		     * */
		    public boolean isUserLoggedIn(Context context){
		        DatabaseHandler db = new DatabaseHandler(context);
		        int count = db.getRowCount();
		        if(count > 0){
		            // user logged in
		            return true;
		        }
		        return false;
		    }
		 
		    /**
		     * Function to logout user
		     * Reset Database
		     * */
		    public boolean logoutUser(Context context){
		        DatabaseHandler db = new DatabaseHandler(context);
		        db.resetTables();
		        return true;
		    }
		 
		}