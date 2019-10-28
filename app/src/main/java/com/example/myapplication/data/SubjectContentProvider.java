package com.example.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.example.myapplication.Model.Lessons;
import com.example.myapplication.data.SchoolManagerContract.*;

public class SubjectContentProvider extends ContentProvider {

    private static final int SUBJECT = 100;
    private static final int SUBJECT_ID = 101;
    private static final int ROZRLAD = 200;
    private DatabaseHandler databaseHandler;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,SubjectEntry.PATH_SUBJECT, SUBJECT);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,SubjectEntry.PATH_SUBJECT +"/#",SUBJECT_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,LessonsEntry.PATH_ROZKLAD, ROZRLAD);

    }
    @Override
    public boolean onCreate() {
        databaseHandler = new DatabaseHandler(getContext());
        return true;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor newCursor;
        int match = sUriMatcher.match(uri);
        switch (match){
            case SUBJECT:
                newCursor = db.query(SubjectEntry.TABLE_NAME, projection,
                        selection, selectionArgs,null,null,sortOrder);
                break;
            case SUBJECT_ID:
                selection =SubjectEntry.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                newCursor = db.query(SubjectEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case ROZRLAD:
                String sqlText = "Select L."+LessonsEntry.LESSON_ID+", L."+LessonsEntry.POSITION_ID
                        +", L."+ LessonsEntry.LESSON_PLACE +", S."+SubjectEntry.KEY_NAME+", J."+
                        JingleEntry.TIME_BEGIN+", J."+JingleEntry.TIME_END
                        +" from " +LessonsEntry.TABLE_NAME+" as L inner join "
                        +SubjectEntry.TABLE_NAME+" as S on L."+LessonsEntry.SUBJECT_ID+"=S."
                        +SubjectEntry.KEY_ID+ " inner join "+JingleEntry.TABLE_NAME+" as J on L."+
                        LessonsEntry.POSITION_ID+"=J."+JingleEntry.POSITION_ID+" inner join "+
                        DaysEntry.TABLE_NAME+" as D on L."+LessonsEntry.DAY_ID+"=D."+DaysEntry.DAY_ID
                        +" where "+LessonsEntry.DAY_ID+"=? and D."+DaysEntry.JINGLE_TYPE+"= J."+
                        JingleEntry.JINGLE_TYPE_ID;
                newCursor = db.rawQuery(sqlText,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }
        return newCursor;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        int math = sUriMatcher.match(uri);
        switch (math){
            case SUBJECT:
                long id = db.insert(SubjectEntry.TABLE_NAME,null,values);
                if (id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                return ContentUris.withAppendedId(uri,id);
            default:
                throw new IllegalArgumentException("Insert of data in the table failed for " + uri);
        }

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int countRec;
        switch (match){
            case SUBJECT:
                countRec = db.update(SubjectEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            case SUBJECT_ID:
                selection =SubjectEntry.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(SubjectEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't update incorrect URI " + uri);
        }
        return countRec;

    }
    @Override
    public String getType( Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case SUBJECT:
                return SubjectEntry.SUBJECT_MULTIPLE_ITEM;
            case SUBJECT_ID:
                return SubjectEntry.SUBJECT_SINGLE_ITEM;
            case ROZRLAD:
                return LessonsEntry.ROZKLAD_MULTIPLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
