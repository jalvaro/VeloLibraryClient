package com.jalvaro.velobleu.client.storage;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

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

	public abstract void deleteFavouriteStation(int id) throws VeloException;

}
