package com.jalvaro.velolibrary.client.asynctasks;

import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.controllers.Controller.VeloCallback;
import com.jalvaro.velolibrary.client.exceptions.VeloException;
import com.jalvaro.velolibrary.client.models.FleetVO;
import com.jalvaro.velolibrary.client.models.StationVO;

public class AddFavouriteStationAsyncTask extends VeloAsyncTask {

	private static final String TAG = AddFavouriteStationAsyncTask.class.getSimpleName();
	private int id;

	public AddFavouriteStationAsyncTask(VeloCallback callback, VeloApp mApp, int id) {
		super(callback, mApp);
		this.id = id;
	}

	@Override
	protected void doInBackgroundToImplement(String... params) throws VeloException {
		FleetVO fleetVO = getApplication().getFleetVO();
		FleetVO favFleetVO = getApplication().getFavouriteFleetVO();
		StationVO stationVO = fleetVO.getStationById(id);
		
		if (favFleetVO.getStationById(id) == null) {
			getApplication().addFavouriteStationVO(stationVO);
		} else {
			throw(new VeloException(VeloException.VELO_EXCEPTION_ALREADY_IN_DB));
		}
	}
}
