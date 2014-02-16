package com.jalvaro.velolibrary.client.db;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jalvaro.velolibrary.client.utils.SqlUtils;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*
     * The Android's default system path of the application database in internal
     * storage. The package of the application is part of the path of the
     * directory.
     */
	public static final String TAG = DatabaseHelper.class.getSimpleName();
	private static int DB_VERSION = 1;
	private static String DB_CREATE = "sql/create_bdd.sql";
	private static String DB_UPDATE = "sql/update_bdd_%1$s.sql";
//    private static String DB_DIR = "/data/data/%1$s/databases/";
    private static String DB_NAME = "database.sqlite";
//    private static String DB_PATH = DB_DIR + DB_NAME;
//    private static String DB_PATH;
//    private static String OLD_DB_PATH = DB_DIR + "old_" + DB_NAME;

    private final Context myContext;

    private boolean createDatabase = false;
    private boolean upgradeDatabase = false;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     * 
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        myContext = context;
        // Get the path of the database that is based on the context.
//        DB_PATH = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    /**
     * Upgrade the database in internal storage if it exists but is not current. 
     * Create a new empty database in internal storage if it does not exist.
     */
    public void initializeDataBase() {
    	Log.d(TAG,"INITIALIZE DATABASE");
    	/*
         * Creates or updates the database in internal storage if it is needed
         * before opening the database. In all cases opening the database copies
         * the database in internal storage to the cache.
         */
        getWritableDatabase();

        if (createDatabase) {
        	Log.d(TAG, "This bdd has been created");
        	
        } else if (upgradeDatabase) {
         	Log.d(TAG, "This bdd has been updated");
        }

    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {
       
    	Log.d(TAG,"Copy DATABASE");
    	 
//        close();
//        InputStream myInput = myContext.getAssets().open(DB_NAME);
//        OutputStream myOutput = new FileOutputStream(DB_PATH);
//        FileHelper.copyFile(myInput, myOutput);
//        getWritableDatabase().close();
    }

    /*
     * This is where the creation of tables and the initial population of the
     * tables should happen, if a database is being created from scratch instead
     * of being copied from the application package assets. Copying a database
     * from the application package assets to internal storage inside this
     * method will result in a corrupted database.
     * <P>
     * NOTE: This method is normally only called when a database has not already
     * been created. When the database has been copied, then this method is
     * called the first time a reference to the database is retrieved after the
     * database is copied since the database last cached by SQLiteOpenHelper is
     * different than the database in internal storage.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
         * Signal that a new database needs to be copied. The copy process must
         * be performed after the database in the cache has been closed causing
         * it to be committed to internal storage. Otherwise the database in
         * internal storage will not have the same creation timestamp as the one
         * in the cache causing the database in internal storage to be marked as
         * corrupted.
         */
        createDatabase = true;
        Log.d(TAG,"onCreate DBB ");
        /*
         * This will create by reading a sql file and executing the commands in
         * it.
         */
             try {
            	 InputStream is = myContext.getResources().getAssets().open(DB_CREATE);

            	 String[] statements = SqlUtils.parseSqlFile(is);

            	 for (String statement : statements) {
            		 db.execSQL(statement);
            	 }
             } catch (Exception ex) {
            	 Log.e("SQL","Error create DBB ",ex);
            	 ex.printStackTrace();
             }
    }

    /**
     * Called only if version number was changed and the database has already
     * been created. Copying a database from the application package assets to
     * the internal data system inside this method will result in a corrupted
     * database in the internal data system.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  
    	upgradeDatabase = true;
    	Log.d(TAG,"onUpgrade DBB ");
    	try {
    		String updateURL = String.format(DB_UPDATE, DB_VERSION);
    		Log.d(TAG," UpdateUrl -->"+updateURL);
    		InputStream is = myContext.getResources().getAssets().open(updateURL);
    		String[] statements = SqlUtils.parseSqlFile(is);

    		for (String statement : statements) {
    			db.execSQL(statement);
    		}
    	} catch (Exception ex) {
    		Log.e(TAG,"Error update DBB ",ex);
    		ex.printStackTrace();
    	}
    }
}
