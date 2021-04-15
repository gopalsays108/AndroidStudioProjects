package com.gopal.blog;

public class BlogModel {
    private String title;
    private String url;
    private String desc;
    private String date;
    private String time;
    private String imageUrl;
    private String userName;

    //empty constructor for firebase
    public BlogModel(){

    }

    public BlogModel(String title, String url, String desc, String date, String time, String imageUrl , String userName) {
        this.title = title;
        this.url = url;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.imageUrl = imageUrl;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
