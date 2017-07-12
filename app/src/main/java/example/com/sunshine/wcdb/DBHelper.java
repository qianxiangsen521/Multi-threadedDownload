package example.com.sunshine.wcdb;

import android.content.Context;

import com.tencent.wcdb.DatabaseErrorHandler;
import com.tencent.wcdb.database.SQLiteCipherSpec;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;

/**
 * Created by qianxiangsen on 2017/7/10.
 */

public class DBHelper extends SQLiteOpenHelper {


    static final String DATABASE_NAME = "test-repair.db";
    static final int DATABASE_VERSION = 1;

    static final byte[] PASSPHRASE = "testkey".getBytes();

    // The test database is taken from SQLCipher test-suit.
    //
    // To be compatible with databases created by the official SQLCipher
    // library, a SQLiteCipherSpec must be specified with page size of
    // 1024 bytes.
    static final SQLiteCipherSpec CIPHER_SPEC = new SQLiteCipherSpec()
            .setPageSize(1024);


    // We don't want corrupted databases get deleted or renamed on this sample,
    // so use an empty DatabaseErrorHandler.
    static final DatabaseErrorHandler ERROR_HANDLER =   new DatabaseErrorHandler() {
        @Override
        public void onCorruption(com.tencent.wcdb.database.SQLiteDatabase dbObj) {
            // Do nothing
        }
    };

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, PASSPHRASE, CIPHER_SPEC, null,
                DATABASE_VERSION, ERROR_HANDLER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +

                /*
                 * WeatherContract.WeatherEntry did not explicitly declare a column called "_ID". However,
                 * WeatherContract.WeatherEntry implements the interface, "BaseColumns", which does have a field
                 * named "_ID". We use that here to designate our table's primary key.
                 */
                        WeatherContract.WeatherEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        WeatherContract.WeatherEntry.COLUMN_DATE       + " INTEGER, "                 +

                        WeatherContract.WeatherEntry.COLUMN_WEATHER_ID + " INTEGER, "                 +

                        WeatherContract.WeatherEntry.COLUMN_MIN_TEMP   + " REAL, "                    +
                        WeatherContract.WeatherEntry.COLUMN_MAX_TEMP   + " REAL, "                    +

                        WeatherContract.WeatherEntry.COLUMN_HUMIDITY   + " REAL, "                    +
                        WeatherContract.WeatherEntry.COLUMN_PRESSURE   + " REAL, "                    +

                        WeatherContract.WeatherEntry.COLUMN_WIND_SPEED + " REAL, "                    +
                        WeatherContract.WeatherEntry.COLUMN_DEGREES    + " REAL" + ");";

        /*
         * After we've spelled out our SQLite table creation statement above, we actually execute
         * that SQL with the execSQL method of our SQLite database object.
         */
        db.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
