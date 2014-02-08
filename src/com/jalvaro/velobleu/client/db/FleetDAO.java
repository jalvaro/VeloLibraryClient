package com.jalvaro.velobleu.client.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;

public class FleetDAO extends GenericDAO<FleetVO> {

	private final static String TAG = FleetDAO.class.getName();
	private static FleetDAO instance = null;
	protected static final String TABLE = "fleets";

	private enum Fields {
		GID("gid"), ORGANIZATION("org"), COUNTRY("cou"), LAT("lat"), LNG("lng"), ZOOM("zoom"), DESCRIPTION("desc"), WEB(
				"web"), REGISTRATION_LINK("reg"), DATE("gmt"), LOCATION("loc"), TIME_STAMP("timestamp");

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
	protected FleetDAO(Context context) {
		super(context);
	}

	public static FleetDAO getInstanced(Context context) {
		if (instance == null) {
			instance = new FleetDAO(context);
		}
		return instance;
	}

	/* Methods to CRUD */
	@Override
	public synchronized long insert(FleetVO fleetVO) throws VeloException {
		long gid = 0;
		Log.i("BDD - " + TAG, "START INSERT - Insert record in table: " + TABLE);

		try {
			/* Enable BDD */
			enableBDD();
			db.beginTransaction();

			/* Set content to BDD */
			ContentValues initialValues = setBddContent(fleetVO);

			/* Insert in BDD */
			gid = db.insert(TABLE, "", initialValues);

			db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Insert error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END INSERT - Record inserted with gid: " + gid + ", in table: " + TABLE);

		return gid;
	}

	@Override
	public synchronized long update(FleetVO fleetVO) throws VeloException {
		long rows = 0;
		Log.i("BDD - " + TAG, "START UPDATE - Update records in table: " + TABLE);

		try {
			/* Enable BDD */
			enableBDD();
			db.beginTransaction();
			/* Set content to BDD */
			ContentValues initialValues = setBddContent(fleetVO);

			/* Update in BDD */
			rows = db.update(TABLE, initialValues, Fields.ORGANIZATION.getName() + "=?", new String[] { fleetVO.getOrganization() });
			db.setTransactionSuccessful();

		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Insert error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END UPDATE - Number of records updated: " + rows + " in table: " + TABLE);

		return rows;
	}

	@Override
	public synchronized long deleteAll() throws VeloException {
		long rows = 0;
		Log.i("BDD - " + TAG, "START DELETE_ALL- Delete all records in table: " + TABLE);

		try {
			/* Enable BDD */
			enableBDD();

			rows = db.delete(TABLE, "1", new String[] {});
		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Load error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END DELETE_ALL - Number of records deleted: " + rows + ", in table: " + TABLE);

		return rows;
	}

	@Override
	public synchronized long delete(String id) throws VeloException {
		long rows = 0;
		Log.i("BDD - " + TAG, "START DELETE - Delete record " + id + " in table: " + TABLE);

		try {
			/* Enable BDD */
			enableBDD();
			Log.i("BDD - " + TAG, "Delete records");

			rows = db.delete(TABLE, Fields.GID.getName() + "=?", new String[] { id });
		} catch (Exception e) {
			Log.e("BDD - " + TAG, "Load error ", e);
			throw new VeloException(VeloException.VELOBLEU_EXCEPTION_BDD_ERROR);
		} finally {
			/* Close BDD */
			closeBDD();
		}
		Log.d("BDD - " + TAG, "END DELETE - Number of records deleted: " + rows + ", in table: " + TABLE);

		return rows;
	}

	@Override
	public synchronized List<FleetVO> getAll() throws VeloException {
		List<FleetVO> result = new ArrayList<FleetVO>();
		Log.i("BDD - " + TAG, "START GET_ALL - Get all records in table: " + TABLE);

		try {
			/* Enable BDD */
			enableBDD();
			Log.i("BDD - " + TAG, "Load records");
			Cursor c = db.query(true, TABLE, Fields.getStringValues(), "", null, null, null, Fields.TIME_STAMP.getName() + " desc", null);
			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				// Recorremos el cursor hasta que no haya mas registros
				do {
					FleetVO fleetVO = loadFromCursor(c);

					/* Add to list */
					result.add(fleetVO);

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
		Log.d("BDD - " + TAG, "END GET_ALL - Number of records got: " + result.size() + ", in table: " + TABLE);

		return result;
	}

	@Override
	public synchronized FleetVO get(String id) throws VeloException {
		FleetVO fleetVO = null;
		long rows = 0;
		Log.i("BDD - " + TAG, "START GET - Get record " + id + " in table: " + TABLE);

		try {
			/* Enable BDD */
			enableBDD();
			Log.i("BDD - " + TAG, "Load records");
			Cursor c = db.query(true, TABLE, Fields.getStringValues(), Fields.ORGANIZATION.getName() + "=?", new String[] { id },
					null, null, Fields.TIME_STAMP.getName() + " desc", null);

			// Nos aseguramos de que existe al menos un registro
			if (c.moveToFirst()) {
				fleetVO = loadFromCursor(c);
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
		Log.d("BDD - " + TAG, "END GET - Number of records got: " + rows + ", in table: " + TABLE);

		return fleetVO;
	}

	@Override
	FleetVO loadFromCursor(Cursor c) {
		FleetVO fleetVO = new FleetVO();
		fleetVO.setOrganization(c.getString(1));
		fleetVO.setCountry(c.getString(2));
		fleetVO.setLatitude(c.getDouble(3));
		fleetVO.setLongitude(c.getDouble(4));
		fleetVO.setZoom(c.getInt(5));
		fleetVO.setDescription(c.getString(6));
		fleetVO.setWeb(c.getString(7));
		fleetVO.setRegistrationLink(c.getString(8));
		fleetVO.setDate(c.getString(9));
		fleetVO.setLocation(c.getString(10));

		return fleetVO;
	}

	@Override
	ContentValues setBddContent(FleetVO fleetVO) {

		ContentValues initialValues = new ContentValues();

		initialValues.put(Fields.ORGANIZATION.getName(), fleetVO.getOrganization());
		initialValues.put(Fields.COUNTRY.getName(), fleetVO.getCountry());
		initialValues.put(Fields.LAT.getName(), fleetVO.getLatitude());
		initialValues.put(Fields.LNG.getName(), fleetVO.getLongitude());
		initialValues.put(Fields.ZOOM.getName(), fleetVO.getZoom());
		initialValues.put(Fields.DESCRIPTION.getName(), fleetVO.getDescription());
		initialValues.put(Fields.WEB.getName(), fleetVO.getWeb());
		initialValues.put(Fields.REGISTRATION_LINK.getName(), fleetVO.getRegistrationLink());
		initialValues.put(Fields.DATE.getName(), fleetVO.getDate());
		initialValues.put(Fields.LOCATION.getName(), fleetVO.getLocation());
		initialValues.put(Fields.TIME_STAMP.getName(), Calendar.getInstance().getTimeInMillis());

		return initialValues;
	}

}
