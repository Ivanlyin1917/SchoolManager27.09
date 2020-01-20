package com.example.myapplication.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication.Model.Subject;
import com.example.myapplication.Model.Teacher;
import com.example.myapplication.Model.TeachersSubject;
import com.example.myapplication.data.SchoolManagerContract.*;

public class SubjectContentProvider extends ContentProvider {

    private static final int SUBJECT = 100;
    private static final int SUBJECT_ID = 101;
    private static final int ROZKLAD = 200;
    private static final int ROZKLAD_ID=201;
    private static final int HOMEWORK = 300;
    private static final int HOMEWORK_ID = 301;
    private static final int NOTE = 400;
    private static final int NOTE_ID = 401;
    private static final int TEACHER = 500;
    private static final int TEACHER_ID = 501;
    private static final int TS = 600;
    private static final int TS_ID = 601;
    private static final int JINGLE_TYPE= 700;
    private static final int JINGLE_TYPE_ID = 701;
    private static final int JINGLE= 800;
    private static final int JINGLE_ID = 801;
    private DatabaseHandler databaseHandler;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,SubjectEntry.PATH_SUBJECT, SUBJECT);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,SubjectEntry.PATH_SUBJECT +"/#",SUBJECT_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,LessonsEntry.PATH_ROZKLAD, ROZKLAD);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,LessonsEntry.PATH_ROZKLAD+"/#", ROZKLAD_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,HomeworksEntry.PATH_HOMEWORK, HOMEWORK);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,HomeworksEntry.PATH_HOMEWORK+"/#", HOMEWORK_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,NoteEntry.PATH_NOTE, NOTE);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,NoteEntry.PATH_NOTE+"/#", NOTE_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY, TeachersEntry.PATH_TEACHER, TEACHER);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,TeachersEntry.PATH_TEACHER+"/#", TEACHER_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY, TeacherSubjectEntry.PATH_TS, TS);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,TeacherSubjectEntry.PATH_TS+"/#", TS_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY, JingleTypeEntry.PATH_JINGLE_TYPE, JINGLE_TYPE);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,JingleTypeEntry.PATH_JINGLE_TYPE+"/#", JINGLE_TYPE_ID);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY, JingleEntry.PATH_JINGLE, JINGLE);
        sUriMatcher.addURI(SchoolManagerContract.AUTHORITY,JingleEntry.PATH_JINGLE+"/#", JINGLE_ID);



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
                        +", H."+HomeworksEntry.HW_PHOTO+", H."+HomeworksEntry.HW_IS_READY
                        +" from " +HomeworksEntry.TABLE_NAME
                        +" as H inner join "+SubjectEntry.TABLE_NAME+" as S " +
                        "on H."+HomeworksEntry.SUBJECT_ID+"=S."+SubjectEntry.KEY_ID
                        +" where "+HomeworksEntry.DATE_HW+"=? ";
                newCursor = db.rawQuery(sqlHW,selectionArgs);
                break;
            case HOMEWORK_ID:
                String hwArgFrom = HomeworksEntry.TABLE_NAME
                        +" as H inner join "+SubjectEntry.TABLE_NAME+" as S " +
                        "on H."+HomeworksEntry.SUBJECT_ID+"=S."+SubjectEntry.KEY_ID;
                projection = new String[]{"H."+HomeworksEntry.HM_ID+",H."+ HomeworksEntry.HOMEWORK
                        +", S."+SubjectEntry.KEY_NAME +", H."+HomeworksEntry.SUBJECT_ID
                        +", H."+HomeworksEntry.DATE_HW+", H."+HomeworksEntry.HW_PHOTO
                        +", H."+HomeworksEntry.HW_IS_READY};
                selection = "H."+HomeworksEntry.HM_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                newCursor = db.query(hwArgFrom,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case NOTE:
                newCursor = db.query(NoteEntry.TABLE_NAME, projection,
                        selection, selectionArgs,null,null,sortOrder);
                break;
            case NOTE_ID:
                selection =NoteEntry.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                newCursor = db.query(NoteEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case TEACHER:
                String sqlTeacher = "Select T."+TeachersEntry.TEACHER_ID+" as teather_id,T."+ TeachersEntry.SURNAME
                        +", T."+TeachersEntry.NAME+", T."+TeachersEntry.LASTNAME
                        +", S."+SubjectEntry.KEY_NAME +", S."+ SubjectEntry.KEY_ID+" as subject_id, TS."
                        +TeacherSubjectEntry.TS_ID+" as ts_id from " +TeachersEntry.TABLE_NAME
                        +" as T left outer join "+TeacherSubjectEntry.TABLE_NAME+" as TS " +
                        "on TS."+ TeacherSubjectEntry.TEACHER_ID+"=T."+TeachersEntry.TEACHER_ID
                        +" left outer join " + SubjectEntry.TABLE_NAME + " as S "+
                        "on TS."+ TeacherSubjectEntry.SUBJECT_ID+"=S."+SubjectEntry.KEY_ID;
                Log.i("Teacher",sqlTeacher);
                newCursor = db.rawQuery(sqlTeacher,selectionArgs);
                break;
            case JINGLE_TYPE:
                newCursor = db.query(JingleTypeEntry.TABLE_NAME, projection,
                        selection, selectionArgs,null,null,sortOrder);
                break;
            case JINGLE_TYPE_ID:
                selection =JingleTypeEntry.JINGLE_TYPE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                newCursor = db.query(JingleTypeEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case JINGLE:
                selection =JingleEntry.JINGLE_TYPE_ID + "=?";
                sortOrder = JingleEntry.POSITION_ID;
                newCursor = db.query(JingleEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;

            case JINGLE_ID:
                selection =JingleEntry.JINGLE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                newCursor = db.query(JingleEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,sortOrder);
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
        long new_id;
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
            case HOMEWORK:
                long hw_id = db.insert(HomeworksEntry.TABLE_NAME,null,values);
                if (hw_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,hw_id);
            case NOTE:
                long note_id = db.insert(NoteEntry.TABLE_NAME,null,values);
                if (note_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,note_id);
            case TEACHER:
                long teacher_id = db.insert(TeachersEntry.TABLE_NAME,null,values);
                if (teacher_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,teacher_id);
            case TS:
                long ts_id = db.insert(TeacherSubjectEntry.TABLE_NAME,null,values);
                if (ts_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,ts_id);
            case JINGLE_TYPE:
                long jt_id = db.insert(JingleTypeEntry.TABLE_NAME,null,values);
                if (jt_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,jt_id);
            case JINGLE:
                long jingle_id = db.insert(JingleEntry.TABLE_NAME,null,values);
                if (jingle_id ==-1){
                    Log.e("insertMethod","Insert of data in the table failed for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,jingle_id);
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
            case HOMEWORK_ID:
                selection = HomeworksEntry.HM_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countDelRec = db.delete(HomeworksEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case NOTE_ID:
                selection = NoteEntry.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countDelRec = db.delete(NoteEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case TS_ID:
                selection = TeacherSubjectEntry.TS_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countDelRec = db.delete(TeacherSubjectEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case TEACHER_ID:
                selection = TeachersEntry.TEACHER_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countDelRec = db.delete(TeachersEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case JINGLE_TYPE_ID:
                selection = JingleTypeEntry.JINGLE_TYPE_ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countDelRec = db.delete(JingleTypeEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case JINGLE_ID:
                selection = JingleEntry.JINGLE_ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countDelRec = db.delete(JingleEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case JINGLE:
                selection = JingleEntry.JINGLE_TYPE_ID+ "=?";
                countDelRec = db.delete(JingleEntry.TABLE_NAME,selection,selectionArgs);
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
            case HOMEWORK_ID:
                selection = HomeworksEntry.HM_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(HomeworksEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            case NOTE_ID:
                selection = NoteEntry.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(NoteEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            case TS_ID:
                selection = TeacherSubjectEntry.TS_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(TeacherSubjectEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            case TEACHER_ID:
                selection = TeachersEntry.TEACHER_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(TeachersEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            case JINGLE_TYPE_ID:
                selection = JingleTypeEntry.JINGLE_TYPE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(JingleTypeEntry.TABLE_NAME,values,selection, selectionArgs);
                break;
            case JINGLE_ID:
                selection = JingleEntry.JINGLE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                countRec =db.update(JingleEntry.TABLE_NAME,values,selection, selectionArgs);
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
            case ROZKLAD_ID:
                return LessonsEntry.ROZKLAD_SINGLE_ITEM;
            case HOMEWORK:
                return HomeworksEntry.HOMEWORK_MULTIPLE_ITEM;
            case HOMEWORK_ID:
                return HomeworksEntry.HOMEWORK_SINGLE_ITEM;
            case NOTE:
                return NoteEntry.NOTE_MULTIPLE_ITEM;
            case NOTE_ID:
                return NoteEntry.NOTE_SINGLE_ITEM;
            case TS:
                return TeacherSubjectEntry.TS_MULTIPLE_ITEM;
            case TS_ID:
                return TeacherSubjectEntry.TS_SINGLE_ITEM;
            case TEACHER:
                return TeachersEntry.TEACHER_MULTIPLE_ITEM;
            case TEACHER_ID:
                return TeachersEntry.TEACHER_SINGLE_ITEM;
            case JINGLE_TYPE:
                return JingleTypeEntry.JINGLE_TYPE_MULTIPLE_ITEM;
            case JINGLE_TYPE_ID:
                return JingleTypeEntry.JINGLE_TYPE_SINGLE_ITEM;
            case JINGLE:
                return JingleEntry.JINGLE_MULTIPLE_ITEM;
            case JINGLE_ID:
                return JingleEntry.JINGLE_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
