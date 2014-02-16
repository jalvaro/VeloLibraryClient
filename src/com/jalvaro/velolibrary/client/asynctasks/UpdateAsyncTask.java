package com.jalvaro.velolibrary.client.asynctasks;

import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.controllers.Controller.VeloCallback;
import com.jalvaro.velolibrary.client.exceptions.VeloException;
import com.jalvaro.velolibrary.client.io.Api;

public class UpdateAsyncTask extends VeloAsyncTask {

	private static final String TAG = UpdateAsyncTask.class.getSimpleName();

	public UpdateAsyncTask(VeloCallback callback, VeloApp mApp) {
		super(callback, mApp);
	}

	@Override
	protected void doInBackgroundToImplement(String... params) throws VeloException {
		Api veloApi = ((VeloApp) getApplication()).getVeloApi();
		getApplication().setFleetVO(veloApi.getFleet());
	}
}
