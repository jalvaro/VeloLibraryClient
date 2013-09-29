package com.jalvaro.velobleu.client.application;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.storage.DatabaseManager;
import com.jalvaro.velobleu.client.storage.StorageManager;

public class VeloApp extends Application {
	private static final String TAG = VeloApp.class.getSimpleName();
	private FleetVO fleetVO;
	private FleetVO favouriteFleetVO;
	private StorageManager storeManager;

	public VeloApp() {
		super();
		storeManager = new DatabaseManager(this);
	}

	private void initFleets() {
		if (fleetVO == null || favouriteFleetVO == null) {
			try {
				fleetVO = storeManager.getFleet();
				favouriteFleetVO = storeManager.getFavouriteFleet();
			} catch (VeloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public FleetVO getFleetVO() {
		if (fleetVO != null) {
			Log.d(TAG, "getFleet --> Fleet: " + fleetVO.getStations().length);
		} else {
			Log.d(TAG, "getFleet --> fleetVO is null");
		}

		if (favouriteFleetVO != null) {
			Log.d(TAG, "getFleet --> FavouriteFleet: " + favouriteFleetVO.getStations().length);
		} else {
			Log.d(TAG, "getFleet --> favouriteFleetVO is null");
		}
		initFleets();
		return fleetVO;
	}

	public void setFleetVO(FleetVO fleetVO) throws VeloException {
		Log.d(TAG, "setFleet --> " + fleetVO.getStations().length);
		new AsyncTask<FleetVO, Integer, Integer>() {

			@Override
			protected Integer doInBackground(FleetVO... fleetVO) {
				try {
					storeManager.saveFleet(fleetVO[0]);
				} catch (VeloException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}.execute(fleetVO);

		this.fleetVO = fleetVO;
		Log.d(TAG, "setFleet --> " + this.fleetVO.getStations().length);
	}

	public FleetVO getFavouriteFleetVO() {
		return favouriteFleetVO;
	}

	public void setFavouriteFleetVO(FleetVO favouriteFleetVO) throws VeloException {
		storeManager.saveFavouriteFleet(favouriteFleetVO);
		this.favouriteFleetVO = favouriteFleetVO;
	}
}
