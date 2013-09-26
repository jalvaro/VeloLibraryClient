package com.jalvaro.velobleu.client.models;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.google.gson.annotations.SerializedName;
import com.jalvaro.velobleu.client.R;

public class StationVO {

	@SerializedName("wcom")
	private String mDescription;

	@SerializedName("disp")
	private int mAvailability;

	@SerializedName("lng")
	private double mLongitude;

	@SerializedName("lat")
	private double mLatitude;

	/**
	 * Total Slots >= Total Functional Slots
	 * Total Functional Slots = Total Free Slots + Total Occupied Slots
	 */
	@SerializedName("tc")
	private int mTotalSlots;

	@SerializedName("ac")
	private int mTotalFunctionalSlots;

	@SerializedName("ap")
	private int mTotalFreeSlots;

	@SerializedName("ab")
	private int mTotalOccupiedSlots;

	@SerializedName("id")
	private int mId;

	@SerializedName("name")
	private String mName;

	public StationVO() {
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		String aux = mDescription;
		try {
			 aux = URLDecoder.decode(mDescription, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			aux = "";
		}
		return aux;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}

	public boolean isAvailable() {
		return mAvailability == 1;
	}

	public void setAvailable(int availability) {
		this.mAvailability = availability;
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

	public int getTotalSlots() {
		return mTotalSlots;
	}

	public void setTotalSlots(int totalSlots) {
		this.mTotalSlots = totalSlots;
	}

	public int getTotalFunctionalSlots() {
		return mTotalFunctionalSlots;
	}

	public void setTotalFunctionalSlots(int totalFunctionalSlots) {
		this.mTotalFunctionalSlots = totalFunctionalSlots;
	}

	public int getTotalFreeSlots() {
		return mTotalFreeSlots;
	}

	public void setTotalFreeSlots(int totalFreeSlots) {
		this.mTotalFreeSlots = totalFreeSlots;
	}

	public int getTotalOccupiedSlots() {
		return mTotalOccupiedSlots;
	}

	public void setTotalOccupiedSlots(int totalOccupiedSlots) {
		this.mTotalOccupiedSlots = totalOccupiedSlots;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public String getName() {
		String aux = mName;
		try {
			 aux = URLDecoder.decode(mName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			aux = "";
		}
		return aux;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public int getDisabledSlots() {
		return mTotalSlots - mTotalFunctionalSlots;
	}

	public int getDrawable() {
		int res;
		double groups = (double) (mTotalFunctionalSlots - 2) / 3;

		if (mTotalFunctionalSlots == mTotalOccupiedSlots) {
			res = R.drawable.ballon_5;
		} else if ((mTotalFunctionalSlots - groups) <= mTotalOccupiedSlots) {
			res = R.drawable.ballon_4;
		} else if ((mTotalFunctionalSlots - groups * 2) <= mTotalOccupiedSlots) {
			res = R.drawable.ballon_3;
		} else if ((mTotalFunctionalSlots - groups * 3) <= mTotalOccupiedSlots) {
			res = R.drawable.ballon_2;
		} else if (mTotalOccupiedSlots == 1) {
			res = R.drawable.ballon_1;
		} else if (mTotalOccupiedSlots == 0) {
			res = R.drawable.ballon_0;
		} else{
			res = R.drawable.popup_inline_error_above_holo_dark_mod2;
		}
		return res;
	}
	
	@Override
	public String toString() {
		return getName() + " - " + getDescription();
	}
}