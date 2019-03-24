package com.android.blackoder.makta.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.blackoder.makta.R;
import com.android.blackoder.makta.model.books.FirestoreViewModel;
import com.android.blackoder.makta.model.entities.SharedBook;
import com.android.blackoder.makta.utils.AppUtils;

import org.parceler.Parcels;

/**
 * Created By blackcoder
 * On 22/03/19
 **/

public final class RequestDetailFragment extends Fragment {

    private FirestoreViewModel mFirestoreViewModel;
    private String mUid;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_request, container, false);
        Button lBtnRequest = v.findViewById(R.id.button_request_book);

        mFirestoreViewModel = ViewModelProviders.of(this).get(FirestoreViewModel.class);
        if (getArguments() != null) {
            SharedBook lSharedBook = Parcels.unwrap(getArguments().getParcelable("book"));
            mUid = mFirestoreViewModel.getFirebaseUser().getUid();
            if (lSharedBook != null && lSharedBook.getUser().equals(mUid)) {
                lBtnRequest.setEnabled(false);
                lBtnRequest.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                lBtnRequest.setText(getString(R.string.from_peronal_collection));
            }
            AppUtils.bindBookView(lSharedBook, v);
            lBtnRequest.setOnClickListener(v1 -> {
                if (lSharedBook != null)
                    sendRequest(lSharedBook.getUser(), lSharedBook.getTitle());
            });
        }
        return v;
    }

    private void sendRequest(String userId, String title) {
        if (!mUid.equals(userId)) {
            mFirestoreViewModel.borrowBook(userId, title);
            Toast.makeText(getContext(), getString(R.string.request_sent), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), getString(R.string.request_not_sent), Toast.LENGTH_LONG).show();
        }
    }

}
