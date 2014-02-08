package com.jalvaro.velobleu.client.io;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.util.Log;

import com.jalvaro.velobleu.client.application.Constants;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;
import com.jalvaro.velobleu.client.utils.CRUDUtils;

public class MonacoApi extends Api{

	private static final String TAG = MonacoApi.class.getName();

	private static final String URL_1 = "https://cam.cleanenergyplanet.com/public/stations_poi.php?key=DMG346GLR567";

	@Override
	public FleetVO getFleet() throws VeloException {
		FleetVO fleetVO = null;

		try {
			fleetVO = parse(CRUDUtils.get(URL_1));
		} catch (Exception e) {
			// Error
			Log.e(TAG, "getDevices - " + e.getMessage(), e);
			throw (new VeloException(VeloException.VELOBLEU_EXCEPTION_SERVER_ERROR));
		}

		return fleetVO;
	}
	
	private FleetVO parse(String response) {
		int i = 0;
		FleetVO fleetVO = new FleetVO();

		for (String station : response.split("\\n")) {
			i++;
			if (i != 1) {
				String[] stationSplitted = station.split("\\t");
				StationVO stationVO = new StationVO();
				stationVO.setLatitude(Double.valueOf(stationSplitted[0]));
				stationVO.setLongitude(Double.valueOf(stationSplitted[1]));
				stationVO.setId(Integer.valueOf(stationSplitted[2]));
				stationVO.setDescription(stationSplitted[3]);
				stationVO.setName("");
				stationVO.setAvailable(1);
				int freeSlots = Integer.valueOf(stationSplitted[7].substring(stationSplitted[7].lastIndexOf("c=")+2, stationSplitted[7].lastIndexOf("&m=")));
				int totalSlots = Integer.valueOf(stationSplitted[7].substring(stationSplitted[7].lastIndexOf("&m=")+3));
				stationVO.setTotalFreeSlots(freeSlots);
				stationVO.setTotalOccupiedSlots(totalSlots-freeSlots);
				stationVO.setTotalFunctionalSlots(totalSlots);
				stationVO.setTotalSlots(totalSlots);
				
				fleetVO.addStation(stationVO);
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.JSON_TIME_FORMATTER, Locale.getDefault());
		fleetVO.setDate(dateFormat.format(Calendar.getInstance().getTime()));
		
		return fleetVO;
	}

}
