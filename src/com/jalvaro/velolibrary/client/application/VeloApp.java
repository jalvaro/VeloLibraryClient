package com.jalvaro.velolibrary.client.application;

import android.app.Application;
import android.util.Log;

import com.jalvaro.velolibrary.client.exceptions.VeloException;
import com.jalvaro.velolibrary.client.io.Api;
import com.jalvaro.velolibrary.client.models.FleetVO;
import com.jalvaro.velolibrary.client.models.StationVO;
import com.jalvaro.velolibrary.client.storage.DatabaseManager;
import com.jalvaro.velolibrary.client.storage.StorageManager;

public class VeloApp extends Application {
	private static final String TAG = VeloApp.class.getSimpleName();
	private FleetVO fleetVO;
	private FleetVO favouriteFleetVO;
	private StorageManager storeManager;
	private Api veloApi;

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
		/*
		 * new AsyncTask<FleetVO, Integer, Integer>() {
		 * 
		 * @Override
		 * protected Integer doInBackground(FleetVO... fleetVO) {
		 * try {
		 * storeManager.saveFleet(fleetVO[0]);
		 * VeloApp.this.fleetVO = storeManager.getFleet();
		 * } catch (VeloException e) {
		 * // TODO Auto-generated catch block
		 * e.printStackTrace();
		 * }
		 * return null;
		 * }
		 * }.execute(fleetVO);
		 */
		this.fleetVO = fleetVO;
		storeManager.saveFleet(fleetVO);
		//this.fleetVO = storeManager.getFleet();

		Log.d(TAG, "setFleet --> " + this.fleetVO.getStations().length);
	}

	public FleetVO getFavouriteFleetVO() {
		return favouriteFleetVO;
	}

	public void setFavouriteFleetVO(FleetVO favouriteFleetVO) throws VeloException {
		this.favouriteFleetVO = favouriteFleetVO;
		storeManager.saveFavouriteFleet(favouriteFleetVO);
	}

	public void addFavouriteStationVO(StationVO stationVO) throws VeloException {
		stationVO.setFavourite(true);
		fleetVO.getStationById(stationVO.getId()).setFavourite(true);
		favouriteFleetVO.addStation(stationVO);
		storeManager.saveFavouriteStation(stationVO);
		//this.favouriteFleetVO = storeManager.getFavouriteFleet();
	}

	public void deleteFavouriteStationVO(StationVO stationVO) throws VeloException {
		if (stationVO != null) {
			stationVO.setFavourite(false);
			fleetVO.getStationById(stationVO.getId()).setFavourite(false);
			favouriteFleetVO.deleteStation(stationVO);
			storeManager.deleteFavouriteStation(stationVO);
			//this.favouriteFleetVO = storeManager.getFavouriteFleet();
		}
	}
	
	public Api getVeloApi() {
		return veloApi;
	}
	
	public void setVeloApi(Api veloApi) {
		this.veloApi = veloApi;
	}
}
