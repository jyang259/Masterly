package com.example.masterly;

/**
 * Created by Navjashan on 12/01/2018.
 */

public class list_items {

    private String Description;

    public list_items() {}

    public list_items(String title) {
        this.Description = title;
    }

    public String getTitle() {
        return Description;
    }

    public void setTitle(String title) {
        this.Description = title;
    }
}
