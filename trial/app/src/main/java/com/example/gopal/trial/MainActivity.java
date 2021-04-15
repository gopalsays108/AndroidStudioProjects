package com.example.gopal.trial;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.SystemClock;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>(  );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById( R.id.listView );

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, list );

        listView.setAdapter( arrayAdapter );

      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );
      list.add( "name" );


      arrayAdapter.notifyDataSetChanged();


    }
}
