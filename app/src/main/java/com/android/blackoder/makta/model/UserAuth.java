package com.android.blackoder.makta.model;

import android.content.Context;

import com.android.blackoder.makta.contract.AuthContract;
import com.google.firebase.auth.FirebaseUser;

import static com.android.blackoder.makta.utils.Constants.ANONYMOUS;

/**
 * Created By blackcoder
 * On 13/03/19
 **/
public final class UserAuth implements AuthContract.Model {
    @Override
    public String userSignIn(FirebaseUser user) {
        if (user != null) {
            return user.getDisplayName();
        }
        return ANONYMOUS;
    }

}
