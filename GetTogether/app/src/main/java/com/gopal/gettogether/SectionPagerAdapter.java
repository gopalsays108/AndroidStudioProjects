package com.gopal.gettogether;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gopal.gettogether.tabFragment.ChatsFragment;
import com.gopal.gettogether.tabFragment.FriendsFragment;
import com.gopal.gettogether.tabFragment.RequestFragment;

class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super( fm, behavior );
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new RequestFragment();
                /*
                    RequestFragment requestFragment  = new RequestFragment();
                    return requestFragment;  (we can do this way also)

                 */
            case 1:
                return new ChatsFragment();

            case 2:
                return new FriendsFragment();

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
           return "Requests";
       } else if (position == 1){
           return "Chats";
       }else if(position == 2){
           return "Friends";
       }else {
           return  null;
       }
    }
}
