package com.zmy.zahomy_app.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zmy.zahomy_app.src.SingleItemActivity;
import com.zmy.zahomy_app.util.Log;
import com.zmy.zahomy_app.widget.FileCache;
import com.zmy.zahomy_app.widget.ImageLoader;
import com.zmy.zahomy_app.widget.MemoryCache;
import com.zmy.zahomy_app.R;
import com.zmy.zahomy_app.model.Item;

		/**
		 * This adapter is used to show our items objects in a ListView
		 * It hasn't got many memory optimisations, if your list is getting bigger or more complex
		 * you may want to look at better using your view resources: http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/List14.html
		 * @author paul.blundell
		 */
		public class ItemsAdapter extends BaseAdapter {
			List<Item> Items;
			DecimalFormat df = new DecimalFormat("#.##");
		    // An inflator to use when creating rows
		    private LayoutInflater mInflater;
		    Context mContext;
		    public ImageLoader imageLoader; 
		    FileCache fileCache;
		    MemoryCache memoryCache=new MemoryCache(); 
		   public String posy =SingleItemActivity.positionDel;
		    /**
		     * @param context this is the context that the list will be shown in - used to create new list rows
		     * @param items this is a list of items to display
		     */
		    public ItemsAdapter(Context context, List<Item> Items) {
		        this.Items = Items;
		        this.mInflater = LayoutInflater.from(context);
		        this.mContext = context;
		        imageLoader=new ImageLoader(context.getApplicationContext());
		    }
		 
		    
		    public void setListData(List<Item> ListItems){
		         Items= ListItems;
		        
		      }
		    
		    //@Override
		    public int getCount() {
		        return Items.size();
		    }
		 
		    //@Override
		    public Object getItem(int position) {
		        return Items.get(position);
		    }
		    /*
		    public Object set(int index, Item newValue){
		    	
		    	/*if((index > list.length-1)  || (index < 0))
		    		throw new ArrayIndexOutOfBoundsException(index);
		  
		    	E oldValue = item[index]
		    	list[index] = newValue;
		    	return newValue;  	
		    	
		    }
		    */
		    
		    //@Override
		    public long getItemId(int position) {
		        return position;
		    }
			
			static class ViewHolder {
				public TextView client_name;		
				public TextView PhoneNo;		
				public ImageView Itemimage,statusimage;		
				public TextView Time,order_no;		
				public TextView location;
			}
		 
		    //@Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		        View view = null; // If convertView wasn't null it means we have already set it to our list_item_user_video so no need to do it again
				if(convertView == null) {
		            // This is the layout we are using for each row in our list anything you declare in this layout can then be referenced below
		            convertView = mInflater.inflate(R.layout.single_row_item, parent, false);
					
					final ViewHolder viewHolder = new ViewHolder();
					viewHolder.client_name = (TextView) convertView.findViewById(R.id.txtclient_name); 
					viewHolder.PhoneNo = (TextView) convertView.findViewById(R.id.txtPhoneNo);
					viewHolder.Itemimage = (ImageView) convertView.findViewById(R.id.itemsImage);
					viewHolder.statusimage = (ImageView) convertView.findViewById(R.id.imageViewStatus);
					viewHolder.location = (TextView) convertView.findViewById(R.id.txtLocation);
					viewHolder.Time = (TextView) convertView.findViewById(R.id.txtTime);
					//viewHolder.order_no = (TextView) convertView.findViewById(R.id.txtOrder_id);
					convertView.setTag(viewHolder);
		        }
				
				ViewHolder holder = (ViewHolder) convertView.getTag();
			        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
			        Typeface font_a = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf");
			        Typeface font_b = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
			        Typeface font_c = Typeface.createFromAsset(mContext.getAssets(), "fonts/DistProTh.otf");
			        Typeface font_d = Typeface.createFromAsset(mContext.getAssets(), "fonts/DroidSans.ttf");
			        holder.Time.setTypeface(font_b);
			        holder.PhoneNo.setTypeface(font_b);
			        holder.client_name.setTypeface(font_a);
			        holder.location.setTypeface(font_b);
				
				
		        // Get a single video from our list
		        Item Item = Items.get(position);
		      
		        
		        
		        Calendar c = Calendar.getInstance();
                int t = Integer.parseInt(Item.getItemtime());
    		    c.setTimeInMillis(t*1000L);
    		    SimpleDateFormat currentDate = new SimpleDateFormat("MMM/dd");
    		    Log.i( "Hours: " +currentDate.format(c.getTime()));
                 String del_d = currentDate.format(c.getTime());
		        // Set the image for the list item
		        //holder.thumb.setImageDrawable(video.getThumbUrl());
		        imageLoader.DisplayImage(Item.getItemimage_url(), holder.Itemimage);
		      
		        holder.client_name.setText(Item.getClientname());
		        holder.location.setText(Item.getLocation());
		        holder.PhoneNo.setText(Item.getPhoneno());
		        holder.Time.setText(del_d);
		       // holder.order_no.setText(Integer.toString(Item.getOrder_id()));
		        //int posd =Integer.parseInt(pos);
		        
		       // String uri = "@drawable/check.png";
               
		        String uri = Item.getStatus_image_url();
		        
		        //imageView.setImageBitmap(bmImg);
		        int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
		        Drawable res = mContext.getResources().getDrawable(imageResource);
		        holder.statusimage.setImageDrawable(res);
		        
              // ImageView selectedImage = (ImageView) viewHolder.findViewById(R.id.imageViewStatus);    
		        //holder.statusimage.setImageResource(Item.getStatus_image_url()); 
                
		    	/*	
		    	 * Integer.toString(5)
		    	 
		        //String startTime = Item.getItemtime();
				String pn="am";
				int a = Item.getItemtime(); 
				
				if (a>12){
		    		pn="pm";
		    		//holder.Time.setText(a+""+pn);	
		    		a = a-12;
		    		if(a<1)
		    			a=a+1;
				}/*else{
					pn="PM"	;
				}
				String m = df.format(a) + pn;
				m = m.replaceAll("[.]", ":");
				holder.Time.setText(del_d+" "+m);
				*/
		        
		        
		        return convertView;
		    }
		    
		   
		}
