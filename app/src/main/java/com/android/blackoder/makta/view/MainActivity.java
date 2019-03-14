package com.android.blackoder.makta.view;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.view.fragments.BorrowedFragment;
import com.android.blackoder.makta.view.fragments.LentFragment;
import com.android.blackoder.makta.view.fragments.ProfileFragment;
import com.android.blackoder.makta.view.fragments.SearchFragment;


public class MainActivity extends AppCompatActivity {

    FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.main_bottom_navigation);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.fragment_container, new BorrowedFragment());
        mFragmentTransaction.commit();
        setupBottomNavigation(navigation);
    }

    private void setupBottomNavigation(BottomNavigationView navigation) {
        navigation.setOnNavigationItemSelectedListener(menuItem ->
        {
            switch (menuItem.getItemId()) {
                case R.id.navigation_borrowed:
                    setupFragment(new BorrowedFragment());
                    return true;
                case R.id.navigation_lent:
                    setupFragment(new LentFragment());
                    return true;
                case R.id.navigation_search:
                    setupFragment(new SearchFragment());
                    return true;
                case R.id.navigation_profile:
                    setupFragment(new ProfileFragment());
                    return true;
            }
            return false;
        });
    }

    public void setupFragment(Fragment fragment) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, fragment);
        mFragmentTransaction.commit();
    }

}
