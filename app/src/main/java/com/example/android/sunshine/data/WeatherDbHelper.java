package com.example.android.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "weather.db";

    WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_LOCATION_TABLE = new StringBuilder().append("CREATE TABLE ")
                .append(WeatherContract.LocationEntry.TABLE_NAME).append(" (")
                .append(WeatherContract.LocationEntry._ID).append(" INTEGER PRIMARY KEY,")
                .append(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING).append(" TEXT UNIQUE NOT NULL, ")
                .append(WeatherContract.LocationEntry.COLUMN_CITY_NAME).append(" TEXT NOT NULL, ")
                .append(WeatherContract.LocationEntry.COLUMN_COORD_LAT).append(" REAL NOT NULL, ")
                .append(WeatherContract.LocationEntry.COLUMN_COORD_LONG).append(" REAL NOT NULL, ")

                .append("UNIQUE (").append(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING)
                .append(") ON CONFLICT IGNORE").append(" );").toString();

        final String SQL_CREATE_WEATHER_TABLE = new StringBuilder().append("CREATE TABLE ")
                .append(WeatherContract.WeatherEntry.TABLE_NAME).append(" (")
                .append(WeatherContract.WeatherEntry._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(WeatherContract.WeatherEntry.COLUMN_LOC_KEY).append(" INTEGER NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_DATE).append(" INTEGER NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC).append(" TEXT NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID).append(" INTEGER NOT NULL,")
                .append(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP).append(" REAL NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP).append(" REAL NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_HUMIDITY).append(" REAL NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_PRESSURE).append(" REAL NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED).append(" REAL NOT NULL, ")
                .append(WeatherContract.WeatherEntry.COLUMN_DEGREES).append(" REAL NOT NULL, ")

                .append(" FOREIGN KEY (").append(WeatherContract.WeatherEntry.COLUMN_LOC_KEY)
                .append(") REFERENCES ").append(WeatherContract.LocationEntry.TABLE_NAME)
                .append(" (").append(WeatherContract.LocationEntry._ID).append("), ")

                .append(" UNIQUE (").append(WeatherContract.WeatherEntry.COLUMN_DATE)
                .append(", ").append(WeatherContract.WeatherEntry.COLUMN_LOC_KEY
                ).append(") ON CONFLICT REPLACE);").toString();

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
        db.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.LocationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME);
        onCreate(db);
    }
}
