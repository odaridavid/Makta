package com.android.blackoder.makta.contract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created By blackcoder
 * On 13/03/19
 **/
public interface AuthContract {
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
