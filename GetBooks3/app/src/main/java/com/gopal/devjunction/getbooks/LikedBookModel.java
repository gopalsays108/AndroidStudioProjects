package com.gopal.devjunction.getbooks;

public class LikedBookModel {

    String bookKey;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LikedBookModel() {

    }

    public LikedBookModel(String bookKey, String postUserId) {
        this.bookKey = bookKey;
        this.date = postUserId;
    }

    public String getBookKey() {
        return bookKey;
    }

    public void setBookKey(String bookKey) {
        this.bookKey = bookKey;
    }
}
