package com.doorbeen.gopal.menuadmin.cafe;

public class CafeModel {
    private String url;
    private String name;
    private String address;
    private String city;

    public CafeModel(){

    }

    public CafeModel(String s, String sds, String ds, String sd, String sds1) {
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

    public CafeModel(String url, String name, String address, String city) {
        this.url = url;
        this.name = name;
        this.address = address;
        this.city = city;
    }
}
