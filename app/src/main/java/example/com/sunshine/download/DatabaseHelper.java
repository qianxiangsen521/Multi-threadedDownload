package example.com.sunshine.download;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.golshadi.majid.database.constants.TABLES;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianxiangsen on 2017/4/20.
 */

public class DatabaseHelper {

    private SQLiteDatabase database;

    public void openDatabase(MyOpenHelper dbHelper) {
        database = dbHelper.getWritableDatabase();
    }

    public synchronized long insertTask(Task task){
        long id = database
                .insert(MyOpenHelper.TABLE_NAME, null, task.convertToContentValues());

        return id;
    }
    public synchronized boolean update(Task task){
        int affectedRow = database
                .update(MyOpenHelper.TABLE_NAME, task.convertToContentValues(), TASKS.COLUMN_ID+"="+task.getId(), null);

        if (affectedRow != 0)
            return true;

        return false;
    }
    public synchronized List<Task> getTasksInState(int state) {
        List<Task> tasks = new ArrayList<Task>();
        String query;

        if (state < 6)
            query = "SELECT * FROM "+MyOpenHelper.TABLE_NAME+" WHERE "+ TASKS.COLUMN_STATE+"="+ SqlString.Int(state);
        else
            query = "SELECT * FROM "+MyOpenHelper.TABLE_NAME;

        Cursor cr = database.rawQuery(query, null);

        if (cr != null){
            cr.moveToFirst();

            while (!cr.isAfterLast()) {
                Task task = new Task();
                task.cursorToTask(cr);
                tasks.add(task);

                cr.moveToNext();
            }

            cr.close();
        }

        return tasks;
    }

    public synchronized List<Task> getUnnotifiedCompleted() {
        List<Task> completedTasks = new ArrayList<>();

        String query = "SELECT * FROM "+MyOpenHelper.TABLE_NAME;
        Cursor cr = database.rawQuery(query, null);

        if (cr != null){
            while (cr.moveToNext()) {
                Task task = new Task();
                task.cursorToTask(cr);
                completedTasks.add(task);
            }

            cr.close();
        }

        return completedTasks;
    }
    public synchronized Task getTaskInfo(Task task) {
        String query = "SELECT * FROM " + MyOpenHelper.TABLE_NAME + " WHERE " + TASKS.COLUMN_ID + "=" + SqlString.Int(task.getId());
        Cursor cr = database.rawQuery(query, null);

        if (cr != null && cr.moveToFirst()) {
            task.cursorToTask(cr);
            cr.close();
        }

        return task;
    }

    public synchronized Task getTaskInfoWithName(String name) {
        String query = "SELECT * FROM "+MyOpenHelper.TABLE_NAME+" WHERE "+ TASKS.COLUMN_NAME+"="+ SqlString.String(name);
        Cursor cr = database.rawQuery(query, null);

        Task task = new Task();
        if (cr != null && cr.moveToFirst()) {
            task.cursorToTask(cr);
            cr.close();
        }

        return task;
    }

    public synchronized boolean delete(long taskID) {
        int affectedRow = database
                .delete(MyOpenHelper.TABLE_NAME, TASKS.COLUMN_ID + "=" + SqlString.Int(taskID), null);

        if (affectedRow != 0)
            return true;

        return false;
    }


    public  synchronized boolean containsTask(String name) {
        boolean result = false;
        String  query = "SELECT * FROM "+ MyOpenHelper.TABLE_NAME +" WHERE "+ TASKS.COLUMN_NAME+"="+ SqlString.String(name);
        Cursor cr = database.rawQuery(query, null);

        if (cr != null && cr.getCount() != 0) {
            result = true;
            cr.close();
        }

        return result;
    }

    public synchronized  boolean checkUnNotifiedTasks() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASKS.COLUMN_NOTIFY, 1);
        int affectedRows = database.update(MyOpenHelper.TABLE_NAME, contentValues, TASKS.COLUMN_NOTIFY+"="+ SqlString.Int(0), null);

        return affectedRows>0;
    }

    public  synchronized void close() {
        database.close();
    }

}
