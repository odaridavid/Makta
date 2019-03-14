package com.android.blackoder.makta.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.view.LoginActivity;
import com.firebase.ui.auth.AuthUI;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class ProfileFragment extends Fragment {

    private Button btnSignOut;

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnSignOut = view.findViewById(R.id.button_sign_out);
        btnSignOut.setOnClickListener(v -> AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(task -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }));
        return view;
    }

}
