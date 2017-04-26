package example.com.sunshine.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qianxiangsen on 2017/4/20.
 */

public class MyOpenHelper extends SQLiteOpenHelper {

    //数据集库名字
    private final static String DATABASE_NAME = "downloads.db";
    //版本号
    private final static int DATABASE_VERSION = 5;
    //表明
    public static final String TABLE_NAME = "downloads";

    public MyOpenHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        createTable(db);
    }

    private void createTable(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(TABLE_NAME).append("(")
                .append(TASKS.COLUMN_ID).append(" INTEGER PRIMARY KEY autoincrement,")
                .append(TASKS.COLUMN_NAME).append(" TEXT,")
                .append(TASKS.COLUMN_SIZE).append(" TEXT,")
                .append(TASKS.COLUMN_STATE).append(" TEXT,")
                .append(TASKS.COLUMN_URL).append(" TEXT,")
                .append(TASKS.COLUMN_PERCENT).append(" TEXT,")
                .append(TASKS.COLUMN_CHUNKS).append(" TEXT,")
                .append(TASKS.COLUMN_NOTIFY).append(" TEXT,")
                .append(TASKS.COLUMN_RESUMABLE).append(" TEXT,")
                .append(TASKS.COLUMN_PRIORITY).append(" TEXT,")
                .append(TASKS.COLUMN_SAVE_ADDRESS).append(" TEXT,")
                .append(TASKS.COLUMN_EXTENSION).append(" TEXT,")
                .append(TASKS.COLUMN_SPEED).append(" TEXT,")
                .append(TASKS.COLUMN_IAMGE).append(" TEXT")
                .append(");");

        db.execSQL(sb.toString());
    }
}
