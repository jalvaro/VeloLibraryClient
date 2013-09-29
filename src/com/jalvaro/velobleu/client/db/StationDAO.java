package com.jalvaro.velobleu.client.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.StationVO;

public class StationDAO extends GenericDAO<StationVO> {

	private final static String TAG = StationDAO.class.getName();
	private static StationDAO instance = null;
	private static final String TABLE = "stations";

	private enum Fields {
		GID("gid"), DESCRIPTION("wcom"), AVAILABILITY("disp"), LAT("lat"), LNG("lng"), TOTAL_SLOTS("tc"), TOTAL_FUNCTIONAL_SLOTS("ac"), TOTAL_FREE_SLOTS(
				"ap"), TOTAL_OCCUPIED_SLOTS("ab"), ID("id"), NAME("name"), TIME_STAMP("timestamp");

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
	protected StationDAO(Context context) {
		super(context);
	}

	public static StationDAO getInstanced(Context context) {
		if (instance == null) {
			instance = new StationDAO(context);
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
		Log.i("BDD - " + TAG, "START INSERT - Insert record in table: " + getTableName());
		long gid = 0;

		try {
			/* Enable BDD */
			enableBDD();
			db.beginTransaction();

			/* Set content to BDD */
			ContentValues initialValues = setBddContent(stationVO);

			/* Insert in BDD */
			gid = db.insert(getTableName(), "", initialValues);

			db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Insert error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			db.endTransaction();

			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END INSERT - Record inserted with gid: " + gid + ", in table: " + getTableName());

		return gid;
	}

	public long insert(StationVO[] stationsVO) throws VeloException {
		Log.i("BDD - " + TAG, "START INSERT - Insert record in table: " + getTableName());
		long rows = 0;
		long rowId = 0;

		try {
			/* Enable BDD */
			enableBDD();
			db.beginTransaction();

			for (int i = (stationsVO.length - 1); i >= 0; i--) {
				StationVO stationVO = stationsVO[i];
				/* Set content to BDD */
				ContentValues initialValues = setBddContent(stationVO);

				/* Insert in BDD */
				rowId = db.insert(getTableName(), "", initialValues);
				rows++;
			}

			db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Insert error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			db.endTransaction();

			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END INSERT - Records inserted: " + rows + ", ROW_ID: " + rowId + ", in table: " + getTableName());

		return rows;
	}

	@Override
	public long update(StationVO stationVO) throws VeloException {
		long rows = 0;
		Log.i("BDD - " + TAG, "START UPDATE - Update records in table: " + getTableName());

		try {
			/* Enable BDD */
			enableBDD();
			db.beginTransaction();
			/* Set content to BDD */
			ContentValues initialValues = setBddContent(stationVO);

			/* Update in BDD */
			rows = db.update(getTableName(), initialValues, Fields.ID.getName() + "=?", new String[] { Integer.toString(stationVO.getId()) });
			db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Insert error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			db.endTransaction();

			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END UPDATE - Number of records updated: " + rows + " in table: " + getTableName());

		return rows;
	}

	@Override
	public long deleteAll() throws VeloException {
		long rows = 0;
		Log.i("BDD - " + TAG, "START DELETE_ALL- Delete all records in table: " + getTableName());

		try {
			/* Enable BDD */
			enableBDD();

			rows = db.delete(getTableName(), "1", new String[] {});
		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Load error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END DELETE_ALL - Number of records deleted: " + rows + ", in table: " + getTableName());

		return rows;
	}

	@Override
	public long delete(String id) throws VeloException {
		long rows = 0;
		Log.i("BDD - " + TAG, "START DELETE - Delete record " + id + " in table: " + getTableName());

		try {
			/* Enable BDD */
			enableBDD();
			Log.i("BDD - " + TAG, "Delete records");

			rows = db.delete(getTableName(), Fields.ID.getName() + "=?", new String[] { id });
		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Load error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END DELETE - Number of records deleted: " + rows + ", in table: " + getTableName());

		return rows;
	}

	@Override
	public List<StationVO> getAll() throws VeloException {
		List<StationVO> result = new ArrayList<StationVO>();
		Log.i("BDD - " + TAG, "START GET_ALL - Get all records in table: " + getTableName());

		try {
			/* Enable BDD */
			enableBDD();
			Log.i("BDD - " + TAG, "Load records");
			Cursor c = db.query(true, getTableName(), getStringValues(), "", null, null, null, Fields.TIME_STAMP.getName() + " desc", null);
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya mas registros
				do {
					StationVO stationVO = loadFromCursor(c);

					/* Add to list */
					result.add(stationVO);

				} while (c.moveToNext());
			}
			// Close Cursor
			c.close();
		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Load error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END GET_ALL - Number of records got: " + result.size() + ", in table: " + getTableName());

		return result;
	}

	@Override
	public StationVO get(String id) throws VeloException {
		StationVO stationVO = null;
		long rows = 0;
		Log.i("BDD - " + TAG, "START GET - Get record " + id + " in table: " + getTableName());

		try {
			/* Enable BDD */
			enableBDD();
			Log.i("BDD - " + TAG, "Load records");
			Cursor c = db.query(true, getTableName(), getStringValues(), Fields.ID.getName() + "=?", new String[] { id }, null, null,
					Fields.TIME_STAMP.getName() + " desc", null);

			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				stationVO = loadFromCursor(c);
				rows++;
			}
			c.close();
		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Load error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END GET - Number of records got: " + rows + ", in table: " + getTableName());

		return stationVO;
	}

	@Override
	StationVO loadFromCursor(Cursor c) {
		StationVO stationVO = new StationVO();
		stationVO.setDescription(c.getString(1));
		stationVO.setAvailable(c.getInt(2));
		stationVO.setLatitude(c.getDouble(3));
		stationVO.setLongitude(c.getDouble(4));
		stationVO.setTotalSlots(c.getInt(5));
		stationVO.setTotalFunctionalSlots(c.getInt(6));
		stationVO.setTotalFreeSlots(c.getInt(7));
		stationVO.setTotalOccupiedSlots(c.getInt(8));
		stationVO.setId(c.getInt(9));
		stationVO.setName(c.getString(10));

		return stationVO;
	}

	@Override
	ContentValues setBddContent(StationVO stationVO) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(Fields.DESCRIPTION.getName(), stationVO.getDescription());
		initialValues.put(Fields.AVAILABILITY.getName(), stationVO.isAvailable());
		initialValues.put(Fields.LAT.getName(), stationVO.getLatitude());
		initialValues.put(Fields.LNG.getName(), stationVO.getLongitude());
		initialValues.put(Fields.TOTAL_SLOTS.getName(), stationVO.getTotalSlots());
		initialValues.put(Fields.TOTAL_FUNCTIONAL_SLOTS.getName(), stationVO.getTotalFunctionalSlots());
		initialValues.put(Fields.TOTAL_FREE_SLOTS.getName(), stationVO.getTotalFreeSlots());
		initialValues.put(Fields.TOTAL_OCCUPIED_SLOTS.getName(), stationVO.getTotalOccupiedSlots());
		initialValues.put(Fields.ID.getName(), stationVO.getId());
		initialValues.put(Fields.NAME.getName(), stationVO.getName());
		initialValues.put(Fields.TIME_STAMP.getName(), Calendar.getInstance().getTimeInMillis());

		return initialValues;
	}

}
