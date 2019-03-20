package com.android.blackoder.makta.view.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.view.LoginActivity;
import com.android.blackoder.makta.view.profile.BookListActivity;
import com.android.blackoder.makta.view.profile.BookRequestActivity;
import com.android.blackoder.makta.view.profile.WishListActivity;
import com.android.blackoder.makta.view.settings.SettingsActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


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
        TextView tvRequest, tvWishlist, tvBooks, tvSettings, tvSignOut;
        tvSignOut = view.findViewById(R.id.text_view_sign_out);
        tv_username = view.findViewById(R.id.text_view_username);
        tv_email = view.findViewById(R.id.text_view_email);
        tv_location = view.findViewById(R.id.text_view_location);
        setupUserProfile();
        tvRequest = view.findViewById(R.id.text_view_book_request);
        tvWishlist = view.findViewById(R.id.text_view_wish_list);
        tvBooks = view.findViewById(R.id.text_view_my_books);
        tvSettings = view.findViewById(R.id.text_view_settings);
        tvRequest.setOnClickListener(v -> intentHandler(BookRequestActivity.class));
        tvWishlist.setOnClickListener(v -> intentHandler(WishListActivity.class));
        tvBooks.setOnClickListener(v -> intentHandler(BookListActivity.class));
        tvSettings.setOnClickListener(v -> intentHandler(SettingsActivity.class));
        tvSignOut.setOnClickListener(v -> AuthUI.getInstance()
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
            checkPermissions();
        }
    }

    private void checkPermissions() {
        Dexter.withActivity(getActivity())
                .withPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        tv_location.append("Granted Location");
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of any permission
                        if (response.isPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            tv_location.append("Unknown Location");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(error -> Log.e("Dexter", "There was an error: " + error.toString()))
                .onSameThread()
                .check();
    }
}
