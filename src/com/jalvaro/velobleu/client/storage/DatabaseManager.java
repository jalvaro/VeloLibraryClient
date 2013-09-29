package com.jalvaro.velobleu.client.storage;

import java.util.List;

import android.content.Context;

import com.jalvaro.velobleu.client.db.FavouriteStationDAO;
import com.jalvaro.velobleu.client.db.FleetDAO;
import com.jalvaro.velobleu.client.db.StationDAO;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

public class DatabaseManager extends StorageManager {

	private StationDAO stationDAO;
	private FavouriteStationDAO favouriteStationDAO;
	private FleetDAO fleetDAO;

	public DatabaseManager(Context context) {
		stationDAO = StationDAO.getInstanced(context);
		favouriteStationDAO = FavouriteStationDAO.getInstanced(context);
		fleetDAO = FleetDAO.getInstanced(context);
	}

	@Override
	public FleetVO getFleet() throws VeloException {
		FleetVO fleetVO;
		List<FleetVO> listFleets = fleetDAO.getAll();
		if (listFleets != null && listFleets.get(0) != null) {
			fleetVO = listFleets.get(0);
			List<StationVO> listStations = stationDAO.getAll();
			fleetVO.setStations(listStations.toArray(new StationVO[listStations.size()]));
		} else {
			fleetVO = new FleetVO();
		}
		return fleetVO;
	}

	@Override
	public void saveFleet(FleetVO fleetVO) throws VeloException {
		fleetDAO.deleteAll();
		fleetDAO.insert(fleetVO);
		synchronized (stationDAO) {
			stationDAO.deleteAll();
			stationDAO.insert(fleetVO.getStations());
		}
	}

	@Override
	public StationVO getStation(int id) throws VeloException {
		return stationDAO.get(Integer.toString(id));
	}

	@Override
	public void saveStation(StationVO stationVO) throws VeloException {
		if (stationDAO.update(stationVO) == 0) {
			stationDAO.insert(stationVO);
		}
	}

	@Override
	public void deleteStation(int id) throws VeloException {
		stationDAO.delete(Integer.toString(id));
	}

	@Override
	public FleetVO getFavouriteFleet() throws VeloException {
		FleetVO fleetVO = new FleetVO();
		List<StationVO> listStations = favouriteStationDAO.getAll();
		if (listStations != null) {
			fleetVO.setStations(listStations.toArray(new StationVO[listStations.size()]));
		}
		return fleetVO;
	}

	@Override
	public void saveFavouriteFleet(FleetVO fleetVO) throws VeloException {
		synchronized (favouriteStationDAO) {
			favouriteStationDAO.deleteAll();
			favouriteStationDAO.insert(fleetVO.getStations());
		}
	}

	@Override
	public StationVO getFavouriteStation(int id) throws VeloException {
		return favouriteStationDAO.get(Integer.toString(id));
	}

	@Override
	public void saveFavouriteStation(StationVO stationVO) throws VeloException {
		if (favouriteStationDAO.update(stationVO) == 0) {
			favouriteStationDAO.insert(stationVO);
		}
	}

	@Override
	public void deleteFavouriteStation(int id) throws VeloException {
		favouriteStationDAO.delete(Integer.toString(id));
	}

}
