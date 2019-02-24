package com.android.blackoder.makta.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.blackoder.makta.R;

/**
 * Created By blackcoder
 * On 30/01/19
 **/
public final class BorrowedFragment extends Fragment {
    public BorrowedFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_borrowed, container, false);
    }
}
