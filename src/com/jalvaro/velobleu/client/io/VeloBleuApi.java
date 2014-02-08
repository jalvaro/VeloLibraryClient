package com.jalvaro.velobleu.client.io;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.util.Log;

import com.jalvaro.velobleu.client.application.Constants;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.utils.CRUDUtils;

public class VeloBleuApi extends Api {

	private static final String TAG = VeloBleuApi.class.getName();

	private static final String URL_1 = "http://www.velo-vision.com/nice/oybike/stands.nsf/getsite?site=nice&format=json&key=veolia";
	private static final String URL_2 = "http://www.velobleu.org/cartoV2/libProxyCarto.asp";

	@Override
	public FleetVO getFleet() throws VeloException {
		FleetVO fleetVO = null;

		try {
			fleetVO = CRUDUtils.getGson(URL_1);
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.JSON_TIME_FORMATTER, Locale.getDefault());
			fleetVO.setDate(dateFormat.format(Calendar.getInstance().getTime()));
		} catch (Exception e) {
			// Error
			Log.e(TAG, "getDevices - " + e.getMessage(), e);
			throw (new VeloException(VeloException.VELOBLEU_EXCEPTION_SERVER_ERROR));
		}

		return fleetVO;
	}

}
