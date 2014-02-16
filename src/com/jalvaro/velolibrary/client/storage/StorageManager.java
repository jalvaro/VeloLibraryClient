package com.jalvaro.velolibrary.client.storage;

import com.jalvaro.velolibrary.client.exceptions.VeloException;
import com.jalvaro.velolibrary.client.models.FleetVO;
import com.jalvaro.velolibrary.client.models.StationVO;

public abstract class StorageManager {
	
	public abstract FleetVO getFleet() throws VeloException;
	
	public abstract void saveFleet(FleetVO fleet) throws VeloException;

	public abstract StationVO getStation(int id) throws VeloException;

	public abstract void saveStation(StationVO stationVO) throws VeloException;

	public abstract void deleteStation(int id) throws VeloException;

	public abstract FleetVO getFavouriteFleet() throws VeloException;

	public abstract void saveFavouriteFleet(FleetVO fleetVO) throws VeloException;

	public abstract StationVO getFavouriteStation(int id) throws VeloException;

	public abstract void saveFavouriteStation(StationVO stationVO) throws VeloException;

	public abstract void deleteFavouriteStation(StationVO stationVO) throws VeloException;

}
