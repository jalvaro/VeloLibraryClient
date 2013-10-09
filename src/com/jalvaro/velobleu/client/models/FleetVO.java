package com.jalvaro.velobleu.client.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.jalvaro.velobleu.client.models.StationVO.StationComparator;

public class FleetVO {

	@SerializedName("org")
	private String mOrganization;

	@SerializedName("cou")
	private String mCountry;

	@SerializedName("lng")
	private double mLongitude;

	@SerializedName("lat")
	private double mLatitude;

	@SerializedName("zoom")
	private int mZoom;

	@SerializedName("desc")
	private String mDescription;

	@SerializedName("web")
	private String mWeb;

	@SerializedName("reg")
	private String mRegistrationLink;

	@SerializedName("gmt")
	private String mDate;

	@SerializedName("stand")
	private StationVO[] mStations;

	@SerializedName("loc")
	private String mLocation;

	private boolean isOrdered;

	public FleetVO() {
		this("", "", 0.0, 0.0, 12, "", "", "", "", new StationVO[0], "");
	}

	public FleetVO(String organization, String country, double longitude, double latitude, int zoom, String description, String web,
			String registrationLink, String date, StationVO[] stations, String location) {
		super();
		this.mOrganization = organization;
		this.mCountry = country;
		this.mLongitude = longitude;
		this.mLatitude = latitude;
		this.mZoom = zoom;
		this.mDescription = description;
		this.mWeb = web;
		this.mRegistrationLink = registrationLink;
		this.mDate = date;
		this.mStations = stations;
		this.mLocation = location;
		this.isOrdered = false;
	}

	public String getOrganization() {
		return mOrganization;
	}

	public void setOrganization(String organization) {
		this.mOrganization = organization;
	}

	public String getCountry() {
		return mCountry;
	}

	public void setCountry(String country) {
		this.mCountry = country;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		this.mLongitude = longitude;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		this.mLatitude = latitude;
	}

	public int getZoom() {
		return mZoom;
	}

	public void setZoom(int zoom) {
		this.mZoom = zoom;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

	public String getWeb() {
		return mWeb;
	}

	public void setWeb(String web) {
		this.mWeb = web;
	}

	public String getRegistrationLink() {
		return mRegistrationLink;
	}

	public void setRegistrationLink(String registrationLink) {
		this.mRegistrationLink = registrationLink;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String date) {
		this.mDate = date;
	}

	public synchronized StationVO[] getStations() {
		orderStations();
		return mStations;
	}

	private void orderStations() {
		if (!isOrdered) {
			Arrays.sort(mStations, new StationComparator());
			isOrdered = true;
		}
	}

	public StationVO getStation(int i) {
		return mStations[i];
	}

	public StationVO getStationById(int id) {
		StationVO stationVO = null;
		for (StationVO stVO : mStations) {
			if (stVO.getId() == id) {
				stationVO = stVO;
				break;
			}
		}
		return stationVO;
	}

	public void setStations(StationVO[] stations) {
		this.mStations = stations;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		this.mLocation = location;
	}

	public StationVO[] getSubArrayOfAvailableStationsById(FleetVO fleetVO) {
		List<StationVO> stations = new ArrayList<StationVO>();
		if (fleetVO != null && fleetVO.getStations() != null) {
			for (StationVO stationVO : fleetVO.getStations()) {
				StationVO stationAux = getStationById(stationVO.getId());
				if (stationAux != null && stationAux.isAvailable()) {
					// stationAux.setDescription(stationVO.getDescription());
					stationAux.setFavourite(stationVO.isFavourite());
					stations.add(stationAux);
				}
			}
		}
		return stations.toArray(new StationVO[stations.size()]);
	}

	public StationVO[] getSubArrayOfAvailableStations() {
		List<StationVO> stations = new ArrayList<StationVO>();
		for (StationVO stationVO : mStations) {
			if (stationVO.isAvailable()) {
				stations.add(stationVO);
			}
		}
		return stations.toArray(new StationVO[stations.size()]);
	}

	public void setFavourites(StationVO[] stations) {
		for (StationVO stationVO : stations) {
			getStationById(stationVO.getId()).setFavourite(true);
		}
	}

	public void addStation(StationVO stationVO) {
		StationVO[] stations = new StationVO[mStations.length + 1];
		for (int i = 0; i < mStations.length; i++) {
			stations[i] = mStations[i];
		}
		stations[mStations.length] = stationVO;
		mStations = stations;
		orderStations();
	}

	public void deleteStation(StationVO stationVO) {
		if (getStationById(stationVO.getId()) != null) {
			StationVO[] stations = new StationVO[mStations.length - 1];
			for (int i = 0, k = 0; i < mStations.length; i++) {
				if (mStations[i].getId() != stationVO.getId()) {
					stations[k] = mStations[i];
					k++;
				}
			}
			mStations = stations;
			orderStations();
		}
	}
}
