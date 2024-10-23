package com.tophat.todo.database;
import com.tophat.todo.database.Task;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;

public class Fetcher extends SQLiteOpenHelper {
    // TODO: add database interaction here
   public static final String DATABASE_NAME = "todo.db";
   public static final String TASKS_TABLE_NAME = "tasks";
   public static final String TASKS_COLUMN_ID = "id";
   public static final String TASKS_COLUMN_NAME = "name";
   public static final String TASKS_COLUMN_URGENCY = "urgency";
   public static final String TASKS_COLUMN_DESC = "desc";
    public Fetcher(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
         "create table tasks " +
         "(id integer primary key, name text,urgency integer,desc text)"
      );
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO: Implement this method
       // db.execSQL("DROP TABLE IF EXISTS contacts");
      //  onCreate(db);
        
    }
    public void insertTask (String name, String desc, Integer urgency) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(TASKS_COLUMN_NAME, name);
      contentValues.put(TASKS_COLUMN_DESC, desc);
      contentValues.put(TASKS_COLUMN_URGENCY, urgency);	
      db.insert(TASKS_TABLE_NAME, null, contentValues);
   }
    public Task getData(int id) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from tasks where id="+id+"", null );
      return new Task(
            res.getString(res.getColumnIndex(TASKS_COLUMN_NAME)),
            res.getString(res.getColumnIndex(TASKS_COLUMN_DESC)),
            res.getInt(res.getColumnIndex(TASKS_COLUMN_URGENCY)), id);
   }
    public int getLatestId() {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select MAX(id) from tasks", null);
        res.moveToFirst();
      return res.getInt(res.getColumnIndex("MAX(id)"));
   }
    public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, TASKS_TABLE_NAME);
      return numRows;
   }
    public void updateTask (Integer id, String name, String desc, Integer urgency) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(TASKS_COLUMN_NAME, name);
      contentValues.put(TASKS_COLUMN_DESC, desc);
      contentValues.put(TASKS_COLUMN_URGENCY, urgency);
      db.update(TASKS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
   }
    public Integer deleteTask (Integer id) {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete(TASKS_TABLE_NAME, 
      "id = ? ", 
      new String[] { Integer.toString(id) });
   }
    public ArrayList<Task> getAllTasks() {
      ArrayList<Task> array_list = new ArrayList<Task>();
      
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from tasks", null );
      res.moveToFirst();
      
      while(res.isAfterLast() == false){
         array_list.add(
             new Task(
            res.getString(res.getColumnIndex(TASKS_COLUMN_NAME)),
            res.getString(res.getColumnIndex(TASKS_COLUMN_DESC)),
            res.getInt(res.getColumnIndex(TASKS_COLUMN_URGENCY)),
            res.getInt(res.getColumnIndex(TASKS_COLUMN_ID))));
         res.moveToNext();
      }
      return array_list;
   }
}
