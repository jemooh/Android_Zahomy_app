package com.zmy.zahomy_app.model;

import java.io.Serializable;
import java.util.List;

public class ItemLibrary implements Serializable {
	private static final long serialVersionUID = 1L;
	// The username of the owner of the library
    private String user;
    // A list of lessons that the user owns
    private List<Item> Items;
     
    public ItemLibrary(String user, List<Item> Items) {
        this.user = user;
        this.Items = Items;
    }
 
    /**
     * @return the user name
     */
    public String getUser() {
        return user;
    }
 
    /**
     * @return the lessons
     */
    public List<Item> getItems() {
        return Items;
    }
}