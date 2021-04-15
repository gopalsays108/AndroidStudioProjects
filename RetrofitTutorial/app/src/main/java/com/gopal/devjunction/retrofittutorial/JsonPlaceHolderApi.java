package com.gopal.devjunction.retrofittutorial;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {
//
//    @GET("posts")
//    Call<List<Post>> getPost();


    // todo In this we have Query, We can also use Single query or multiple query
//    @GET("posts")
//    Call<List<Post>> getPost(@Query("userId") int userId);

//    @GET("posts")
//    Call<List<Post>> getPost(
//            @Query("userId") int userId,
//            @Query("_sort") String sort,
//            @Query("_order") String order
//    );

    //todo This is how we can pass null in primitive data type like int we will cahnge to wrapper class Intege
    // as Integer can be nullable
    // we can also fetch data of multiple userId as follows
    // This can be mess if we want to pass multiple userID we can do this as down Example
    /*@GET("posts")
    Call<List<Post>> getPost(
            @Query("userId") Integer userId1,
            @Query("userId") Integer userId2,
            @Query("_sort") String sort,
            @Query("_order") String order
    );*/

    //    //1st way
//    @GET("posts")
//    Call<List<Post>> getPost(
//            @Query("_sort") String sort,
//            @Query("_order") String order,
//            @Query("userId") Integer... userId1);

    //This is Call  ===>    Call<List<Post>> call = jsonPlaceHolderApi.getPost( null ,"desc",4,2,5,6);


    //1st way
    @GET("posts")
    Call<List<Post>> getPost(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    //USing QueryMap
    @GET("posts")
    Call<List<Post>> getPost(@QueryMap Map<String, String> parameters);


    //----- In this we have Passed custom ID--------
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    @POST("posts")
     Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts ")
    Call<Post> createPost(
            @Field( "userId" ) int userId,
            @Field( "title" ) String title,
            @Field( "body" ) String text
    );

    @FormUrlEncoded
    @POST("posts ")
    Call<Post> createPost(
           @FieldMap Map<String,String> field
    );

    //Used to Update the existing data when we want update whole data
    @PUT("posts/{id}")
    Call<Post> putPost(@Path( "id" ) int id, @Body Post post);

    //Used to update specific attrbutes
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path( "id" ) int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path( "id" ) int id);
}
