package com.example.android.simpleblog;

/**
 * Created by DELL PC on 2/22/2017.
 */

public class Blog {
    private String title;
    private String desc;
    private String image;
    private String price;
    private String contact;

    public Blog(){

    }

    public Blog(String title, String desc, String image, String price, String contact) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.price= price;
        this.contact= contact;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }
    public String getCost() {
        return price;
    }
    public String getContact() {
        return contact;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCost(String price) {
        this.price = price;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
}

