package com.android.blackoder.makta.model;

import com.android.blackoder.makta.contract.Authentication;
import com.google.firebase.auth.FirebaseUser;

import static com.android.blackoder.makta.utils.Constants.ANONYMOUS;

/**
 * Created By blackcoder
 * On 13/03/19
 **/
public final class UserAuth implements Authentication.Model {
    @Override
    public String userSignIn(FirebaseUser user) {
        if (user != null) {
            return user.getDisplayName();
        }
        return ANONYMOUS;
    }

}
