package com.example.myapplication.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class SchoolManagerContract {

    public SchoolManagerContract() {
    }
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.myapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);
    public static final String PATH_SUBJECT = "Subjects";


    public static final class SubjectEntry implements BaseColumns {
        public static  final  String TABLE_NAME="Subjects";
        public static  final String KEY_ID="Subject_id";
        public static  final String KEY_NAME="Subject_name";
        public static  final  String KEY_TYPE_ID="Subject_type_id";
        public static final Uri SUBJECT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SUBJECT);
    }
    public static final class DaysEntry implements BaseColumns{
        public  static final String DAY_ID="Day_id";
        public  static  final  String DAY_NAME="Day_name";
    }
    public static final class JingleEntry implements BaseColumns{
        public static  final String JINGLE_ID="Jingle_id";
        public static  final String POSITION_ID="Position_id";
        public static  final String TIME_BEGIN="Time_begin";
        public static  final String TIME_END="Time_end";
        public static  final String JINGLE_TYPE_ID="Jingle_type_id";
    }
    public static final class HobbyEntry implements BaseColumns{
        public static  final String HOBBY_ID="Hobby_id";
        public static  final String HOBBY_NAME="Hoby_name";
        public static  final String DAY_ID="Day_id";
        public static  final String HOBBY_DAY="Hobby_day";
        public static  final String HOBBY_PLACE="Hobby_place";
        public static  final String SUBJECT_TYPE_ID="Subject_type_id";
    }
    public static final class Subject_type_Entry implements BaseColumns{
        public static  final String SUBJECT_TYPE_ID="Subject_type_id";
        public static  final String SUBJECT_TYPE_NAME="Subject_type_name";
    }
    public static final class HomeworksEntry implements BaseColumns{
        public static  final String HM_ID="hm_id";
        public static  final String DATE_HW="Date_hw";
        public static  final String SUBJECT_ID="Subject_id";
        public static  final String HOMEWORK="Homework";
    }
    public static final class Jingle_type_Entry implements BaseColumns{
        public static  final String JINGLE_TYPE_ID="Jingle_type_id";
        public static  final String TYPE_NAME="Type_name";
    }
    public static final class LessonsEntry implements BaseColumns{
        public static  final String REC_ID="Rec_id";
        public static  final String DAY_ID="Day_id";
        public static  final String POSITION_ID="Position_id";
        public static  final String SUBJECT_ID="Subject_id";
    }
    public static final class Teacher_subject_Entry implements BaseColumns{
        public static  final String TS_ID="Ts_id";
        public static  final String TEACHER_ID="Teacher_id";
        public static  final String SUBJECT_ID="Subject_id";
    }
    public static final class TeachersEntry implements BaseColumns{
        public static  final String TEACHER_ID="Teacher_id";
        public static  final String SURNAME="Surname";
        public static  final String NAME="Name";
        public static  final String LASTNAME="LastName";
    }


}
