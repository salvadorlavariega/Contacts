package com.mobilemakers.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Juan on 05/02/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private final static String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private final static String DATABASE_NAME = "contacts.db";
    private final static int DATABES_VERSION = 1;

    private Dao<SignalContactData, Integer> contact = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABES_VERSION);

    }

    public Dao<SignalContactData, Integer> getContactDao() throws SQLException {
        if(contact == null) {
            contact = getDao(SignalContactData.class);
        }
        return contact;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(LOG_TAG, "Creating database");
            TableUtils.createTable(connectionSource, SignalContactData.class);
        } catch (SQLException e) {
            Log.i(LOG_TAG, "Error creating database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
