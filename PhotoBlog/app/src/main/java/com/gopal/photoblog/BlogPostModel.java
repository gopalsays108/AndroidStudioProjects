package com.gopal.photoblog;

import java.sql.Timestamp;

public class BlogPostModel extends BlogPostIdModel {

    public String userId;
    public String imageUrl;
    public String desc;
    public String time;

    public BlogPostModel() {

    }

    public BlogPostModel(String userId, String imageUrl, String desc, String time) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.desc = desc;
        this.time = time;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
