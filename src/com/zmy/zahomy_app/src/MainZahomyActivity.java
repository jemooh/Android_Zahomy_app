package com.zmy.zahomy_app.src;


import com.zmy.zahomy_app.R;
	//import com.zmy.zahomy_app.src.PreferenceListFragment.OnPreferenceAttachedListener;
	import com.zmy.zahomy_app.util.DatabaseHandler;
import com.zmy.zahomy_app.util.ServerInteractions;
	//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;



import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.ActionBarPolicy;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

				
				public class MainZahomyActivity extends ActionBarActivity  {
					
				 
				    Builder builder;
					 static String parseNull(Object obj){
					        return obj == null?"null"  : "Object";
					    }
					ServerInteractions server;
					@Override
					public void onCreate(Bundle savedInstanceState) {
						Log.e("ActivityCycle", "onCreate: bundle="+parseNull(savedInstanceState));
						super.onCreate(savedInstanceState);
						setContentView(R.layout.activity_main_zahomy);
						
						//server = new ServerInteractions();
		
			    // if(server.isUserLoggedIn(getApplicationContext())){
					     setContentView(R.layout.activity_main_zahomy);
					     
					     //calling home activity
					    // ActionBar actionBar = getSupportActionBar();
					    // actionBar.setDisplayHomeAsUpEnabled(true);
					     
					    // getOverflowMenu();
					     
					     ActionBarPolicy.get(this).showsOverflowMenuButton();
					     //setBehindContentView(R.layout.menu_frame);
					     
					     if (savedInstanceState == null) {
								getSupportFragmentManager().beginTransaction()
										.add(R.id.container, new ListItemsFragment()).commit();
								/*getSupportFragmentManager().beginTransaction()
						                 .add(R.id.menu_frame, new SettingsFragment()).commit();*/
							
						}
					     
					 
					/*		
						
						ActionBar actionBar = getSupportActionBar();
					     actionBar.setDisplayHomeAsUpEnabled(true);
						 
		              }else
					       {
					           Intent login = new Intent(getApplicationContext(),LoginActivity.class);
					           login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					           startActivity(login);
					           finish();
					       }
					*/
					     
					}
					
				
							
					@Override
					public boolean onCreateOptionsMenu(Menu menu) {
						
						// Inflate the menu; this adds items to the action bar if it is present.
						//new MenuInflater(getApplication()).inflate(R.menu.activity_main, menu);
						getMenuInflater().inflate(R.menu.activity_main, menu);
						return true;
					}
					
					public boolean onOptionsItemSelected(MenuItem item) {
				        switch (item.getItemId()) {
				      
				           
				          case R.id.menu_refresh://dsipaly video
				                //Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
				                Intent i = new Intent(getApplicationContext(), MainZahomyActivity.class);
				                startActivity(i);
				                finish();
				                break;
				             /*  
				            case R.id.a_More:
				            	openOptionsMenuDeferred();
				                break;
				            */
				            
				        }
				        return super.onOptionsItemSelected(item);
				    }
					
				
					/**
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
					
			 */

						@Override
						public boolean onKeyDown(int keyCode, KeyEvent event) {
							// Handle the back button
							if (keyCode == KeyEvent.KEYCODE_BACK) {
								// Ask the user if they want to quit
								/* new AlertDialog.Builder(new ContextThemeWrapper(
						    		    this,R.style.AlertDialogCustom))*/
								new AlertDialog.Builder(this)
										.setIcon(android.R.drawable.ic_dialog_alert)
										.setTitle("Quit?")
										.setMessage("Do you want to quit Zahomy?")
										.setPositiveButton("Yes",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialog,
															int which) {

														// Stop the activity
														MainZahomyActivity.this.finish();
													}

												}).setNegativeButton("No", null).show();

								return true;
							} else {
								return super.onKeyDown(keyCode, event);
							}

						}
					  
						 @Override
						    public void onConfigurationChanged(Configuration newConfig) {
						        super.onConfigurationChanged(newConfig);
						        Log.e("ActivityCycle", "onConfigurationChanged");
						    }

						    @Override
						    public View onCreateView(String name, Context context, AttributeSet attrs) {
						        Log.e("ActivityCycle", "onCreateView");
						        return super.onCreateView(name, context, attrs);
						    }

						    @Override
							public void onPostCreate(Bundle savedInstanceState) {
						        Log.e("ActivityCycle", "onPostCreate: bundle="+parseNull(savedInstanceState));
						        super.onPostCreate(savedInstanceState);
						    }

						    @Override
						    protected void onResume() {
						        Log.e("ActivityCycle", "onResume");
						        super.onResume();
						    }

						    @Override
						    protected void onPostResume() {
						        Log.e("ActivityCycle", "onPostResume");
						        super.onPostResume();
						    }

						    @Override
						    protected void onResumeFragments() {
						        Log.e("ActivityCycle", "onResumeFragments");
						        super.onResumeFragments();
						    }

						    @Override
						    protected void onStop() {
						        Log.e("ActivityCycle", "onStop");
						        super.onStop();
						    }

						    @Override
						    protected void onPause() {
						        Log.e("ActivityCycle", "onPause");
						        super.onPause();
						    }

						    @Override
						    protected void onDestroy() {
						        Log.e("ActivityCycle", "onDestroy");
						        super.onDestroy();
						    }

						    @Override
						    protected void onSaveInstanceState(Bundle outState) {
						        Log.e("ActivityCycle", "onSaveInstanceState: outState="+parseNull(outState));
						      //  super.onSaveInstanceState(outState);
						    }

						    @Override
						    public void onAttachFragment(Fragment fragment) {
						        Log.e("ActivityCycle", "onAttachFragment");
						        super.onAttachFragment(fragment);
						    }

						    @Override
						    protected void onRestoreInstanceState(Bundle savedInstanceState) {
						        Log.e("ActivityCycle", "onRestoreInstanceState: bundle="+parseNull(savedInstanceState));
						        super.onRestoreInstanceState(savedInstanceState);
						    }

						    @Override
						    protected void onRestart() {
						        Log.e("ActivityCycle", "onRestart");
						        super.onRestart();
						    }

						    @Override
						    public void onAttachedToWindow() {
						        Log.e("ActivityCycle", "onAttachedToWindow");
						        super.onAttachedToWindow();
						    }

						    @Override
						    public void onDetachedFromWindow() {
						        Log.e("ActivityCycle", "onDetachedFromWindow");
						        super.onDetachedFromWindow();
						    }

						    @Override
						    protected void onStart() {
						        Log.e("ActivityCycle", "onStart");
						        super.onStart();
						    }

						    @Override
						    public void onWindowFocusChanged(boolean hasFocus) {
						        Log.e("ActivityCycle", "onWindowFocusChanged");
						        super.onWindowFocusChanged(hasFocus);
						    }

						    @Override
						    public void onLowMemory() {
						        Log.e("ActivityCycle", "onLowMemory");
						        super.onLowMemory();
						    }
					
					
				}
				
