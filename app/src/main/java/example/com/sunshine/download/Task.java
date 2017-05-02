package example.com.sunshine.download;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Majid Golshadi on 4/10/2014.
 *
 "CREATE TABLE "+ TABLES.TASKS + " ("
 + TASKS.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
 + TASKS.COLUMN_NAME + " CHAR( 128 ) NOT NULL, "
 + TASKS.COLUMN_SIZE + " INTEGER, "
 + TASKS.COLUMN_STATE + " INT( 3 ), "
 + TASKS.COLUMN_URL + " CHAR( 256 ), "
 + TASKS.COLUMN_PERCENT + " INT( 3 ), "
 + TASKS.COLUMN_CHUNKS + " BOOLEAN, "
 + TASKS.COLUMN_NOTIFY + " BOOLEAN, "
 + TASKS.COLUMN_SAVE_ADDRESS + " CHAR( 256 ),"
 + TASKS.COLUMN_EXTENSION + " CHAR( 32 )"
 + " ); "
 */
public class Task {


    public static final String STATUS_IDLE = "status_idle";
    /**
     * Download status value, when download is started.
     */
    public static final String STATUS_START = "status_start";
    /**
     * Download status value, when download is paused.
     */
    public static final String STATUS_PAUSE = "status_pause";
    /**
     * Download status value, when download is completed successfully.
     */
    public static final String STATUS_COMPLETE = "status_complete";
    /**
     * Download status value, when download has error.
     */
    public static final String STATUS_ERROR = "status_error";
    /**
     * Download status value, when download is paused.
     */
    public static final String STATUS_DELETE = "status_delete";

    public static final String STATUS_INIT = "status_init";

    private DownloadUiListener downloadUiListener;

    private long id;
    private String name;
    private long size;
    private String state ;
    private String url;
    private int percent;
    private int chunks;
    private boolean notify;
    private boolean resumable;
    private String save_address;
    private String muniquely_id = " ";//主键，唯一标示，来自服务器，GID
    private boolean priority;
    private long Speed;
    private String iamgeUrl;

    public Task(){
    	this.id			= 0;
        this.name       = null;
        this.size       = 0;
        this.state      = STATUS_INIT;
        this.url        = null;
        this.percent    = 0;
        this.chunks     = 0;
        this.notify     = false;
        this.resumable  = true;
        this.save_address = null;
        this.muniquely_id  = null;
        this.priority = false;  // low priority
        this.Speed = 0;
        this.iamgeUrl = null;
    }

    public Task(long size, String name, String url,
                String state, int chunks, String sdCardFolderAddress,
                boolean priority,long Speed,String iamgeUrl){
    	this.id			= 0;
        this.name       = name;
        this.size       = size;
        this.state      = state;
        this.url        = url;
        this.percent    = 0;
        this.chunks     = chunks;
        this.notify     = false;
        this.resumable  = true;
        this.save_address = sdCardFolderAddress;
        this.muniquely_id  = "";
        this.priority = priority;
        this.Speed = Speed;
        this.iamgeUrl = iamgeUrl;
    }

    public synchronized ContentValues convertToContentValues(){
        ContentValues contentValues = new ContentValues();

        if (id != 0) {
            contentValues.put(TASKS.COLUMN_ID,  id);

            contentValues.put(TASKS.COLUMN_NAME,    name);
            contentValues.put(TASKS.COLUMN_SIZE,    size);
            contentValues.put(TASKS.COLUMN_STATE,   state);
            contentValues.put(TASKS.COLUMN_URL,     url);
            contentValues.put(TASKS.COLUMN_PERCENT, percent);
            contentValues.put(TASKS.COLUMN_CHUNKS,  chunks);
            contentValues.put(TASKS.COLUMN_NOTIFY,  notify);
            contentValues.put(TASKS.COLUMN_RESUMABLE,    resumable);
            contentValues.put(TASKS.COLUMN_SAVE_ADDRESS, save_address);
            contentValues.put(TASKS.COLUMN_EXTENSION,    muniquely_id);
            contentValues.put(TASKS.COLUMN_PRIORITY,    priority);
            contentValues.put(TASKS.COLUMN_SPEED,Speed);
            contentValues.put(TASKS.COLUMN_IAMGE,iamgeUrl);

        }


        return contentValues;
    }

