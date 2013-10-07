package com.jalvaro.velobleu.client.db;

import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.StationVO;

public class FavouriteStationDAO extends StationDAO {

	private static FavouriteStationDAO instance = null;
	private static final String TABLE = "favourite_stations";

	private enum Fields {
		GID("gid"), ID("id"), NAME("name"), POSITION("position"), TIME_STAMP("timestamp");

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
	public long insert(StationVO stationVO) throws VeloException {
		return super.insert(stationVO);
	}

	public long insert(StationVO[] stationsVO) throws VeloException {
		return super.insert(stationsVO);
	}

	@Override
	public long update(StationVO stationVO) throws VeloException {
		return super.update(stationVO);
	}

	@Override
	public long deleteAll() throws VeloException {
		return super.deleteAll();
	}

	@Override
	public long delete(String id) throws VeloException {
		return super.delete(id);
	}

	@Override
	public List<StationVO> getAll() throws VeloException {
		return super.getAll();
	}

	@Override
	public StationVO get(String id) throws VeloException {
		return super.get(id);
	}

	@Override
	StationVO loadFromCursor(Cursor c) {
		StationVO stationVO = new StationVO();
		stationVO.setId(c.getInt(1));
		stationVO.setName(c.getString(2));
		stationVO.setPosition(c.getInt(3));
		stationVO.setFavourite(true);

		return stationVO;
	}

	@Override
	ContentValues setBddContent(StationVO stationVO) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(Fields.ID.getName(), stationVO.getId());
		initialValues.put(Fields.NAME.getName(), stationVO.getName());
		initialValues.put(Fields.POSITION.getName(), stationVO.getPosition());
		initialValues.put(Fields.TIME_STAMP.getName(), Calendar.getInstance().getTimeInMillis());

		return initialValues;
	}

}
