package com.android.blackoder.makta.view.fragments;

import android.os.AsyncTask;

/**
 * Created By blackcoder
 * On 21/03/19
 **/
class SearchAsyncTask extends AsyncTask<Void, Void, Void> {

    private IShowProgress mProgressBar;

    SearchAsyncTask(IShowProgress iShowProgress) {
        mProgressBar = iShowProgress;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.showProgressBar();
    }


    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        mProgressBar.hideProgressBar();
    }
}
