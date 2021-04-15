package com.doorbeen.gopal.menuadmin;

public class PhotoModel {

    private String url;
    String key;

    public PhotoModel()
    {

    }
    public PhotoModel(String url, String key) {
        this.url = url;
        this.key = key;
    }

    public PhotoModel(String downloadUrl) {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}