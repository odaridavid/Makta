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

import com.android.blackoder.makta.databinding.FragmentProfileBinding;
import com.android.blackoder.makta.view.LoginActivity;
import com.android.blackoder.makta.view.BookListActivity;
import com.android.blackoder.makta.view.BookRequestActivity;
import com.android.blackoder.makta.view.WishListActivity;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBinding lProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        setupUserProfile(lProfileBinding);
        lProfileBinding.textViewBookRequest.setOnClickListener(v -> intentHandler(BookRequestActivity.class));
        lProfileBinding.textViewWishList.setOnClickListener(v -> intentHandler(WishListActivity.class));
        lProfileBinding.textViewMyBooks.setOnClickListener(v -> intentHandler(BookListActivity.class));
        lProfileBinding.textViewSignOut.setOnClickListener(v -> AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }));
        return lProfileBinding.getRoot();
    }

    private void intentHandler(@NonNull Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    private void setupUserProfile(FragmentProfileBinding fragmentProfileBinding) {
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView username = fragmentProfileBinding.textViewUsername;
        TextView contact = fragmentProfileBinding.textViewEmail;
        if (mFirebaseUser == null) {
            username.append("Anonymous");
            contact.append("random@host.com");
            fragmentProfileBinding.textViewLocation.append("Nairobi,Kenya");
        } else {
            username.append(mFirebaseUser.getDisplayName());
            if (!mFirebaseUser.getEmail().isEmpty()) {
                contact.append(mFirebaseUser.getEmail());
            } else {
                contact.append(mFirebaseUser.getPhoneNumber());
            }
            checkPermissions(fragmentProfileBinding);
        }
    }

    private void checkPermissions(FragmentProfileBinding fragmentProfileBinding) {
        Dexter.withActivity(getActivity())
                .withPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        fragmentProfileBinding.textViewLocation.append("Granted Location");
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of any permission
                        if (response.isPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            fragmentProfileBinding.textViewLocation.append("Unknown Location");
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
