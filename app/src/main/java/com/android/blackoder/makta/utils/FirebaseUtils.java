package com.android.blackoder.makta.utils;


import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

/**
 * Created By blackcoder
 * On 13/03/19
 **/
public final class FirebaseUtils {
    public static List<AuthUI.IdpConfig> getProviders() {
        return Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
    }


}
