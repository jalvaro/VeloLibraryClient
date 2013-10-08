package com.jalvaro.velobleu.client.asynctasks;

import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.controllers.Controller.VeloCallback;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

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
