package com.gopal.devjunction.splashscreenandonboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gopal.devjunction.getbooks.R;

import java.util.List;
public class IntroPagerAdapter extends PagerAdapter {

    Context context;
    List<ScreenTime> screenItems;

    public IntroPagerAdapter(Context context, List<ScreenTime> screenItems) {
        this.context = context;
        this.screenItems = screenItems;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layoutScreen = inflater.inflate( R.layout.layout_screen, null );

        ImageView imageView = layoutScreen.findViewById( R.id.intro_image );
        TextView title = layoutScreen.findViewById( R.id.intro_title );
        TextView description = layoutScreen.findViewById( R.id.intro_desc );

        title.setText( screenItems.get( position ).getTitle() );
        description.setText( screenItems.get( position ).getDescription() );
        imageView.setImageResource( screenItems.get( position ).getScreenImg() );

        container.addView( layoutScreen );
        return layoutScreen;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem (@NonNull ViewGroup container,int position, @NonNull Object object){

        container.removeView( (View) object );
    }

    @Override
    public int getCount() {
        return screenItems.size();
    }

}
