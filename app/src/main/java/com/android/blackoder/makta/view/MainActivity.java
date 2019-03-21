package com.android.blackoder.makta.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.databinding.ActivityMainBinding;
import com.android.blackoder.makta.view.fragments.BorrowedFragment;
import com.android.blackoder.makta.view.fragments.LentFragment;
import com.android.blackoder.makta.view.fragments.ProfileFragment;
import com.android.blackoder.makta.view.fragments.SearchFragment;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView lBottomNavigationView = mBinding.mainBottomNavigation;
        setupBottomNavigation(lBottomNavigationView);
        if (savedInstanceState == null) {
            lBottomNavigationView.setSelectedItemId(R.id.navigation_borrowed); // change to whichever id should be default
        }
    }

    private void setupBottomNavigation(BottomNavigationView navigation) {
        navigation.setOnNavigationItemSelectedListener(menuItem ->
        {
            switch (menuItem.getItemId()) {
                case R.id.navigation_borrowed:
                    setupFragment(new BorrowedFragment(), "Borrowed");
                    return true;
                case R.id.navigation_lent:
                    setupFragment(new LentFragment(), "Lent");
                    return true;
                case R.id.navigation_search:
                    setupFragment(new SearchFragment(), "Search");
                    return true;
                case R.id.navigation_profile:
                    setupFragment(new ProfileFragment(), "Profile");
                    return true;
            }
            return false;
        });
    }

    public void setupFragment(Fragment fragment, String Tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(mBinding.fragmentContainer.getId(), fragment, Tag);
        fragmentTransaction.commit();
    }

}
