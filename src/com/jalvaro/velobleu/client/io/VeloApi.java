package com.jalvaro.velobleu.client.io;

import android.util.Log;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;

public class VeloApi {

	private static final String TAG = VeloApi.class.getName();

	private static final String URL_1 = "http://www.velo-vision.com/nice/oybike/stands.nsf/getsite?site=nice&format=json&key=veolia";
	private static final String URL_2 = "http://www.velobleu.org/cartoV2/libProxyCarto.asp";

	public static FleetVO getFleet() throws VeloException {
		FleetVO fleetVO = null;

		try {
			fleetVO = CRUDUtils.getGson(URL_1);
			// fleetVO.copyValues(fleetVOResponse);
		} catch (Exception e) {
			// Error
			Log.e(TAG, "getDevices - " + e.getMessage(), e);
			throw (new VeloException());
		}

		return fleetVO;
	}

}
