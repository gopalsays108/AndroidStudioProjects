package com.gopal.devjunction.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide / knows how to create the layout for each list
 * based on a data source, which is a list of {@link EarthquakeModel} objects.
 * */

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeModel> {

    private ArrayList<EarthquakeModel> earthquakeModels;

    /**
     * constructs a new {@link EarthquakeAdapter}
     *
     * @param context          of the app
     * @param earthquakeModels is the list of the earthquake which is the data source of the adapter
     */
    public EarthquakeAdapter(@NonNull Context context, List<EarthquakeModel> earthquakeModels) {
        super( context, 0, earthquakeModels );
    }

    /**
     * @param position    of the earthquake from the list
     * @param convertView
     * @param parent
     * @return the list of the item view that display the info of the earthquake at the givn position
     * of the list of earthquake.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view ( called convertView ) that we can use ,
        // otherwise , if the convert view is null then inflate the new list item layout
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from( getContext() ).inflate( R.layout.list_item, parent, false );
        }

        //Find the EarthQuake at the give position in the list of earthquakes
        final EarthquakeModel currentEarthQuake = getItem( position );

        // View in the {@link list_item.xml}
        TextView magnitudeTxtView = listItemView.findViewById( R.id.magnitude );
        TextView placeTxtView = listItemView.findViewById( R.id.place );
        TextView dateTxtView = listItemView.findViewById( R.id.dateAndTime );
        TextView direction = listItemView.findViewById( R.id.direction );

        if (currentEarthQuake != null) {
            DecimalFormat decimalFormat = new DecimalFormat( "0.0" );
            String output = decimalFormat.format( currentEarthQuake.getMagn() );

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTxtView.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(currentEarthQuake.getMagn());
            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

            magnitudeTxtView.setText( output );
            placeTxtView.setText( currentEarthQuake.getPlace() );
            dateTxtView.setText( currentEarthQuake.getDate() );
            if (!(currentEarthQuake.getDirection() == null)) {
                direction.setVisibility( View.VISIBLE);
                direction.setText( currentEarthQuake.getDirection() );
            }else{
                direction.setVisibility( View.GONE );
            }

//            listItemView.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthQuake.getUrl()));
//                    finalListItemView.getContext().startActivity(browserIntent);
//                }
//            } );

        }
        return listItemView;
    }

    /**
     * Return the color for the magnitude circle based on the intensity of the earthquake.
     *
     * @param magn of the earthquake
     */
    private int getMagnitudeColor(double magn) {

        int  magnitudeColorResourceId;

        /*Once we find the right color resource ID, we still have one more step to convert it into
        an actual color value. Remember that color resource IDs just point to the resource we defined,
         but not the value of the color. For example, R.layout.earthquake_list_item is a reference to
         tell us where the layout is located. It’s just a number, not the full XML layout.
        * */

        int magnitudeFloor = (int) Math.floor(magn);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor( getContext() , magnitudeColorResourceId );
    }
}









