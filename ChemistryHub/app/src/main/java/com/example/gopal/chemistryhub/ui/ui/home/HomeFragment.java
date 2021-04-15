package com.example.gopal.chemistryhub.ui.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gopal.chemistryhub.R;
import com.example.gopal.chemistryhub.ui.ui.Grade12;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    SliderView sliderView;
    int TotalCounts;
    private AdView mAdview;
    //List<Image_slider_model> image_slider_model_list;


    public static HomeFragment newInstance() {

            return new HomeFragment();
    }

    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        bottomNavigationView = view.findViewById(R.id.top_navigation_bar);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                mAdview =  view.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdview.loadAd(adRequest);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdview != null){      // added this so returing to activity show adds again
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdview.loadAd(adRequest);
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.eleven_grade:
                        Toast.makeText(getActivity(), "11th grade", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.twelth_grade:
                        startActivity(new Intent(getActivity(), Grade12.class));
                        Toast.makeText(getActivity(), "12 grade", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.periodic_table:
                        Toast.makeText(getActivity(), "Wohooo", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

       // image_slider_model_list = new ArrayList<>();  used in cutoom image
        /*
        image_slider_model_list.add(new Image_slider_model(R.drawable.a));
        image_slider_model_list.add(new Image_slider_model(R.drawable.b));
        image_slider_model_list.add(new Image_slider_model(R.drawable.c));
        image_slider_model_list.add(new Image_slider_model(R.drawable.d));
        image_slider_model_list.add(new Image_slider_model(R.drawable.e));
        image_slider_model_list.add(new Image_slider_model(R.drawable.f));*/
        sliderView = requireActivity().findViewById(R.id.imageSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

        sliderView.setIndicatorSelectedColor(Color.BLUE);

        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();



        FirebaseDatabase.getInstance().getReference("ImagesLinks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Long counts = dataSnapshot.getChildrenCount();
               TotalCounts = counts.intValue();
                sliderView.setSliderAdapter(new Image_slider_adapter(HomeFragment.this ,TotalCounts  ));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
