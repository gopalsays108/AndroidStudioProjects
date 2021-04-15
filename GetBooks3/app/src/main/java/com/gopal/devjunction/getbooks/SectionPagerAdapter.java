package com.gopal.devjunction.getbooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gopal.devjunction.fragments.LibraryFragment;
import com.gopal.devjunction.fragments.RecentReadFragment;
import com.gopal.devjunction.fragments.YourBookFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {


    public SectionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super( fm, behavior );
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new RecentReadFragment();
                /*
                    RequestFragment requestFragment  = new RequestFragment();
                    return requestFragment;  (we can do this way also)

                 */

            case 1:
                return new LibraryFragment();

            case 2:
                return new YourBookFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;  // bcz we have 3 tab or say fragment
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Recent Books";
        } else if (position == 1){
            return "Library";
        }else if(position == 2){
            return "My Books";
        }else {
            return  null;
        }
    }
}
