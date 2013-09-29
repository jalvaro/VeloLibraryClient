package com.jalvaro.velobleu.client.storage;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

public class PreferencesManager extends StorageManager{

	public PreferencesManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FleetVO getFleet() throws VeloException {
		// TODO Auto-generated method stub
		return new FleetVO();
	}

	@Override
	public void saveFleet(FleetVO fleet) throws VeloException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StationVO getStation(int id) throws VeloException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveStation(StationVO stationVO) throws VeloException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FleetVO getFavouriteFleet() throws VeloException {
		// TODO Auto-generated method stub
		return new FleetVO();
	}

	@Override
	public void saveFavouriteFleet(FleetVO fleetVO) throws VeloException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StationVO getFavouriteStation(int id) throws VeloException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveFavouriteStation(StationVO stationVO) throws VeloException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStation(int id) throws VeloException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFavouriteStation(int id) throws VeloException {
		// TODO Auto-generated method stub
		
	}

}
