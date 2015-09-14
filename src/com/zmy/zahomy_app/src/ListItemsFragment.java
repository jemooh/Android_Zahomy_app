package com.zmy.zahomy_app.src;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment.SavedState;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zmy.zahomy_app.adapter.ItemsAdapter;
import com.zmy.zahomy_app.listener.ItemClickListener;
import com.zmy.zahomy_app.listener.ItemLongClickListener;
import com.zmy.zahomy_app.model.Item;
import com.zmy.zahomy_app.model.ItemLibrary;
import com.zmy.zahomy_app.service.task.GetItemsTask;
import com.zmy.zahomy_app.widget.ItemsListView;
import com.zmy.zahomy_app.R;

@SuppressLint("HandlerLeak")

		public class ListItemsFragment extends Fragment implements ItemClickListener,ItemLongClickListener {
			// if run on phone, isSinglePane = true
			 // if run on tablet, isSinglePane = false
	         //implements ItemClickListener
	        private ItemsAdapter ItemAdapter;
			private ItemsListView listView;
			public static String  dayId,phoneNo,pos;
			int today = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);
		    String Day_id,dayTitle;
			static String parseNull(Object obj){
		        return obj == null?"null"  : "Object";
		    }
			private static final int ENTER_DATA_REQUEST_CODE = 1;
			 private List<Item> items;
			int mCurCheckPosition = 0;
			String[] daysOfWeek = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", };
		
		    // ListFragment is a very useful class that provides a simple ListView inside of a Fragment.
		    // This class is meant to be sub-classed and allows you to quickly build up list interfaces
		    // in your app.
		    @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		    	Log.d("FragmentCycle===>", "onCreateView: bundle="+parseNull(savedInstanceState));
		    	View view=inflater.inflate(R.layout.list_items, container, false);
		       
		  		Calendar c = Calendar.getInstance();
			    SimpleDateFormat currentDate2 = new SimpleDateFormat("MMM/dd");
		  		String currentDate = currentDate2.format(c.getTime());
		  	    String Day_id = String.valueOf(today-1);
		        Intent in=getActivity().getIntent();
		        String dyT = null;
		      
		        	//this.dayId = today+"";
		        	dayId = String.valueOf(today-1);	
		        	dayTitle = daysOfWeek[today];
		        	dyT = dayTitle + ", " + currentDate;
		       
		        
		        TextView txtDateToday = (TextView) view.findViewById(R.id.txtDateToday);
		  		txtDateToday.setText(dyT);
		  		
		       
		    	
				
				
				//create a listview to hold data
		        listView = (ItemsListView) view.findViewById(R.id.todayListView);
				
				// Here we are adding this activity as a listener for when any row in the List is 'clicked'
		        // The activity will be sent back the video that has been pressed to do whatever it wants with
		        // in this case we will retrieve the URL of the video and fire off an intent to view it
		       listView.setOnItemClickListener(this);
		       listView.setOnItemLongClickListener(this);
		       
		       
		        getItemsFeed(listView);
		       
		        registerForContextMenu(listView);
		        
		        return view;
		    }
		 
			
	
			
			
			
			// This is the XML onClick listener to retreive a users video feed
		    public void getItemsFeed(View v){
		        // We start a new task that does its work on its own thread
		        // We pass in a handler that will be called when the task has finished
		        // We also pass in the name of the user we are searching YouTube for
		    	
		    			//Log.i("current Server Lessons", Day_id);
		    	  new Thread(new GetItemsTask(responseHandler,   null,null)).start();
		    	}
		   
		   
		    // This is the handler that receives the response when the YouTube task has finished
		
		    @SuppressLint("HandlerLeak")
			Handler responseHandler = new Handler() {
		        public void handleMessage(Message msg) {
		        	populateListWithItems(msg);
		        };
		    };
		   
		 
		    
		    /**
		     * This method retrieves the Library of videos from the task and passes them to our ListView
		     * @param msg
		     */
		    private void populateListWithItems(Message msg) {
		        // Retreive the videos are task found from the data bundle sent back
		        ItemLibrary lib = (ItemLibrary) msg.getData().get(GetItemsTask.LIBRARY);
		        // Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		        // we can just call our custom method with the list of items we want to display
		        ProgressBar pbpp = (ProgressBar) getView().findViewById(R.id.pbppl);
		        TextView txtMsg = (TextView) getView().findViewById(R.id.progressMsg2);
		        listView.setItems(lib.getItems());
				if(lib.getItems().isEmpty()){
					txtMsg.setText("No Items for today, take a break");	
					pbpp.setVisibility(View.GONE);
				} else {
					pbpp.setVisibility(View.GONE);
					txtMsg.setVisibility(View.GONE);
					items = lib.getItems();
			        listView.setItems(items);
				}
		    
		    
		    }
		    
		    @Override
		    public void onItemClicked(Item Item,int position){
		    	showDetails(Item, position);
		    	mCurCheckPosition = position;
		    	  //pos =items.get(position);
		    	//setItemChecked(position, true);
		    	Log.i("clicked pos",""+mCurCheckPosition);
		    	
		    }
		    	
		     	
		   void showDetails(Item item,int position){
		    	//look for data in position index on array Lessons
			    
		    	String client_name = item.getClientname();
				String item_name =item.getItemname();
				String delivery_date =item.getItemtime();
				//String time = Integer.toString(item.getItemtime());
				String itemimage_url = item.getItemimage_url();
				String price = item.getItemprice();
				//String time = item.getItemtime();
				String positionDel = Integer.toString(position);
				String location = item.getLocation();
				String phoneno = item.getPhoneno();
				String order_id = String.valueOf(item.getOrder_id());
				String product_id = item.getProduct_id();
				
					     Intent si = new Intent(getActivity(), SingleItemActivity.class);
					     Bundle b=new Bundle();
					     
					     b.putString("client_name", client_name);
					     b.putString("item_name", item_name);
					     //b.putString("time", time);
					     b.putString("delivery_date", delivery_date);
					     b.putString("itemimage_url", itemimage_url);
					     b.putString("price", price);
					     b.putString("positionDel", positionDel);
					     b.putString("location", location);
					     b.putString("phoneno", phoneno);
					     b.putString("order_id", order_id);
					     b.putString("product_id", product_id);
					                
					     si.putExtras(b);
					     //startActivity(si);
					     startActivityForResult(si,2);
				    }	
		   
		    	//customListAdapter.notifyDataSetChanged();
		  
		   @Override  
	       public void onActivityResult(int requestCode, int resultCode, Intent data)  
	       
	       {  
	                 super.onActivityResult(requestCode, resultCode, data);  
	                  // check if the request code is same as what is passed  here it is 2  
	                 if(resultCode == Activity.RESULT_OK )
	                         {  
	                	
	                            int  editPos = Integer.parseInt(data.getStringExtra("position_D")); 
	                            String  item_price = data.getStringExtra("price");
	                            String  status_image_url = data.getStringExtra("status_uri_image");
	                            String  itemimage_url = data.getStringExtra("itemimage_url");
	                            String  itemtime = data.getStringExtra("delivery_date");
	                            String  item_name = data.getStringExtra("item_name");
	                            String  client_name = data.getStringExtra("client_name");
	                            String  location = data.getStringExtra("location");
	                            String  phoneno = data.getStringExtra("phoneno");
	                            String  product_id = data.getStringExtra("product_id");
	                            int  order_id = Integer.parseInt(data.getStringExtra("order_id"));
	                            items.set(editPos, new Item(order_id,product_id,client_name,phoneno,item_name,item_price,itemtime,location,itemimage_url,status_image_url));
	                            
	        			        listView.setItems(items);
	                            //ItemAdapter.notifyDataSetChanged(); 
	                         }  
	                 if (resultCode == Activity.RESULT_CANCELED) {
	                     return;
	                 }
	                        	 
	            }/**/
		   
		   
		   
		   
		   
		   
		   
		   
		   
			@Override
			public void onItemLongClicked(Item item, int position) {
						
						phoneNo= item.getPhoneno();
						// TODO Auto-generated method stub
						
					}
			   //When the registered view receives a long-click event, the system calls your onCreateContextMenu() method. 
	           //MenuInflater allows you to inflate the context menu from a menu resource 

	           @Override
	           public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	           {
	                   super.onCreateContextMenu(menu, v, menuInfo);
	                   menu.setHeaderTitle("Select The Action"); 
	                   menu.add(0, v.getId(), 0, "Call"); 
	                   menu.add(0, v.getId(), 0, "Send SMS");
	   
	           }
	
	           @Override 
	           public boolean onContextItemSelected(MenuItem item)
	           { 
	
	                       AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	                       String number= phoneNo;
	             
	                       try
	                       {
	                               //number=new ContactListAdapter(this).numberList.get(info.position);
	                            
	                               if(item.getTitle()=="Call")
	                               {
	                                       Intent callIntent = new Intent(Intent.ACTION_CALL);
	                                       callIntent.setData(Uri.parse("tel:"+number));
	                                       startActivity(callIntent);
	               
	                               } 
	                               else if(item.getTitle()=="Send SMS")
	                               {
	                                       Intent smsIntent = new Intent(Intent.ACTION_VIEW);
	                                       smsIntent.setType("vnd.android-dir/mms-sms");
	                                       smsIntent.putExtra("address", number);
	                                       startActivity(smsIntent);
	        
	                               } 
	                               else
	                               {return false;} 
	                               return true; 
	                       }
	                       catch(Exception e)
	                       {
	                               return true;
	                       }
	           }  
			  /*  
	           private void updateView(int index){
           	    View v = listView.getChildAt(index - 
           	    		listView.getFirstVisiblePosition());

           	    if(v == null)
           	       return;
           	 ImageView statusimageview = v.findViewById(R.id);
           	 statusimage.setImageResource(R.drawable.delete);
           	}
		   
		   */
		   	
		
		   @Override
		    public void onCreate(Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onCreate: bundle="+parseNull(savedInstanceState));
		        super.onCreate(savedInstanceState);
		    }
		
		    @Override
		    public void onAttach(Activity activity) {
		        Log.d("FragmentCycle===>", "onAttach");
		        super.onAttach(activity);
		    }
		
		    @Override
		    public void onViewCreated(View view, Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onViewCreated: bundle="+parseNull(savedInstanceState));
		        super.onViewCreated(view, savedInstanceState);
		    }
		
		    @Override
		    public void onActivityCreated(Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onActivityCreated: bundle="+parseNull(savedInstanceState));
		        super.onActivityCreated(savedInstanceState);
		    }
		
		    @Override
		    public void onViewStateRestored(Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onViewStateRestored: bundle="+parseNull(savedInstanceState));
		        super.onViewStateRestored(savedInstanceState);
		    }
		
		    @Override
		    public void onStart() {
		        Log.d("FragmentCycle===>", "onStart");
		        super.onStart();
		    }
		
		    @Override
		    public void onResume() {
		        Log.d("FragmentCycle===>", "onResume");
		        super.onResume();
		    }
		
		    @Override
		    public void onPause() {
		        Log.d("FragmentCycle===>", "onPause");
		        super.onPause();
		    }
		
		    @Override
		    public void onStop() {
		        Log.d("FragmentCycle===>", "onStop");
		        super.onStop();
		    }
		
		    @Override
		    public void onDestroyView() {
		        Log.d("FragmentCycle===>", "onDestroyView");
		        super.onDestroyView();
		    }
		
		    @Override
		    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
		        Log.d("FragmentCycle===>", "onInflate: bundle="+parseNull(savedInstanceState));
		        super.onInflate(activity, attrs, savedInstanceState);
		    }
		
		    @Override
		    public void onSaveInstanceState(Bundle outState) {
		        Log.d("FragmentCycle===>", "onSaveInstanceState: outState="+parseNull(outState));
		       
		        outState.putInt("index", mCurCheckPosition);
		        super.onSaveInstanceState(outState);
		    }
		
		    @Override
		    public void onConfigurationChanged(Configuration newConfig) {
		        Log.d("FragmentCycle===>", "onConfigurationChanged");
		        super.onConfigurationChanged(newConfig);
		    }
		
		    @Override
		    public void onDestroy() {
		        Log.d("FragmentCycle===>", "onDestroy");
		        super.onDestroy();
		    }
		
		    @Override
		    public void onDetach() {
		        Log.d("FragmentCycle===>", "onDetach");
		        super.onDetach();
		    }
		
		    @Override
		    public void setInitialSavedState(SavedState state) {
		        Log.d("FragmentCycle===>", "setInitialSavedState");
		        super.setInitialSavedState(state);
		    }
		
		    @Override
		    public void onHiddenChanged(boolean hidden) {
		        Log.d("FragmentCycle===>", "onHiddenChanged");
		        super.onHiddenChanged(hidden);
		    }
		
		    @Override
		    public void onLowMemory() {
		        Log.d("FragmentCycle===>", "onLowMemory");
		        super.onLowMemory();
		    }







			
		}
    	
    	
    	
    	