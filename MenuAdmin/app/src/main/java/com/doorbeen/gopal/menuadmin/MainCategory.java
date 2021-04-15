package com.doorbeen.gopal.menuadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.doorbeen.gopal.menuadmin.cafe.CafeCategory;

public class MainCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_category );

        CardView categoryCard = findViewById( R.id.category_card_view );
        CardView categotyCafe = findViewById( R.id.cafe_card_view );

        categoryCard.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( MainCategory.this , CategoryActivity.class ) );
            }
        } );

        categotyCafe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( MainCategory.this , CafeCategory.class ) );
            }
        } );


    }
}
