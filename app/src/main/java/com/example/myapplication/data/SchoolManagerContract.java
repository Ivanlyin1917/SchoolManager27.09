package com.example.myapplication.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class SchoolManagerContract {

    public SchoolManagerContract() {
    }
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.myapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public static final class SubjectEntry implements BaseColumns {
        public static  final  String TABLE_NAME="Subjects";
        public static  final String KEY_ID="_id";
        public static  final String KEY_NAME="Subject_name";
        public static final String PATH_SUBJECT = "Subjects";
        public static final Uri SUBJECT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SUBJECT);
        public static final String SUBJECT_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+AUTHORITY+"/"+PATH_SUBJECT;
        public static final String SUBJECT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+AUTHORITY+"/"+AUTHORITY+PATH_SUBJECT;
    }
    public static final class NoteEntry implements BaseColumns{
        public static final String TABLE_NAME = "notes";
        public static final String KEY_ID = "id";
        public static final String KEY_NOTE = "note";
        public static final String KEY_TIMESTAMP = "timestamp";
        public static final String PATH_NOTE = "notes";
        public static final Uri NOTE_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTE);
        public static final String NOTE_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+AUTHORITY+"/"+PATH_NOTE;
        public static final String NOTE_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+AUTHORITY+"/"+AUTHORITY+PATH_NOTE;
    }

    public static final class DaysEntry implements BaseColumns{
        public static  final  String TABLE_NAME="days";
        public  static final String DAY_ID="_id";
        public  static  final  String DAY_NAME="day_name";
        public  static  final  String DAY_TYPE="is_school_day";
        public  static  final  String JINGLE_TYPE="jingle_type_id";
    }
    public static final class JingleEntry implements BaseColumns{
        public static  final  String TABLE_NAME="jingle";
        public static  final String JINGLE_ID="_id";
        public static  final String POSITION_ID="position_id";
        public static  final String TIME_BEGIN="time_begin";
        public static  final String TIME_END="time_end";
        public static  final String JINGLE_TYPE_ID="jingle_type_id";
        public static final String PATH_JINGLE= "jingle";
        public static final Uri JINGLE_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_JINGLE);
        public static final String JINGLE_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_JINGLE;
        public static final String JINGLE_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_JINGLE;
    }
    public static final class HobbyEntry implements BaseColumns{
        public static  final String HOBBY_ID="_id";
        public static  final String HOBBY_NAME="Hoby_name";
        public static  final String DAY_ID="Day_id";
        public static  final String HOBBY_DAY="Hobby_day";
        public static  final String HOBBY_PLACE="Hobby_place";
       // public static  final String SUBJECT_TYPE_ID="Subject_type_id";
    }
//    public static final class Subject_type_Entry implements BaseColumns{
//        public static  final String SUBJECT_TYPE_ID="Subject_type_id";
//        public static  final String SUBJECT_TYPE_NAME="Subject_type_name";
//    }
    public static final class HomeworksEntry implements BaseColumns{
        public static  final  String TABLE_NAME="homeworks";
        public static  final String HM_ID="_id";
        public static  final String DATE_HW="date_hw";
        public static  final String SUBJECT_ID="subject_id";
        public static  final String HOMEWORK="Homework";
        public static final String HW_PHOTO = "hw_photo";
        public static final String HW_IS_READY = "is_ready";
        public static final String PATH_HOMEWORK = "homeworks";
        public static final Uri HOMEWORK_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HOMEWORK);
        public static final String HOMEWORK_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE
            +"/"+AUTHORITY+"/"+PATH_HOMEWORK;
        public static final String HOMEWORK_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
            +"/"+AUTHORITY+"/"+PATH_HOMEWORK;
    }
    public static final class JingleTypeEntry implements BaseColumns{
        public static  final  String TABLE_NAME="jingle_type";
        public static  final String JINGLE_TYPE_ID="_id";
        public static  final String TYPE_NAME="type_name";
        public static final String PATH_JINGLE_TYPE= "jingle_type";
        public static final Uri JINGLE_TYPE_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_JINGLE_TYPE);
        public static final String JINGLE_TYPE_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_JINGLE_TYPE;
        public static final String JINGLE_TYPE_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_JINGLE_TYPE;
    }
    public static final class LessonsEntry implements BaseColumns{
        public static  final  String TABLE_NAME="lessons";
        public static  final String LESSON_ID="_id";
        public static  final String DAY_ID="Day_id";
        public static  final String POSITION_ID="position_id";
        public static  final String SUBJECT_ID="subject_id";
        public static  final String LESSON_PLACE="place";
        public static final String PATH_ROZKLAD = "Rozklad";
        public static final Uri ROZKLAD_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ROZKLAD);
        public static final String ROZKLAD_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_ROZKLAD;
        public static final String ROZKLAD_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_ROZKLAD;
    }
    public static final class TeacherSubjectEntry implements BaseColumns{
        public static  final  String TABLE_NAME="teacher_subject";
        public static  final String TS_ID="_id";
        public static  final String TEACHER_ID="Teacher_id";
        public static  final String SUBJECT_ID="Subject_id";
        public static final String PATH_TS = "teacher_subject";
        public static final Uri TS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TS);
        public static final String TS_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_TS;
        public static final String TS_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_TS;
    }
    public static final class TeachersEntry implements BaseColumns{
        public static  final  String TABLE_NAME="teachers";
        public static  final String TEACHER_ID="_id";
        public static  final String SURNAME="surname";
        public static  final String NAME="name";
        public static  final String LASTNAME="lastName";
        public static final String PATH_TEACHER = "teachers";
        public static final Uri TEACHER_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TEACHER);
        public static final String TEACHER_MULTIPLE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_TEACHER;
        public static final String TEACHER_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE
                +"/"+AUTHORITY+"/"+PATH_TEACHER;
    }



}
