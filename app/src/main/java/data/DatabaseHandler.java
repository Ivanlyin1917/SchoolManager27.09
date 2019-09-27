package data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Model.Subject;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String DB_PATH;
    private Context myContext;
    public DatabaseHandler( Context context){
        super(context,Utils.DB_NAME,null,Utils.DB_VERSION);
        String s=context.getFilesDir().getPath()+"/";
        DB_PATH=s+Utils.DB_NAME;
        this.myContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(Utils.DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
 //Крут методы для таблици Subjects
 public Subject getSubject(int id){
     SQLiteDatabase db=this.getReadableDatabase(); //Получмлм объект БД для чтения
     Cursor cursor=db.query(Utils.TABLE_NAME,new String[]{Utils.KEY_ID,Utils.KEY_NAME,Utils.KEY_TYPE_ID},Utils.KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
     Subject subject=new Subject();
     if(cursor!=null){
         cursor.moveToFirst();
         subject.setId(cursor.getInt(0));
         subject.setName(cursor.getString(1));
         subject.setType(cursor.getInt(2));
     }
     return subject;
 }
}

