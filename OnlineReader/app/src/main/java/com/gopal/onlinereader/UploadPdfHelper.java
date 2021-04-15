package com.gopal.onlinereader;

public class UploadPdfHelper {

    String name;
    String url;

    public UploadPdfHelper(){

    }

    public UploadPdfHelper(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
