package com.jalvaro.velobleu.client.application;

import android.app.Application;

import com.jalvaro.velobleu.client.models.FleetVO;

public class VeloApp extends Application {
	private static final String TAG = VeloApp.class.getSimpleName();
	private FleetVO fleetVO;
	private FleetVO favouriteFleetVO;

	public VeloApp() {
		super();
		fleetVO = new FleetVO();
		favouriteFleetVO = new FleetVO();
	}

	public FleetVO getFleetVO() {
		return fleetVO;
	}

	public void setFleetVO(FleetVO fleetVO) {
		this.fleetVO = fleetVO;
	}

	public FleetVO getFavouriteFleetVO() {
		return favouriteFleetVO;
	}

	public void setFavouriteFleetVO(FleetVO favouriteFleetVO) {
		this.favouriteFleetVO = favouriteFleetVO;
	}
}
