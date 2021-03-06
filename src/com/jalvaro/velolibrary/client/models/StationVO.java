package com.jalvaro.velolibrary.client.models;

import java.util.Comparator;

import com.google.gson.annotations.SerializedName;
import com.jalvaro.velolibrary.client.R;
import com.jalvaro.velolibrary.client.utils.AndroidUtils;

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

	private int mPosition;

	private boolean mFavourite;

	public StationVO() {
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		String aux = AndroidUtils.decode(mDescription);
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
		String aux = AndroidUtils.decode(mName);
		return aux;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public void setPosition(int position) {
		this.mPosition = position;
	}

	public int getPosition() {
		return mPosition;
	}

	public void setFavourite(boolean favourite) {
		this.mFavourite = favourite;
	}

	public boolean isFavourite() {
		return mFavourite;
	}

	public int getDisabledSlots() {
		return mTotalSlots - mTotalFunctionalSlots;
	}

	public int getDrawable() {
		int res;
		double groups = (double) (mTotalFunctionalSlots - 2) / 3;
		if (mFavourite) {
			if (mTotalFunctionalSlots == mTotalOccupiedSlots) {
				res = R.drawable.ballon_5_fav;
			} else if ((mTotalFunctionalSlots - groups) <= mTotalOccupiedSlots) {
				res = R.drawable.ballon_4_fav;
			} else if ((mTotalFunctionalSlots - groups * 2) <= mTotalOccupiedSlots) {
				res = R.drawable.ballon_3_fav;
			} else if ((mTotalFunctionalSlots - groups * 3) <= mTotalOccupiedSlots) {
				res = R.drawable.ballon_2_fav;
			} else if (mTotalOccupiedSlots == 1) {
				res = R.drawable.ballon_1_fav;
			} else if (mTotalOccupiedSlots == 0) {
				res = R.drawable.ballon_0_fav;
			} else {
				res = R.drawable.ballon_0;
			}
		} else {
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
			} else {
				res = R.drawable.ballon_0;
			}
		}
		return res;
	}

	@Override
	public String toString() {
		String text = getDescription();
		if (text.equals("")) {
			text = getName();
		}
		return text;
	}

	public static class StationComparator implements Comparator<StationVO> {

		@Override
		public int compare(StationVO arg0, StationVO arg1) {
			return arg0.getDescription().compareTo(arg1.getDescription());
		}

	}
}
