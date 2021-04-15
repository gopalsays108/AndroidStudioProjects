package com.example.gopal.menuuser;

import android.text.Editable;

public class Category_model {

    private String url;
    private String name;
    private String address;
    private String city;
    String key;
    //private String sets;



    public Category_model() {

    }

    public Category_model(Editable text, String toString, String toString1, String toString2, String downloadUrl, String s) {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Category_model(String url, String name, String address, String city , String key) {
        this.url = url;
        this.name = name;
        this.address = address;
        this.city = city;
        this.key = key;
       // this.sets = sets;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    //public String getSets() {
     //   return sets;
   // }

   /* public void setSets(String sets) {
        this.sets = sets;
    }*/
}

