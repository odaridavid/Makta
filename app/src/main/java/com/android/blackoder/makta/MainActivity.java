package com.android.blackoder.makta;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      Setup bottom navigation
        BottomNavigationView navigation = findViewById(R.id.main_bottom_navigation);
        setupBottomNavigation(navigation);
//        Set Default title
        setActionBarTitle(R.string.title_borrowed_books_view);

    }

    private void setupBottomNavigation(BottomNavigationView navigation) {
        navigation.setOnNavigationItemSelectedListener(menuItem ->
        {
            switch (menuItem.getItemId()) {
                case R.id.navigation_borrowed:
                    setActionBarTitle(R.string.title_borrowed_books_view);
                    return true;
                case R.id.navigation_lent:
                    setActionBarTitle(R.string.title_lent_books_view);
                    return true;
                case R.id.navigation_search:
                    setActionBarTitle(R.string.title_book_search_view);
                    return true;
                case R.id.navigation_profile:
                    setActionBarTitle(R.string.title_user_profile_view);
                    return true;
            }
            return false;
        });
    }

    private void setActionBarTitle(int titleResource) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(titleResource);
    }

}
