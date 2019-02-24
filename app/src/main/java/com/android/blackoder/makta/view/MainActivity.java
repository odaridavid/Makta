package com.android.blackoder.makta.view;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.view.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      Setup bottom navigation
        BottomNavigationView navigation = findViewById(R.id.main_bottom_navigation);
        setupBottomNavigation(navigation);

    }

    private void setupBottomNavigation(BottomNavigationView navigation) {
        navigation.setOnNavigationItemSelectedListener(menuItem ->
        {
            switch (menuItem.getItemId()) {
                case R.id.navigation_borrowed:
                    return true;
                case R.id.navigation_lent:
                    return true;
                case R.id.navigation_search:
                    return true;
                case R.id.navigation_profile:
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new ProfileFragment());
                    ft.commit();
                    return true;
            }
            return false;
        });
    }


}
