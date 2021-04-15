package com.gopal.photoblog;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class BlogPostIdModel {

    @Exclude
    public String BlogPostId;

    public <T extends BlogPostIdModel> T withId(@NonNull final String id) {
        this.BlogPostId = id;
        return (T) this;
    }
}
