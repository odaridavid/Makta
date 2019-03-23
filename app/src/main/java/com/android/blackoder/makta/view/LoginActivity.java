package com.android.blackoder.makta.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.contract.Authentication;
import com.android.blackoder.makta.presenter.UserAuthPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.android.blackoder.makta.utils.Constants.RC_SIGN_IN;

public final class LoginActivity extends AppCompatActivity implements Authentication.Views {
    private FirebaseAuth mFirebaseAuthentication;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserAuthPresenter lUserAuthPresenter = new UserAuthPresenter(this, this);
        mFirebaseAuthentication = FirebaseAuth.getInstance();
        //        Firebase UI Authentication
        mAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            lUserAuthPresenter.onCreate(user);
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuthentication.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuthentication.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }


    @Override
    public void displayMainViewOnSignIn(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void displayMainViewOnSignUp(Intent intent) {
        startActivityForResult(intent, RC_SIGN_IN);
    }

}