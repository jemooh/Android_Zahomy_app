package com.zmy.zahomy_app.src;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.zmy.zahomy_app.model.Item;
import com.zmy.zahomy_app.util.FeedbackDialogFragment;
import com.zmy.zahomy_app.R;
import com.zmy.zahomy_app.util.ServerInteractions;
import com.zmy.zahomy_app.widget.ImageLoader;
import com.zmy.zahomy_app.widget.ItemsListView;

import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore.Images;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.ActionBarPolicy;
//ActionBar.TabListener
		
		public class SingleItemActivity extends ActionBarActivity   {
			String client_name,item_name;
			String itemimage_url,price,delivery_d,delivery_date;
			String time;
			String position_D,order_id,product_id,phoneno,location,status_image_url;
			AlertDialog alertDialog;
			Button btnCanceled, btnDelivered,btnFeedBack;
			private ItemsListView listView;
			private static final int LOGIN_DIALOG = 3;
			private static String KEY_SUCCESS = "success";
			SaveTransactionDetailsTask TransDetTsk;
			public static final int SIGNATURE_ACTIVITY = 1;
			//SaveCommentTask commentTsk;
			ServerInteractions userFunction;
			JSONObject json_user;
		    JSONObject json;
		    String errorMsg, successMsg;
		    String res;
		    public ImageLoader imageLoader; 
		    DecimalFormat df = new DecimalFormat("#.##");
			    private ActionBar actionBar;
			    
               //capture signature
			    LinearLayout mContent;
			    signature mSignature;
			    ImageButton mClear, mGetSign, mCancel;
			    TextView txtSignClintName,txtName,txtSign;
			    public static String tempDir;
			    public static String positionDel=null;
			    public int count = 1;
			    public String current = null;
			    private Bitmap mBitmap;
			    View mView;
			    File mypath;
			    private String uniqueId;
			    
			   /* public SingleItemActivity() {
					super(R.string.Details_name);
				}*/
			    //List<Item> items = new ArrayList<Item>();
               // items.set(editPos, new Item(order_id,product_id,clientname,phoneno,itemname,itemprice,itemtime,location,itemimage_url,status_image_url));
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);	
				setContentView(R.layout.single_item_layout);
				
				 getOverflowMenu();
			      ActionBarPolicy.get(this).showsOverflowMenuButton();
			     btnCanceled= (Button) findViewById(R.id.btnCan);
			     btnFeedBack = (Button) findViewById(R.id.btnfeedback);
					btnFeedBack.setOnClickListener(new OnClickListener() {			
						@Override
						public void onClick(View v) {
							//display feedback dialog
							//getActivity().showDialog(FEEDBACK_DIALOG);	
						    showDialog();
						}
						});
			     
					 btnCanceled.setOnClickListener(new OnClickListener() {			
						@Override
						public void onClick(View v) {
				
							//String editPos =position_D;	
							String status_uri_image = "@drawable/delete";  
			                 Intent intent=new Intent();  
			                 intent.putExtra("client_name", client_name);
			                 intent.putExtra("item_name", item_name);
						     //b.putString("time", time);
			                 intent.putExtra("delivery_date", delivery_date);
			                 intent.putExtra("itemimage_url", itemimage_url);
			                 intent.putExtra("price", price);
			                 intent.putExtra("position_D", position_D);
			                 intent.putExtra("location", location);
			                 intent.putExtra("phoneno", phoneno);
			                 intent.putExtra("order_id", order_id);
			                 intent.putExtra("product_id", product_id);
			                 intent.putExtra("status_uri_image",status_uri_image); 
			                    setResult(RESULT_OK ,intent);  
			                    finish();//finishing activity */
						}
						});
			     
					
			     
				actionBar = getSupportActionBar();
			    actionBar.setDisplayHomeAsUpEnabled(true);
				//databaseHelper = new PersonalDatabaseHelper(this);
				Intent in=getIntent();
				Bundle b=in.getExtras();
				
					 imageLoader=new ImageLoader(getApplicationContext());
					this.position_D= b.getString("positionDel");
					this.item_name = b.getString("item_name");
					this.delivery_date = b.getString("delivery_date");
					this.itemimage_url = b.getString("itemimage_url");
					this.client_name = b.getString("client_name");
					//this.time = b.getString("time");
					this.price = b.getString("price");
				 	this.location = b.getString("location");
					this.phoneno = b.getString("phoneno");
					this.order_id = b.getString("order_id");
					this.product_id = b.getString("product_id");
				
				
				
				TextView txtItemName = (TextView) findViewById(R.id.txtItemName);
				TextView txtTime = (TextView) findViewById(R.id.txtTime);
				TextView txtPrice = (TextView) findViewById(R.id.txtPrice);
				TextView txtClientName = (TextView) findViewById(R.id.txtClientName);
				TextView txtStatus = (TextView) findViewById(R.id.txtStatus);
				TextView txtClient = (TextView) findViewById(R.id.TextView02);
				ImageView ItemImage = (ImageView) findViewById(R.id.imageViewItem);
				
				Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Thin.ttf");
		        Typeface font_a = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Regular.ttf");
		        Typeface font_b = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Light.ttf");
		        Typeface font_d = Typeface.createFromAsset(this.getAssets(), "fonts/DistProTh.otf");
		        Typeface font_c = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Medium.ttf");
		        
		        txtTime.setTypeface(font_d);
		        txtItemName.setTypeface(font_a);
		        txtStatus.setTypeface(font_b);
		        txtClient.setTypeface(font_b);
		        txtClientName.setTypeface(font_c);
				txtPrice.setTypeface(font_a);
				//txtTime.setText(createTime(startTime, endTime));
				
				txtItemName.setText(item_name);
				txtPrice.setText("KShs.."+price);
				//txtTime.setText(time);
				txtClientName.setText(client_name);
				
				// Set the image for the list item
		        //holder.thumb.setImageDrawable(video.getThumbUrl());
				 imageLoader.DisplayImage(itemimage_url, ItemImage);
				 
				
				 Calendar c = Calendar.getInstance();
	                int t = Integer.parseInt(delivery_date);
	    		    c.setTimeInMillis(t*1000L);
	    		    SimpleDateFormat currentDate = new SimpleDateFormat("MMM/dd");
	                 String del_d = currentDate.format(c.getTime());
				
				/*
				String pn= "am";
				double a = Float.parseFloat(time)/100; 
		    	
				
				if(a>12){
		    		pn= "pm";
		    		a = a-12;
		    		if(a<1)
		    			a=a+1;
		    	} 
				
				
		    	String m = df.format(a) + pn;
		    	m = m.replaceAll("[.]", ":");
		    	*/
		    	txtTime.setText(del_d);
		    	
		        //capture signature
		        //storing the image of the signature and the its details to the database.
		        tempDir = Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.external_dir) + "/";
		        ContextWrapper cw = new ContextWrapper(getApplicationContext());
		        File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);
		 
		        prepareDirectory();
		        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
		        current = uniqueId + ".png";
		        mypath= new File(directory,current);
		 
		 
		        mContent = (LinearLayout) findViewById(R.id.linearLayout);
		        mSignature = new signature(this, null);
		        mSignature.setBackgroundColor(Color.WHITE);
		        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		        mClear = (ImageButton)findViewById(R.id.clear);
		        btnDelivered= (Button) findViewById(R.id.btnDel);
		        //mGetSign = (ImageButton)findViewById(R.id.getsign);
		        btnDelivered.setEnabled(false);
		        //mCancel = (ImageButton)findViewById(R.id.cancel);
		        mView = mContent;
		        
		        //yourName = (EditText) findViewById(R.id.yourName);
		 
		        mClear.setOnClickListener(new OnClickListener() 
		        {        
		            public void onClick(View v) 
		            {
		                Log.v("log_tag", "Panel Cleared");
		                mSignature.clear();
		                btnDelivered.setEnabled(false);
		            }
		        });
		        
		        btnDelivered.setOnClickListener(new View.OnClickListener() {
					 
	  				@Override
	  				public void onClick(View v) {
	  					String transactionDetails = "Delivered"+" "+item_name+" "+"to"+" "+client_name;
	  					String date = giveDateTime();
	  					//save signature
	  					mView.setDrawingCacheEnabled(true);
	                    mSignature.save(mView);
	                    //clear signature
	                    mSignature.clear();
	  					//start task
	  	                MyCommentParams params = new MyCommentParams(transactionDetails,date);
	  	              TransDetTsk = new SaveTransactionDetailsTask();
	  	              TransDetTsk.execute(params);
	  	              
	  	              
						//String editPos =position_D;	
						 String status_uri_image = "@drawable/check";  
		                 Intent intent=new Intent();  
		                 intent.putExtra("client_name", client_name);
		                 intent.putExtra("item_name", item_name);
					     //b.putString("time", time);
		                 intent.putExtra("delivery_date", delivery_date);
		                 intent.putExtra("itemimage_url", itemimage_url);
		                 intent.putExtra("price", price);
		                 intent.putExtra("position_D", position_D);
		                 intent.putExtra("location", location);
		                 intent.putExtra("phoneno", phoneno);
		                 intent.putExtra("order_id", order_id);
		                 intent.putExtra("product_id", product_id);
		                 intent.putExtra("status_uri_image",status_uri_image); 
		                    setResult(RESULT_OK ,intent);  
		                    finish();//finishing activity */
	  	              
	  	              
	  				}
	  			});
		     
		      /*
		        mGetSign.setOnClickListener(new OnClickListener() 
		        {        
		            public void onClick(View v) 
		            {
		                Log.v("log_tag", "Panel Saved");
		                //boolean error = captureSignature();
		                //if(!error){
		                    mView.setDrawingCacheEnabled(true);
		                    mSignature.save(mView);
		                    //clear signature
		                    mSignature.clear();
		                   /* Bundle b = new Bundle();
		                    b.putString("status", "done");
		                    Intent intent = new Intent();
		                    intent.putExtras(b);
		                    setResult(RESULT_OK,intent);   
		                    finish();
		               // }
		            }
		        });
		 
		        mCancel.setOnClickListener(new OnClickListener() 
		        {        
		            public void onClick(View v) 
		            {
		                Log.v("log_tag", "Panel Canceled");
		                /*Bundle b = new Bundle();
		                b.putString("status", "cancel");
		                Intent intent = new Intent();
		                intent.putExtras(b);
		                setResult(RESULT_OK,intent);  
		                //finish();
		            }
		        });*/
		 
		    }
		    	//capture signature
			  @Override
			    protected void onDestroy() {
			        Log.w("GetSignature", "onDestory");
			        super.onDestroy();
			    }
			 
			    /** private boolean captureSignature() {
			 
			        boolean error = false;
			        String errorMessage = "";
			 
			     
			        if(yourName.getText().toString().equalsIgnoreCase("")){
			            errorMessage = errorMessage + "Please enter your Name\n";
			            error = true;
			            
			        } 
			 
			        if(error){
			            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			            toast.setGravity(Gravity.TOP, 105, 50);
			            toast.show();
			        }
			 
			        return error;
			    } */ 
			 
			    private String getTodaysDate() { 
			 
			        final Calendar c = Calendar.getInstance();
			        int todaysDate =     (c.get(Calendar.YEAR) * 10000) + 
			        ((c.get(Calendar.MONTH) + 1) * 100) + 
			        (c.get(Calendar.DAY_OF_MONTH));
			        Log.w("DATE:",String.valueOf(todaysDate));
			        return(String.valueOf(todaysDate));
			 
			    }
			 
			    private String getCurrentTime() {
			 
			        final Calendar c = Calendar.getInstance();
			        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) + 
			        (c.get(Calendar.MINUTE) * 100) + 
			        (c.get(Calendar.SECOND));
			        Log.w("TIME:",String.valueOf(currentTime));
			        return(String.valueOf(currentTime));
			 
			    }
			 
			 
			    private boolean prepareDirectory() 
			    {
			        try
			        {
			            if (makedirs()) 
			            {
			                return true;
			            } else {
			                return false;
			            }
			        } catch (Exception e) 
			        {
			            e.printStackTrace();
			            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", 1000).show();
			            return false;
			        }
			    }
			 
			    private boolean makedirs() 
			    {
			        File tempdir = new File(tempDir);
			        if (!tempdir.exists())
			            tempdir.mkdirs();
			 
			        if (tempdir.isDirectory()) 
			        {
			            File[] files = tempdir.listFiles();
			            for (File file : files) 
			            {
			                if (!file.delete()) 
			                {
			                    System.out.println("Failed to delete " + file);
			                }
			            }
			        }
			        return (tempdir.isDirectory());
			    }
			 
			    public class signature extends View 
			    {
			        private static final float STROKE_WIDTH = 5f;
			        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
			        private Paint paint = new Paint();
			        private Path path = new Path();
			 
			        private float lastTouchX;
			        private float lastTouchY;
			        private final RectF dirtyRect = new RectF();
			 
			        public signature(Context context, AttributeSet attrs) 
			        {
			            super(context, attrs);
			            paint.setAntiAlias(true);
			            paint.setColor(Color.BLACK);
			            paint.setStyle(Paint.Style.STROKE);
			            paint.setStrokeJoin(Paint.Join.ROUND);
			            paint.setStrokeWidth(STROKE_WIDTH);
			        }
			 
			        public void save(View v) 
			        {
			            Log.v("log_tag", "Width: " + v.getWidth());
			            Log.v("log_tag", "Height: " + v.getHeight());
			            if(mBitmap == null)
			            {
			                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
			            }
			            Canvas canvas = new Canvas(mBitmap);
			            try
			            {
			                FileOutputStream mFileOutStream = new FileOutputStream(mypath);
			 
			                v.draw(canvas); 
			                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream); 
			                mFileOutStream.flush();
			                mFileOutStream.close();
			                String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
			                Log.v("log_tag","url: " + url);
			                //In case you want to delete the file
			                //boolean deleted = mypath.delete();
			                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
			                //If you want to convert the image to string use base64 converter
			 
			            }
			            catch(Exception e) 
			            { 
			                Log.v("log_tag", e.toString()); 
			            } 
			        }
			 //clear signature.
			        public void clear() 
			        {
			            path.reset();
			            invalidate();
			        }
			 
			        @Override
			        protected void onDraw(Canvas canvas) 
			        {
			            canvas.drawPath(path, paint);
			        }
			 
			        @Override
			        public boolean onTouchEvent(MotionEvent event) 
			        {
			            float eventX = event.getX();
			            float eventY = event.getY();
			            btnDelivered.setEnabled(true);
			 
			            switch (event.getAction()) 
			            {
			            case MotionEvent.ACTION_DOWN:
			                path.moveTo(eventX, eventY);
			                lastTouchX = eventX;
			                lastTouchY = eventY;
			                return true;
			 
			            case MotionEvent.ACTION_MOVE:
			 
			            case MotionEvent.ACTION_UP:
			 
			                resetDirtyRect(eventX, eventY);
			                int historySize = event.getHistorySize();
			                for (int i = 0; i < historySize; i++) 
			                {
			                    float historicalX = event.getHistoricalX(i);
			                    float historicalY = event.getHistoricalY(i);
			                    expandDirtyRect(historicalX, historicalY);
			                    path.lineTo(historicalX, historicalY);
			                }
			                path.lineTo(eventX, eventY);
			                break;
			 
			            default:
			                debug("Ignored touch event: " + event.toString());
			                return false;
			            }
			 
			            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
			                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
			                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
			                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
			 
			            lastTouchX = eventX;
			            lastTouchY = eventY;
			 
			            return true;
			        }
			 
			        private void debug(String string){
			        }
			 
			        private void expandDirtyRect(float historicalX, float historicalY) 
			        {
			            if (historicalX < dirtyRect.left) 
			            {
			                dirtyRect.left = historicalX;
			            } 
			            else if (historicalX > dirtyRect.right) 
			            {
			                dirtyRect.right = historicalX;
			            }
			 
			            if (historicalY < dirtyRect.top) 
			            {
			                dirtyRect.top = historicalY;
			            } 
			            else if (historicalY > dirtyRect.bottom) 
			            {
			                dirtyRect.bottom = historicalY;
			            }
			        }
			 
			        private void resetDirtyRect(float eventX, float eventY) 
			        {
			            dirtyRect.left = Math.min(lastTouchX, eventX);
			            dirtyRect.right = Math.max(lastTouchX, eventX);
			            dirtyRect.top = Math.min(lastTouchY, eventY);
			            dirtyRect.bottom = Math.max(lastTouchY, eventY);
			        }
			    }
		    	
		
	        //get feeback dialog
			    
			    private void showDialog() {
					DialogFragment newFragment = FeedbackDialogFragment.newInstance(1);
					newFragment.show(getSupportFragmentManager(), "dialog");
				}    
			 //getting overflowMenu for android 2.3 and below.
			
		    private void getOverflowMenu() {
				 
			    try {
			 
			       ViewConfiguration config = ViewConfiguration.get(this);
			       java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			       if(menuKeyField != null) {
			           menuKeyField.setAccessible(true);
			           menuKeyField.setBoolean(config, false);
			       }
			   } catch (Exception e) {
			       e.printStackTrace();
			   }
			}
			 // so that we know something was triggered
			    public void showToast(String msg){
			        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
			    
			 
			}
		    
		    
		    
		    
				public String createTime(String start, String end) {
					String pn= "am";
					double a = Float.parseFloat(start)/100; 
					double c = Float.parseFloat(end)/100; 
			    	
					if(a>12){
			    		pn= "pm";
			    		a = a-12;
			    		if(a<1)
			    			a=a+1;
			    	} 
					if(c>12){
			    		pn= "pm";
			    		c = c-12;
			    		if(c<0.59)
			    			c=c+1;
			    	} 
			    	String m = df.format(a) + " - " + df.format(c) + pn;
			    	m = m.replaceAll("[.]", ":");
			        return m;
				}

					
				@Override
				public boolean onMenuOpened(int featureId, Menu menu)
				{
				    if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
				        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
				            try{
				                Method m = menu.getClass().getDeclaredMethod(
				                    "setOptionalIconsVisible", Boolean.TYPE);
				                m.setAccessible(true);
				                m.invoke(menu, true);
				            }
				            catch(NoSuchMethodException e){
				               // Log.i( "onMenuOpened", e);
				            }
				            catch(Exception e){
				                throw new RuntimeException(e);
				            }
				        }
				    }
				    return super.onMenuOpened(featureId, menu);
				}
				
	
					@Override
					public boolean onCreateOptionsMenu(Menu menu) {
						// Inflate the menu; this adds items to the action bar if it is present.
						getMenuInflater().inflate(R.menu.activity_main, menu);
						return true;
					}
					public boolean onOptionsItemSelected(MenuItem item) {
				        switch (item.getItemId()) {
				        
				            case android.R.id.home://dsipaly video
				                //Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
				            	    Bundle b = new Bundle();
					                b.putString("status", "cancel");
					                Intent intent = new Intent();
					                intent.putExtras(b);
					                setResult(RESULT_CANCELED ,intent);  
					                finish(); 
				                
				                break;
				    
				           // case R.id.menu_settings: //display reviews
				        	//    toggle();
				  			  //  return true;
				           
				            
				            
				        }
				        return super.onOptionsItemSelected(item);
				    }
					
					//function to get Date_time
					
			public String giveDateTime() {
					       Calendar cal = Calendar.getInstance();
					       SimpleDateFormat currentDate = new SimpleDateFormat("MMM/dd/yyyy HH:mm ");
					       return currentDate.format(cal.getTime());
					    }
					
					
					/*

					//getting results from signature activity.
			  protected void onActivityResult(int requestCode, int resultCode, Intent data){
				  
					       switch(requestCode) {
					       case SIGNATURE_ACTIVITY: 
					           if (resultCode == RESULT_OK) {

					               Bundle bundle = data.getExtras();
					               String status  = bundle.getString("status");
					               if(status.equalsIgnoreCase("done")){
					                   Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
					                   toast.setGravity(Gravity.TOP, 105, 50);
					                   toast.show();
					               }
					           }
					           break;
					       }

					   }
			  */
			  
			  private class SaveTransactionDetailsTask extends AsyncTask<MyCommentParams, Void, String> {
			        @Override
			        protected String doInBackground(MyCommentParams... params) {
			        	userFunction = new ServerInteractions();
			
			        	String transactionDetails = params[0].transactionDetails;
			        	String date = params[0].date;
			        	
			        	
			        	json = userFunction.postTransactionDetails(transactionDetails, date); //100 refers to example user id
			            try {
			                if (json.getString(KEY_SUCCESS) != null) {
			                	errorMsg = "";
			                    res = json.getString(KEY_SUCCESS);
			                    if(Integer.parseInt(res) == 1){
			                    	successMsg = "Successfully sent a transactions details, thank you";
			                    }else{
			                        // Error in login
			                    	successMsg = "Something went wrong, please verify your sentence";
			                    }
			                }
			            } catch (JSONException e) {
			                e.printStackTrace();
			            }
						return successMsg; 
			        }
			        
			        @Override
			        protected void onPostExecute(String json_user) {        	
			        	try {
			        		if(isCancelled())        	
							return;
			        		
			        		if(Integer.parseInt(res) == 1){
			                	Toast.makeText(getApplicationContext(), "Successfully sent a transactions details", Toast.LENGTH_SHORT).show();
			                	//getCommentsFeed(listView, strVideoId);
			                  	alertDialog.dismiss();
			                }
			        	} catch(Exception e){
							Log.e(this.getClass().getSimpleName(), "Error Saving transactions details", e);
						}
			        }        
			    }
				
				private static class MyCommentParams {
			        String date, transactionDetails;
			        MyCommentParams(String transactionDetails,String date) {
			            this.transactionDetails = transactionDetails;
			            this.date = date;
			            
			        }
			    }
					
					
				@Override
				public boolean onKeyDown(int keyCode, KeyEvent event) {
					// Handle the back button
					if (keyCode == KeyEvent.KEYCODE_BACK) {
					
						
			                Bundle b = new Bundle();
			                b.putString("status", "cancel");
			                Intent intent = new Intent();
			                intent.putExtras(b);
			                setResult(RESULT_CANCELED ,intent);  
			                finish(); 
										
						return true;
					} else {
						return super.onKeyDown(keyCode, event);
					}

				}	
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					

			
				}