    public synchronized void cursorToTask(Cursor cr){
        id = cr.getInt(
                cr.getColumnIndex(TASKS.COLUMN_ID));
        name = cr.getString(
                cr.getColumnIndex(TASKS.COLUMN_NAME));
        size = cr.getLong(
                cr.getColumnIndex(TASKS.COLUMN_SIZE));
        state = cr.getString(
                cr.getColumnIndex(TASKS.COLUMN_STATE));
        url = cr.getString(
                cr.getColumnIndex(TASKS.COLUMN_URL));
        percent = cr.getInt(
                cr.getColumnIndex(TASKS.COLUMN_PERCENT));
        chunks = cr.getInt(
                cr.getColumnIndex(TASKS.COLUMN_CHUNKS));
        notify = cr.getInt(
                cr.getColumnIndex(TASKS.COLUMN_NOTIFY))>0;
        resumable = cr.getInt(
                cr.getColumnIndex(TASKS.COLUMN_RESUMABLE))>0;
        save_address = cr.getString(
                cr.getColumnIndex(TASKS.COLUMN_SAVE_ADDRESS));
        muniquely_id = cr.getString(
                cr.getColumnIndex(TASKS.COLUMN_EXTENSION));
        priority = cr.getInt(
                cr.getColumnIndex(TASKS.COLUMN_PRIORITY))>0;
        Speed = cr.getInt(
                cr.getColumnIndex(TASKS.COLUMN_SPEED));
        iamgeUrl =  cr.getString(
                cr.getColumnIndex(TASKS.COLUMN_IAMGE));
    }

    public synchronized String getIamgeUrl() {
        return iamgeUrl;
    }

    public synchronized void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public DownloadUiListener getDownloadUiListener() {
        return downloadUiListener;
    }

    public void setDownloadUiListener(DownloadUiListener downloadUiListener) {
        this.downloadUiListener = downloadUiListener;
    }

    public synchronized long getSpeed() {
        return Speed;
    }

    public synchronized void setSpeed(long speed) {
        Speed = speed;
    }

    public synchronized  long getId() {
        return id;
    }

    public synchronized  void setId(long id) {
        this.id = id;
    }

    public synchronized  String getName() {
        return name;
    }

    public synchronized  void setName(String name) {
        this.name = name;
    }

    public synchronized  long getSize() {
        return size;
    }

    public synchronized  void setSize(long size) {
        this.size = size;
    }

    public  synchronized String getState() {
        return state;
    }

    public  synchronized void setState(String state) {
        this.state = state;
    }

    public synchronized  String getUrl() {
        return url;
    }

    public synchronized  void setUrl(String url) {
        this.url = url;
    }

    public synchronized  int getDownloadSize() {
        return percent;
    }

    public synchronized  void setDownloadSize(int downloadSize) {
        percent = downloadSize;
    }

    public  synchronized long getMtemdownsize() {
        return chunks;
    }

    public synchronized  void setMtemdownsize(int mtemdownsize) {
        this.chunks = mtemdownsize;
    }

    public synchronized  boolean isNotify() {
        return notify;
    }

    public synchronized  void setNotify(boolean notify) {
        this.notify = notify;
    }

    public synchronized  boolean isResumable() {
        return resumable;
    }

    public synchronized  void setResumable(boolean resumable) {
        this.resumable = resumable;
    }

    public synchronized  String getSave_address() {
        return save_address;
    }

    public synchronized  void setSave_address(String save_address) {
        this.save_address = save_address;
    }

    public synchronized  String getmUniquely_id() {
        return muniquely_id;
    }

    public synchronized  void setmUniquely_id(String mUniquely_id) {
        this.muniquely_id = mUniquely_id;
    }

    public synchronized  boolean isPriority() {
        return priority;
    }

    public synchronized void setPriority(boolean priority) {
        this.priority = priority;
    }
}
