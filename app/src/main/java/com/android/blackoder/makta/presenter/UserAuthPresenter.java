package com.android.blackoder.makta.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.auth.FirebaseUIAuthentication;
import com.android.blackoder.makta.contract.AuthContract;
import com.android.blackoder.makta.model.UserAuth;
import com.android.blackoder.makta.view.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;

import static com.android.blackoder.makta.utils.Constants.ANONYMOUS;


/**
 * Created By blackcoder
 * On 13/03/19
 **/
public class UserAuthPresenter implements AuthContract.Presenter {

    private AuthContract.Views view;
    private AuthContract.Model model;
    private Context mContext;

    public UserAuthPresenter(AuthContract.Views view, Context context) {
        this.view = view;
        this.model = new UserAuth();
        mContext = context;
    }

    @Override
    public void onCreate(FirebaseUser firebaseUser) {
        String result = model.userSignIn(firebaseUser);
        System.out.print(result);
        Intent intent;
        if (result.contains(ANONYMOUS)) {
            intent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(true)
                    .setLogo(R.mipmap.ic_launcher)
                    .setTheme(R.style.Theme_MaktaApp)
                    .setAvailableProviders(FirebaseUIAuthentication.getProviders())
                    .build();
            view.displayMainViewOnSignUp(intent);
        } else {
            intent = new Intent(mContext, MainActivity.class);
            view.displayMainViewOnSignIn(intent);
        }
    }


}
