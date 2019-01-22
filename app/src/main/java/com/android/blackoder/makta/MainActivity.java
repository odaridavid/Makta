package com.android.blackoder.makta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        setupBottomNavigation(navigation);

    }

    public void setupBottomNavigation(BottomNavigationView navigation) {
        navigation.setOnNavigationItemSelectedListener(menuItem ->
        {
            switch (menuItem.getItemId()) {
                case R.id.navigation_borrowed:
                    mTextMessage.setText(R.string.title_borrowed_books_view);
                    return true;
                case R.id.navigation_lent:
                    mTextMessage.setText(R.string.title_lent_books_view);
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_book_search_view);
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_book_search_view);
                    return true;
            }
            return false;
        });
    }

}
