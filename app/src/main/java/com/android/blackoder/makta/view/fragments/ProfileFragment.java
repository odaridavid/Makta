package com.android.blackoder.makta.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.databinding.FragmentProfileBinding;
import com.android.blackoder.makta.view.BookListActivity;
import com.android.blackoder.makta.view.BookRequestActivity;
import com.android.blackoder.makta.view.LoginActivity;
import com.android.blackoder.makta.view.WishListActivity;
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
                    Toast.makeText(getActivity(), getString(R.string.signed_out), Toast.LENGTH_LONG).show();
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
            username.append(getString(R.string.anonymous));
            contact.append(getString(R.string.anonymous_email));
        } else {
            username.append(mFirebaseUser.getDisplayName());
            if (!mFirebaseUser.getEmail().isEmpty()) {
                contact.append(mFirebaseUser.getEmail());
            } else {
                contact.append(mFirebaseUser.getPhoneNumber());
            }
        }
    }


}
