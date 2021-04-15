package com.gopal.gettogether;

public class UsersModel {

    public String image;
    public String name;
    public String status;

    //for firebase
    public UsersModel(){

    }

    public UsersModel(String image, String name, String status) {
        this.image = image;
        this.name = name;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
