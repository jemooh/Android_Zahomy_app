package com.zmy.zahomy_app.model;

import java.io.Serializable;
			
			/**
			 * This is the 'library' of all the users videos
			 *
			 * @author paul.blundell
			 */
			public  class Item implements Comparable<Item> {
				/**
				 * code, title, teacher, starttime, endtime, location,
				 */
				private static final long serialVersionUID = 1L;
				private String itemname;
			    private String clientname;
			    private String location;
			    private String itemimage_url,status_image_url;
			    private String itemprice;
			    private String itemtime;
			    private String phoneno;
			    private int order_id;
			    private String product_id;
			     //order_id,product_id,clientname,phoneno,itemname,itemprice,itemtime,location,itemimage_url)
			    public Item(int order_id,String product_id,String clientname,String phoneno,String itemname,String itemprice,String itemtime,String location,String itemimage_url,String status_image_url) {
			        super();
			        this.order_id = order_id ;
			        this.product_id = product_id;
			        this.itemname = itemname ;
			        this.clientname = clientname;
			        this.location = location;
			        this.itemimage_url = itemimage_url;
			        this.status_image_url = status_image_url;
			        this.itemprice = itemprice;
			        this.itemtime = itemtime;
			        this.phoneno = phoneno;
				    }
			 
			    /**
			     * @return the title of the video
			     */
			    public int getOrder_id(){
			        return order_id;
			    }
			    
			/*
			    public Item set(int position,Item object) {
			    	
			 		return object;
			 	}
			
			    
			    /**
			     * @return the title of the video
			     */
			    public String getProduct_id(){
			        return product_id;
			    }
			    
			    /**
			     * @return the title of the video
			     */
			    public String getItemname(){
			        return itemname;
			    }
			    
			    /**
			     * @return the title of the video
			     */
			    public String getClientname(){
			        return clientname;
			    }
			    
			    public String getLocation(){
			        return location;
			    }
			 
			    public String getItemimage_url(){
			        return itemimage_url;
			    }
			    public String getStatus_image_url(){
			        return status_image_url;
			    }
			 
			    public String getItemprice() {
			        return itemprice;
			    }
			 
			    public String getItemtime() {
			        return itemtime;
			    }
			    public String getPhoneno() {
			        return phoneno;
			    }
			    
			    
			    public void setOrder_id(int order_id) {
					this.order_id = order_id;
				}
			
			    public void set(String status_image_url) {
					this.status_image_url = status_image_url;
				}
			
			    
			    
			    
			/*
				@Override
				public int compareTo(Item items) {
					double compareSalary = ((Item) items).getOrder_id();
					          // ascending order
							 return (int) (this.order_id - compareSalary);
			
							// descending order
							//return (int) (compareSalary - this.salary);
				}
			    */
			    @Override
				public int compareTo(Item items) {
					 
					int compareorder_id = ((Item) items).getOrder_id();
			        // ascending order
					 return (int) (this.order_id - compareorder_id);
			 
					//descending order
					//return compareorder_id - this.order_id;
			 
				}	
			
			   
	
}
