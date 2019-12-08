package com.example.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication.data.SchoolManagerContract.*;

public class SubjectContentProvider extends ContentProvider {

    private static final int SUBJECT = 100;
    private static final int SUBJECT_ID = 101;
    private static final int ROZKLAD = 200;
    private static final int ROZKLAD_ID=201;
    private static final int HOMEWORK = 300;
    private static final int HOMEWORK_ID = 301;
    private DatabaseHandler databaseHandler;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,SubjectEntry.PATH_SUBJECT, SUBJECT);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,SubjectEntry.PATH_SUBJECT +"/#",SUBJECT_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,LessonsEntry.PATH_ROZKLAD, ROZKLAD);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,LessonsEntry.PATH_ROZKLAD+"/#", ROZKLAD_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,HomeworksEntry.PATH_HOMEWORK, HOMEWORK);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,HomeworksEntry.PATH_HOMEWORK+"/#", HOMEWORK_ID);

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
            case ROZKLAD:
                String sqlText = "Select L."+LessonsEntry.LESSON_ID+",L."+ LessonsEntry.LESSON_PLACE
                        +", S."+SubjectEntry.KEY_NAME +", L."+LessonsEntry.SUBJECT_ID
                        +" from " +LessonsEntry.TABLE_NAME
                        +" as L inner join "+SubjectEntry.TABLE_NAME+" as S " +
                        "on L."+LessonsEntry.SUBJECT_ID+"=S."+SubjectEntry.KEY_ID
                        +" where "+LessonsEntry.DAY_ID+"=? ";
                newCursor = db.rawQuery(sqlText,selectionArgs);
                break;
            case ROZKLAD_ID:
                String argFrom = LessonsEntry.TABLE_NAME
                        +" as L inner join "+SubjectEntry.TABLE_NAME+" as S " +
                        "on L."+LessonsEntry.SUBJECT_ID+"=S."+SubjectEntry.KEY_ID;
                projection = new String[]{"L."+LessonsEntry.LESSON_ID+",L."+ LessonsEntry.LESSON_PLACE
                        +", S."+SubjectEntry.KEY_NAME +", L."+LessonsEntry.SUBJECT_ID};
                selection = "L."+LessonsEntry.LESSON_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                newCursor = db.query(argFrom,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case HOMEWORK:
                String sqlHW = "Select H."+HomeworksEntry.HM_ID+",H."+ HomeworksEntry.DATE_HW
                        +", S."+SubjectEntry.KEY_NAME +", H."+HomeworksEntry.HOMEWORK
                        +" from " +HomeworksEntry.TABLE_NAME
                        +" as H inner join "+SubjectEntry.TABLE_NAME+" as S " +
                        "on H."+HomeworksEntry.SUBJECT_ID+"=S."+SubjectEntry.KEY_ID
                        +" where "+HomeworksEntry.DATE_HW+"=? ";
                newCursor = db.rawQuery(sqlHW,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }
        newCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return newCursor;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {

        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        int math = sUriMatcher.match(uri);
        switch (math){
            case SUBJECT:
                String subjectName = values.getAsString(SubjectEntry.KEY_NAME);
                if (subjectName==null){
                    throw new IllegalArgumentException("Потрібно вказати назву предмета");
                }
                long subject_id = db.insert(SubjectEntry.TABLE_NAME,null,values);
                if (subject_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,subject_id);
            case ROZKLAD:
                long lesson_id = db.insert(LessonsEntry.TABLE_NAME,null,values);
                if (lesson_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,lesson_id);
            default:
                throw new IllegalArgumentException("Insert of data in the table failed for " + uri);
        }

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int countDelRec;
        switch (match){
            case ROZKLAD_ID:
                selection = LessonsEntry.LESSON_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countDelRec = db.delete(LessonsEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't delete this URI " + uri);
        }
        if(countDelRec !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return countDelRec;
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
            case ROZKLAD_ID:
                selection = LessonsEntry.LESSON_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(LessonsEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't update incorrect URI " + uri);
        }
        if(countRec !=0){
            getContext().getContentResolver().notifyChange(uri,null);
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
            case ROZKLAD:
                return LessonsEntry.ROZKLAD_MULTIPLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
