package com.jalvaro.velolibrary.client.asynctasks;

import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.controllers.Controller.VeloCallback;
import com.jalvaro.velolibrary.client.exceptions.VeloException;
import com.jalvaro.velolibrary.client.models.FleetVO;
import com.jalvaro.velolibrary.client.models.StationVO;

public class DeleteFavouriteStationAsyncTask extends VeloAsyncTask {

	private static final String TAG = DeleteFavouriteStationAsyncTask.class.getSimpleName();
	private int id;

	public DeleteFavouriteStationAsyncTask(VeloCallback callback, VeloApp mApp, int id) {
		super(callback, mApp);
		this.id = id;
	}

	@Override
	protected void doInBackgroundToImplement(String... params) throws VeloException {
		FleetVO favFleetVO = getApplication().getFavouriteFleetVO();
		StationVO stationVO = favFleetVO.getStationById(id);
		
		getApplication().deleteFavouriteStationVO(stationVO);
	}
}
