package com.doorbeen.gopal.menuadmin;

public class DishesModel {

    private String url;
    private String dish;
    private String type;
    private String price;
    private String keyRes;
    String key;
   // private String sets;


    public DishesModel(){

    }


    public DishesModel(String url, String dish, String type, String price,String key ,String keyRes) {
        this.url = url;
        this.dish = dish;
        this.type = type;
        this.price = price;
        this.key = key;
        this.keyRes = keyRes;

    }

    public String getKeyRes() {
        return keyRes;
    }

    public void setKeyRes(String keyRes) {
        this.keyRes = keyRes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
/*
    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }*/
}
