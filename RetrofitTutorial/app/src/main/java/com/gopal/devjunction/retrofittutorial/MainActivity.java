package com.gopal.devjunction.retrofittutorial;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    private static final String TAG = "TAG";
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        resultText = findViewById( R.id.viewResult );

        // Pas this gson object into GsonConverterFactory to get null in Patch Request
        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor( loggingInterceptor )
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "https://jsonplaceholder.typicode.com/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .client( okHttpClient )
                .build();

        jsonPlaceHolderApi = retrofit.create( JsonPlaceHolderApi.class );

        // getPosts();
        // getComments();
        // createPost();
        updatePost();
        //patchPost();
        //deletePost();
    }

    private void getPosts() {
        // We we do not want to sort or add any parameter we can pass null instead of id and desc so we dont
        // dont need to create seprate getPost(), getPost(3, null, null);
        // It only works with String, it doesnt work with primitive data type like int, to do we need to change primitve to wrapper
        // class.

        // Here creating HashMap for QueryMap
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "userId", "2" );
        parameters.put( "_order", "desc" );
        parameters.put( "_sort", "id" );

//        Call<List<Post>> call = jsonPlaceHolderApi.getPost(new Integer[]{1,4,5}, "id","desc");
        Call<List<Post>> call = jsonPlaceHolderApi.getPost( parameters );
        call.enqueue( new Callback<List<Post>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    resultText.setText( "Error Code: " + response.code() );
                    return;
                }

                Log.i( TAG, "onResponse: " + response.code() );
                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User Id: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    resultText.append( content );
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                resultText.setText( t.getLocalizedMessage() );
            }
        } );
    }

    private void getComments() {
        Call<List<com.gopal.devjunction.retrofittutorial.Comment>> call = jsonPlaceHolderApi.getComments( 4 );

        //In this we can either pass whole URL or relative Url "posts/5/comments"
//        Call<List<com.gopal.devjunction.retrofittutorial.Comment> > call = jsonPlaceHolderApi
//                .getComments("https://jsonplaceholder.typicode.com/posts/5/comments");

        call.enqueue( new Callback<List<com.gopal.devjunction.retrofittutorial.Comment>>() {
            @Override
            public void onResponse(Call<List<com.gopal.devjunction.retrofittutorial.Comment>> call, Response<List<com.gopal.devjunction.retrofittutorial.Comment>> response) {
                if (!response.isSuccessful()) {
                    resultText.setText( "Code: " + response.code() );
                    return;
                }

                List<com.gopal.devjunction.retrofittutorial.Comment> list = response.body();

                for (com.gopal.devjunction.retrofittutorial.Comment comment : list) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";
                    resultText.append( content );
                }
            }

            @Override
            public void onFailure(Call<List<com.gopal.devjunction.retrofittutorial.Comment>> call, Throwable t) {

            }
        } );
    }

    private void createPost() {
        Post post = new Post( 23, "New Ttile", "new Text" );

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put( "userId", "234" );
        fieldMap.put( "title", "Rahul" );

        // Call<Post> call =jsonPlaceHolderApi.createPost( post );
//        Call<Post> call =jsonPlaceHolderApi.createPost( 23,"Gopal","Success" );
        Call<Post> call = jsonPlaceHolderApi.createPost( fieldMap );
        call.enqueue( new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.i( TAG, "onResponse: " + response.code() );
                if (response.isSuccessful()) {
                    Post post1 = response.body();

                    String content = "";
                    content += "ID: " + post1.getId() + "\n";
                    content += "User Id: " + post1.getUserId() + "\n";
                    content += "Title: " + post1.getTitle() + "\n";
                    content += "Text: " + post1.getText() + "\n\n";

                    resultText.setText( content );

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        } );
    }

    private void updatePost() {
        Post post = new Post( 2, null, "GOPAL is android Developer" );

        Call<Post> call = jsonPlaceHolderApi.putPost( 5, post );

        call.enqueue( new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    resultText.setText( String.valueOf( response.code() ) );
                    return;
                }
                Post post1 = response.body();

                if (post1 != null) {
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "Id: " + post1.getId() + "\n";
                    content += "User ID: " + post1.getUserId() + "\n";
                    content += "Title: " + post1.getTitle() + "\n";
                    content += "Text: " + post1.getText();

                    resultText.setText( content );
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        } );
    }

    /**
     * IN this We did not repalces the whole body, we will null for the attribute we did not want to change.
     * If anyway we want to pass null in the PatchRequest we can do by manupulation gason as done above;
     */
    private void patchPost() {
        Post post = new Post( 2, null, "GOPAL is android Developer" );

        Call<Post> call = jsonPlaceHolderApi.patchPost( 5, post );

        call.enqueue( new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    resultText.setText( String.valueOf( response.code() ) );
                    return;
                }
                Post post1 = response.body();

                if (post1 != null) {
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "Id: " + post1.getId() + "\n";
                    content += "User ID: " + post1.getUserId() + "\n";
                    content += "Title: " + post1.getTitle() + "\n";
                    content += "Text: " + post1.getText();

                    resultText.setText( content );
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        } );
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost( 1 );
        call.enqueue( new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                resultText.setText( String.valueOf( response.code() ) );
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultText.setText( t.getMessage() );
            }
        } );
    }
}