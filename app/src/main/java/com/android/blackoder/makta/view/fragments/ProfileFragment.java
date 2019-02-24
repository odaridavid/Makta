package com.android.blackoder.makta.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.blackoder.makta.R;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
