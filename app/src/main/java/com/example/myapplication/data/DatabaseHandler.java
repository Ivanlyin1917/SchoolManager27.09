package com.example.myapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.myapplication.Model.Subject;


public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DB_VERSION = 30;
    public static final String DB_NAME = "school.db";
    private static String DB_PATH;
    private Context myContext;
    private SQLiteDatabase myDB;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //DB_PATH = "/com.example.myapplication.data/com.example.myapplication.data/" + context.getPackageName() + "/databases/";
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        File dbFile = new File(DB_PATH);//+ DB_NAME);
        if (dbFile.exists())
            dbFile.delete();
        try {
            copyDataBase();
        } catch (IOException ex) {
            throw new Error("no copy");
        }
    }

    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     *
     * @return true если существует, false если не существует
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;// + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //база еще не существует
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Создает пустую базу данных и перезаписывает ее нашей собственной базой
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

       // if (dbExist) {
        //ничего не делать - база уже есть
        // } else {
        //вызывая этот метод создаем пустую базу, позже она будет перезаписана
        SQLiteDatabase db = this.getReadableDatabase();
        if (db.isOpen()) {
            db.close();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
   // }

}

    /**
     * Копирует базу из папки assets заместо созданной локальной БД
     * Выполняется путем копирования потока байтов.
     * */
    private void copyDataBase() throws IOException{
        //Открываем локальную БД как входящий поток
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        //Путь ко вновь созданной БД
        String outFileName = DB_PATH;//+ DB_NAME;
        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(outFileName);
        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);       }

        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public void openDataBase() throws SQLException{
        //открываем БД
        String myPath = DB_PATH;// + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDB != null)
            myDB.close();
        super.close();
    }

 //Крут методы для таблици Subjects
 public Subject getSubject(int id){
     Subject subject=new Subject();
     SQLiteDatabase db=this.getReadableDatabase(); //Получмлм объект БД для чтения
     Log.i("shlyax",db.getPath());
     List d = db.getAttachedDbs();
     for(int i=0; i<d.size();i++){
         Log.i("shlyax","size="+d.get(i));
     }

     /*Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_NAME,KEY_TYPE_ID},KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
     if(cursor!=null){
         cursor.moveToFirst();
         subject.setId(cursor.getInt(0));
         subject.setName(cursor.getString(1));
         subject.setType(cursor.getInt(2));
     }*/
     return subject;
 }



 //Дополнительный функционал. Удалить после разработки
 public ArrayList<Cursor> getData(String Query){
     //get writable database
     SQLiteDatabase sqlDB = this.getWritableDatabase();
     String[] columns = new String[] { "message" };
     //an array list of cursor to save two cursors one has results from the query
     //other cursor stores error message if any errors are triggered
     ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
     MatrixCursor Cursor2= new MatrixCursor(columns);
     alc.add(null);
     alc.add(null);

     try{
         String maxQuery = Query ;
         //execute the query results will be save in Cursor c
         Cursor c = sqlDB.rawQuery(maxQuery, null);

         //add value to cursor2
         Cursor2.addRow(new Object[] { "Success" });

         alc.set(1,Cursor2);
         if (null != c && c.getCount() > 0) {

             alc.set(0,c);
             c.moveToFirst();

             return alc ;
         }
         return alc;
     } catch(SQLException sqlEx){
         Log.d("printing exception", sqlEx.getMessage());
         //if any exceptions are triggered save the error message to cursor an return the arraylist
         Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
         alc.set(1,Cursor2);
         return alc;
     } catch(Exception ex){
         Log.d("printing exception", ex.getMessage());

         //if any exceptions are triggered save the error message to cursor an return the arraylist
         Cursor2.addRow(new Object[] { ""+ex.getMessage() });
         alc.set(1,Cursor2);
         return alc;
     }
 }

}

