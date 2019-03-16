package com.android.blackoder.makta.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.view.profile.BookListActivity;
import com.android.blackoder.makta.view.profile.BookRequestActivity;
import com.android.blackoder.makta.view.LoginActivity;
import com.android.blackoder.makta.view.settings.SettingsActivity;
import com.android.blackoder.makta.view.profile.WishListActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class ProfileFragment extends Fragment {


    public ProfileFragment() {

    }

    private TextView tv_username, tv_email, tv_location;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Button btnSignOut = view.findViewById(R.id.button_sign_out);
        CardView card_request, card_wishlist, card_books, card_settings;

        tv_username = view.findViewById(R.id.text_view_username);
        tv_email = view.findViewById(R.id.text_view_email);
        tv_location = view.findViewById(R.id.text_view_location);
        setupUserProfile();
        card_request = view.findViewById(R.id.card_book_request);
        card_wishlist = view.findViewById(R.id.card_wish_list);
        card_books = view.findViewById(R.id.card_my_books);
        card_settings = view.findViewById(R.id.card_settings);
        card_request.setOnClickListener(v -> intentHandler(BookRequestActivity.class));
        card_wishlist.setOnClickListener(v -> intentHandler(WishListActivity.class));
        card_books.setOnClickListener(v -> intentHandler(BookListActivity.class));
        card_settings.setOnClickListener(v -> intentHandler(SettingsActivity.class));
        btnSignOut.setOnClickListener(v -> AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }));
        return view;
    }

    private void intentHandler(@NonNull Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    private void setupUserProfile() {
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser == null) {
            tv_username.append("David Odari");
            tv_email.append("davidkibodari@gmail.com");
            tv_location.append("Nairobi,Kenya");
        } else {
            tv_username.append(mFirebaseUser.getDisplayName());
            if (!mFirebaseUser.getEmail().isEmpty()) {
                tv_email.append(mFirebaseUser.getEmail());
            } else {
                tv_email.append(mFirebaseUser.getPhoneNumber());
            }
            tv_location.append("Unknown Location");
        }
    }
}
