package com.zmy.zahomy_app.service.task;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zmy.zahomy_app.model.Item;
import com.zmy.zahomy_app.model.ItemLibrary;
import com.zmy.zahomy_app.util.DatabaseHandler;
import com.zmy.zahomy_app.util.Log;
import com.zmy.zahomy_app.util.StreamUtils;

		public class GetItemsTask extends  Activity implements Runnable {
			// A reference to retrieve the data when this task finishes
			public static String json;
		    public static final String LIBRARY = "LessonsLibrary";
		    // A handler that will be notified when the task is finished
		    private final Handler replyTo;
		    // The user we are querying on server for lessons
		    
		    private String email;
		    private String Url;
		    //private static String MainURL = "http://10.0.2.2/items_data2.php";
		    //private static String MainURL = "http://www.zahomy.com/app/deliveries.php";
		    private static String MainURL = "http://syncsoft.co.ke/zahomy_data.php";
		    /**http://www.zahomy.com/app/deliveries.php
		     * Don't forget to call run(); to start this task
		     * @param replyTo - the handler you want to receive the response when this task has finished
		     * @param username - the username of who on YouTube you are browsing
		     */
		    public GetItemsTask(Handler replyTo,  String email, String userPassword) {
		        this.replyTo = replyTo;
		        this.email = email;
		       
		        	this.Url = MainURL;  //+ "/mobile_lessons_list.php?day_id="+Leo;
		        	//this.Url = "https://www.ratibar.com/app/appLessonsList.php?email="+ email + "&password="+ userPassword + "&dy="+Day_id;
		        	
		        }
		        
		       
		    
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// Get a httpclient to talk to the internet
		            HttpClient client = new DefaultHttpClient();
		            // Perform a GET request to YouTube for a JSON list of all the videos by a specific user
		            HttpUriRequest request = new HttpGet(Url);
		            Log.i("Loging on to fetch data");									  
		            // Get the response that YouTube sends back
		            HttpResponse response = client.execute(request);
		            // Convert this response into a readable string
		            String jsonString = StreamUtils.convertToString(response.getEntity().getContent());
		            json=jsonString;
		            //if Yes
		            //check if data exists in dbase
					//fetch from dbase
		            // Create a JSON object that we can use from the String
		            Log.i("jsonString_____"+jsonString);
		            
		            JSONObject json = new JSONObject(jsonString);
		            Bundle data = new Bundle();
		            
		           JSONObject varObj = null;
		         try { 
		        	  json = new JSONObject(jsonString); 
		        	  varObj = json.getJSONObject("data");
		        	// JSONArray   jsonArray = json.getJSONArray("data");
		        	 Log.i("jsonArray past");
		        	    //getJSONArray("lessons");
		        	} 
		        	catch (JSONException ignored) {}
		            
		           if (varObj != null) {
		            	// Get are search result items
		           JSONArray jsonArray = json.getJSONObject("data").getJSONArray("watches");            
			            
			            // Create a list to store are videos in
			            List<Item> items = new ArrayList<Item>();
			            // Loop round our JSON list of lessons creating Lesson objects to use within our app
			           Log.i("Item Array....."+jsonArray);             
		            	for (int i = 0; i < jsonArray.length(); i++) {            
			                JSONObject jsonObject = jsonArray.getJSONObject(i);	
			                
			                int order_id = Integer.parseInt(jsonObject.getString("order_id"));
			                String product_id = jsonObject.getString("product_id");
			                String clientname = jsonObject.getString("clientname");
			                String phoneno = jsonObject.getString("phoneno");
			                String itemname = jsonObject.getString("itemname");
			                String itemprice = jsonObject.getString("itemprice");
			                String itemtime = jsonObject.getString("delivery_date");
			                String location = jsonObject.getString("location");
			                String itemimage_url = jsonObject.getString("thumbnail");
			                String status_image_url = "@drawable/pending";
			                
                           /*
			                "order_id": "1606",
			                "product_id": "200",
			                "clientname": "Job Eregae",
			                "email": "ereguy.86@gmail.com",
			                "phoneno": "0725999569",
			                "itemname": "CL-ARMY Male luxury watch grey face",
			                "itemprice": "2999",
			                "itemtime": "00",
			                "quantity": "1",
			                "delivery_date": "1434315600",
			                "location": "Lodwar",
			                "thumbnail": "http:\/\/www.zahomy.com\/images\/watches\/items\/256\/cl-army-black.jpg"
			                // Create the video object and add it to our list*/
			               items.add(new Item(order_id,product_id,clientname,phoneno,itemname,itemprice,itemtime,location,itemimage_url,status_image_url));
			               
//Collections.sort(items);

		            	
		            	}
		            	
		            	 Log.i("Item Array....."+items); 
		            	
		            	  // Create a library to hold our lessons
		 	            ItemLibrary lib = new ItemLibrary("br", items);
		 	            // Pack the Library into the bundle to send back to the Activity	            
		 	            data.putSerializable(LIBRARY, lib);
		             /*} else {
		             	LessonLibrary lib = new LessonLibrary("br", null);
		 	            data.putSerializable(LIBRARY, lib);
		             }*/
		             // Send the Bundle of data (our LessonLibrary) back to the handler (our Activity)
		             Message msg = Message.obtain();
		             msg.setData(data);
		             replyTo.sendMessage(msg);
		             // We don't do any error catching, just nothing will happen if this task falls over
		             // an idea would be to reply to the handler with a different message so your Activity can act accordingly
		            }
		 		} catch (ClientProtocolException e) {
		             Log.e("Feck", e);
		         } catch (IOException e) {
		             Log.e("Feck", e);
		         } catch (JSONException e) {
		             Log.e("Feck", e);
		         }
		 	}

		 }
