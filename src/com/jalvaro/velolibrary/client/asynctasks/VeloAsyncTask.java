package com.jalvaro.velolibrary.client.asynctasks;

import android.os.AsyncTask;
import android.os.Message;

import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.controllers.Controller.VeloCallback;
import com.jalvaro.velolibrary.client.exceptions.VeloException;

public abstract class VeloAsyncTask extends AsyncTask<String, Integer, Integer> {
	public static final int OK = 1;
	public static final int KO = -1;

	private VeloCallback mCallback;
	VeloException mException;
	private VeloApp mApp;

	public VeloAsyncTask(VeloCallback callback, VeloApp mApp) {
		super();
		this.mCallback = callback;
		this.mApp = mApp;
	}

	protected abstract void doInBackgroundToImplement(String... params) throws VeloException;

	@Override
	protected Integer doInBackground(String... params) {
		try {
			doInBackgroundToImplement(params);
			return OK;
		} catch (VeloException e) {
			this.mException = e;
			return KO;
		}
	}

	@Override
	protected void onPostExecute(Integer result) {

		switch (result) {
		case OK:
			mCallback.response(new Message());
			break;
		case KO:
			mCallback.error(mException);
			break;
		}
		super.onPostExecute(result);
	}

	public VeloApp getApplication() {
		return mApp;
	}
}
