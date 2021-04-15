package com.gopal.devjunction.hd4kwallpaper;

import android.graphics.Bitmap;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WallpaperModel {

    String photographerName;
    String photographer_url;
    String originalImageSize;
    String smallImageSize;
    String landscapeImage;
    String photoUrl;

    public WallpaperModel(String photographerName, String photographer_url, String originalImageSize,
                          String smallImageSize, String landscapeImage , String photoUrl) {

        this.photographerName = photographerName;
        this.photographer_url = photographer_url;
        this.originalImageSize = originalImageSize;
        this.smallImageSize = smallImageSize;
        this.landscapeImage = landscapeImage;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public String getPhotographer_url() {
        return photographer_url;
    }

    public void setPhotographer_url(String photographer_url) {
        this.photographer_url = photographer_url;
    }

    public String getOriginalImageSize() {
        return originalImageSize;
    }

    public void setOriginalImageSize(String originalImageSize) {
        this.originalImageSize = originalImageSize;
    }

    public String getSmallImageSize() {
        return smallImageSize;
    }

    public void setSmallImageSize(String smallImageSize) {
        this.smallImageSize = smallImageSize;
    }

    public String getLandscapeImage() {
        return landscapeImage;
    }

    public void setLandscapeImage(String landscapeImage) {
        this.landscapeImage = landscapeImage;
    }
}
