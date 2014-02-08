package com.jalvaro.velobleu.client.asynctasks;

import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.controllers.Controller.VeloCallback;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.io.Api;

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
