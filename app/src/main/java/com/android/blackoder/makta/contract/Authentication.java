package com.android.blackoder.makta.contract;

import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created By blackcoder
 * On 13/03/19
 **/
public interface Authentication {
    interface Model {
        String userSignIn(FirebaseUser user);
    }

    interface Views {
        void displayMainViewOnSignIn(Intent intent);

        void displayMainViewOnSignUp(Intent intent);

    }

    interface Presenter {
        void onCreate(FirebaseUser firebaseUser);
    }
}
