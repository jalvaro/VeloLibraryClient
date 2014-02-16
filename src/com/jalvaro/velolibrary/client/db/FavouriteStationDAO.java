package com.jalvaro.velolibrary.client.db;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jalvaro.velolibrary.client.models.StationVO;

public class FavouriteStationDAO extends StationDAO {

	private static FavouriteStationDAO instance = null;
	private static final String TABLE = "favourite_stations";

	private enum Fields {
		GID("gid"), DESCRIPTION("wcom"), ID("id"), NAME("name"), POSITION("position"), TIME_STAMP("timestamp");

		String name;

		Fields(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static String[] getStringValues() {
			Fields[] values = Fields.values();
			String[] stringVals = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				stringVals[i] = values[i].getName();
			}
			return stringVals;
		}
	}

	/* Singleton */
	protected FavouriteStationDAO(Context context) {
		super(context);
	}

	public static FavouriteStationDAO getInstanced(Context context) {
		if (instance == null) {
			instance = new FavouriteStationDAO(context);
		}
		return instance;
	}

	protected String getTableName() {
		return TABLE;
	}
	
	protected String[] getStringValues() {
		return Fields.getStringValues();
	}
	

	/* Methods to CRUD */
	@Override
	StationVO loadFromCursor(Cursor c) {
		StationVO stationVO = new StationVO();
		stationVO.setDescription(c.getString(1));
		stationVO.setId(c.getInt(2));
		stationVO.setName(c.getString(3));
		stationVO.setPosition(c.getInt(4));
		stationVO.setFavourite(true);

		return stationVO;
	}

	@Override
	ContentValues setBddContent(StationVO stationVO) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(Fields.DESCRIPTION.getName(), stationVO.getDescription());
		initialValues.put(Fields.ID.getName(), stationVO.getId());
		initialValues.put(Fields.NAME.getName(), stationVO.getName());
		initialValues.put(Fields.POSITION.getName(), stationVO.getPosition());
		initialValues.put(Fields.TIME_STAMP.getName(), Calendar.getInstance().getTimeInMillis());

		return initialValues;
	}

}
