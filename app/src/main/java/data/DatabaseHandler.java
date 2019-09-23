package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String DB_PATH;
    public DatabaseHandler( Context context){
        super(context,Utils.DB_NAME,null,Utils.DB_VERSION);
        DB_PATH=context.getFilesDir().getPath()+Utils.DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
