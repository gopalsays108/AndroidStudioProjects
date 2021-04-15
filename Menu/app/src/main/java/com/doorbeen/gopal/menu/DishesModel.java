package com.doorbeen.gopal.menu;

public class DishesModel {

    private String url;
    private String dish;
    private String type;
    private String price;

    // private String sets;

    public DishesModel() {

    }

    public DishesModel(String url, String dish, String type, String price) {
        this.url = url;
        this.dish = dish;
        this.type = type;
        this.price = price;
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
}