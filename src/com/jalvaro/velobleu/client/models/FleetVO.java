package com.jalvaro.velobleu.client.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

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

	public FleetVO() {
		this("", "", 0.0, 0.0, 12, "", "", "", "", new StationVO[0], "");
	}

	public FleetVO(String organization, String country, double longitude, double latitude, int zoom, String description, String web, String registrationLink, String date, StationVO[] stations,
			String location) {
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

	public StationVO[] getStations() {
		return mStations;
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
		for (StationVO stationVO : fleetVO.getStations()) {
			StationVO stationAux = getStationById(stationVO.getId());
			if (stationAux != null && stationAux.isAvailable()) {
				stations.add(stationAux);
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
/*
	public void copyValues(FleetVO fleetVO) {
		this.mOrganization = fleetVO.getOrganization();
		this.mCountry = fleetVO.getCountry();
		this.mLongitude = fleetVO.getLongitude();
		this.mLatitude = fleetVO.getLatitude();
		this.mZoom = fleetVO.getZoom();
		this.mDescription = fleetVO.getDescription();
		this.mWeb = fleetVO.getWeb();
		this.mRegistrationLink = fleetVO.getRegistrationLink();
		this.mDate = fleetVO.getDate();
		this.mStations = fleetVO.getStations();
		this.mLocation = fleetVO.getLocation();
	}*/
}
