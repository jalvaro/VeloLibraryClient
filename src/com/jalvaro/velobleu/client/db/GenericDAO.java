package com.jalvaro.velobleu.client.db;

import java.util.List;

import com.jalvaro.velobleu.client.exceptions.VeloException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class GenericDAO<T> implements GenericInterfaceDAO<T> {

	protected SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	private Context context;

	public GenericDAO(Context context) {
		super();
		this.context = context;
	}

	@Override
	public long insert(T o) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long update(T o) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long insertAndUpdate(T o) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long deleteAll() throws VeloException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long delete(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean enableBDD() {
		if (dbHelper == null)
			dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
		dbHelper.initializeDataBase();
		return false;
	}

	@Override
	public boolean closeBDD() {
		if (db.inTransaction())
			db.endTransaction();
		db.close();

		return true;
	}

	abstract Object loadFromCursor(Cursor c);
	abstract ContentValues setBddContent(T o);

}